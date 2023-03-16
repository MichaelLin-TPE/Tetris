package com.game.tetris.ui;

import static com.game.tetris.tool.CubeTool.CUBE_TURN_L1_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L1_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L1_WAY3;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L1_WAY4;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY3;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY4;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_LONG_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_LONG_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_T_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_T_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_T_WAY3;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_T_WAY4;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_Z1_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_Z1_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_Z2_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_Z2_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_L1;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_L2;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_LONG;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_SQUIRE;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_T;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_Z;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_Z2;

import android.annotation.SuppressLint;

import com.game.tetris.MichaelLog;
import com.game.tetris.battle.R;
import com.game.tetris.bean.CompareY;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;
import com.game.tetris.tool.CubeTool;
import com.game.tetris.tool.SharedPreferTool;
import com.game.tetris.tool.Tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class GamePresenterImpl implements GamePresenter {

    private final GameVu mView;

    private final ArrayList<LatticeData> latticeDataList = new ArrayList<>();
    private final ArrayList<CubeData> cubeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeTempList, supportCubeList;
    private float latticeWidth, latticeHeight; // 格子的寬跟高
    private float gameViewBottomY, gameViewTopY, gameViewLeftX, gameViewRightX;
    private int currentCubeType,nextCubeType = -1;
    private int CUBE_DOWN_SPEED = 500;
    private boolean isCanMoveOrTurnCube = false;
    private long pressDownTimeMillis = 0, pressUpTimeMillis = 0;
    private Disposable disposableKeepMoving; //控制左右按鈕按壓後的TIMER
    private Disposable disposableCountingKeepPress;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isActiveSupportLine = false;
    private int currentPoint = 0;

    public GamePresenterImpl(GameVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCreateGameView() {

        mView.createGameViewBackground();

        if (SharedPreferTool.getInstance().isActiveMusic()){
            mView.startPlayBackgroundMusic();
        }

    }

    @Override
    public void createLatticeDataList(float x, float y, int right, int left, int bottom) {
        float gameViewX = x + 15;
        float gameViewY = y + 15;
        latticeWidth = Tool.convertDoubleWithTwoPercent(((right - 15) - (left + 15)) / 10f);
        latticeHeight = Tool.convertDoubleWithTwoPercent(((bottom - 15) - (y + 15)) / 20);
        MichaelLog.i("width : "+latticeWidth+" , height : "+latticeHeight);
        float compareY = 0;
        ArrayList<LatticeData> dataList = new ArrayList<>();
        for (int straight = 0; straight < 20; straight++) {
            for (int row = 0; row < 10; row++) {
                if (compareY == 0) {
                    compareY = gameViewY + (latticeHeight * straight);
                } else {
                    compareY = Math.abs(compareY - (gameViewY + (latticeHeight * straight))) < 5 ? compareY : gameViewY + (latticeHeight * straight);
                }
                dataList.add(new LatticeData(gameViewX + latticeWidth * row, compareY,
                        straight,
                        straight % 2 == 0 ?
                                row % 2 == 0 ? R.drawable.lattice_bg : R.drawable.lattice_bg1 :
                                row % 2 == 0 ? R.drawable.lattice_bg1 : R.drawable.lattice_bg
                ));
            }
        }

        latticeDataList.addAll(dataList);
        gameViewBottomY = Tool.convertDoubleWithTwoPercent(latticeDataList.get(latticeDataList.size() - 1).getY());
        gameViewTopY = Tool.convertDoubleWithTwoPercent(latticeDataList.get(0).getY());
        gameViewLeftX = Tool.convertDoubleWithTwoPercent(left + 15);
        gameViewRightX = Tool.convertDoubleWithTwoPercent(right - 15);
        MichaelLog.i("bottomY : " + gameViewBottomY+" , rightX : "+gameViewRightX);


        for (LatticeData data : latticeDataList) {
            mView.showLattice(data, latticeWidth, latticeHeight);
        }

        //開始產出方塊
        createCube();


    }


    private boolean isReachLeft() {
        boolean isReachLeft = false;
        for (CubeData data : cubeTempList) {
            if (Math.abs(data.getX() - gameViewLeftX) < 5) {
                isReachLeft = true;
                break;
            }
        }
        for (CubeData movingCube : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs((movingCube.getX() - latticeWidth ) - cubeData.getX()) < 5 && Math.abs(cubeData.getY() - movingCube.getY()) < 5) {
                    isReachLeft = true;
                    break;
                }
            }
            if (isReachLeft) {
                break;
            }
        }

        return isReachLeft;
    }

    private boolean isReachRight() {
        boolean isReachRight = false;
        for (CubeData data : cubeTempList) {
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 5) {
                isReachRight = true;
                break;
            }
        }

        for (CubeData movingCube : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs((movingCube.getX() + latticeWidth) - cubeData.getX()) < 5 && Math.abs(cubeData.getY() - movingCube.getY()) < 5) {
                    isReachRight = true;
                    break;
                }
            }
            if (isReachRight) {
                break;
            }
        }

        return isReachRight;
    }

    private void moveSupportLine() {
        if (!isActiveSupportLine) {
            return;
        }
        float leftX = 0f;
        float rightX = 0f;
        float topY = 0f;
        float bottomY = 0f;
        float rightBottomY = 0f;
        for (CubeData data : cubeTempList) {
            if (leftX == 0) {
                leftX = data.getX();
                topY = data.getY();
                continue;
            }
            if (leftX > data.getX()) {
                leftX = data.getX();
                topY = data.getY();
            }
        }
        for (CubeData data : cubeTempList) {
            if (rightX == 0) {
                rightX = data.getX() + latticeWidth;
                continue;
            }
            if (rightX < data.getX() + latticeWidth) {
                rightX = data.getX() + latticeWidth;
            }
        }
        if (leftX == rightX) {
            rightX = rightX + latticeWidth;
        }
        if (cubeDataList.isEmpty()) {
            bottomY = gameViewBottomY + latticeHeight;
            rightBottomY = gameViewBottomY + latticeHeight;
        } else {
            for (CubeData data : cubeDataList) {
                if (bottomY == 0 && data.getX() == leftX) {
                    bottomY = data.getY();
                    continue;
                }
                if (bottomY > data.getY() && data.getX() == leftX) {
                    bottomY = data.getY();
                }
            }
            for (CubeData data : cubeDataList) {
                if (rightBottomY == 0 && data.getX() + latticeWidth == rightX) {
                    rightBottomY = data.getY();
                    continue;
                }
                if (rightBottomY > data.getY() && data.getX() + latticeWidth == rightX) {
                    rightBottomY = data.getY();
                }
            }
        }
        if (bottomY == 0) {
            bottomY = gameViewBottomY + latticeHeight;
        }
        if (rightBottomY == 0) {
            rightBottomY = gameViewBottomY + latticeHeight;
        }
        mView.moveSupportLine(leftX, rightX, topY, bottomY, rightBottomY);
    }

    @Override
    public void onTurnCubeButtonClickListener() {
        if (!isCanMoveOrTurnCube) {
            return;
        }
        if (currentCubeType == CUBE_TYPE_LONG) {
            changeWayOfLongCube();
        }
        if (currentCubeType == CUBE_TYPE_L2) {
            changeWayOfL2Cube();
        }
        if (currentCubeType == CUBE_TYPE_L1) {
            changeWayOfL1Cube();
        }
        if (currentCubeType == CUBE_TYPE_Z) {
            changeWayOfZ1Cube();
        }
        if (currentCubeType == CUBE_TYPE_Z2) {
            changeWayOfZ2Cube();
        }
        if (currentCubeType == CUBE_TYPE_T) {
            changeWayOfTCube();
        }

        moveSupportLine();

        moveSupportCube();
    }

    private void changeWayOfTCube() {
        CubeData data = cubeTempList.get(1);
        if ((data.getCubeTurnWay() == CUBE_TURN_T_WAY2 || data.getCubeTurnWay() == CUBE_TURN_T_WAY4) && isCanTurnTWay(data)) {
            return;
        }
        if (data.getCubeTurnWay() == CUBE_TURN_T_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_T_WAY2);
            CubeTool.getTTurnWay2(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_T_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_T_WAY3);
            CubeTool.getTTurnWay3(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_T_WAY3) {
            data.setCubeTurnWay(CUBE_TURN_T_WAY4);
            CubeTool.getTTurnWay4(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_T_WAY4) {
            data.setCubeTurnWay(CUBE_TURN_T_WAY1);
            CubeTool.getTTurnWay1(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        }

        boolean isOverRight = false;
        //先判断是否有超出边界
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() >= gameViewRightX) {
                isOverRight = true;
                break;
            }
        }
        if (isOverRight) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() - latticeWidth);
                cubeData.setX(cubeData.getX() - latticeWidth);
            }
        }
        boolean isOverLeft = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() < gameViewLeftX) {
                isOverLeft = true;
                break;
            }
        }
        if (isOverLeft) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() + latticeWidth);
                cubeData.setX(cubeData.getX() + latticeWidth);
            }
        }
        boolean isOverTop = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getY() < gameViewTopY) {
                isOverTop = true;
                break;
            }
        }
        if (isOverTop){
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setY(cubeData.getCubeView().getY() + latticeHeight);
                cubeData.setY(cubeData.getY() + latticeHeight);
            }
        }

    }


    private void changeWayOfZ2Cube() {
        CubeData data = cubeTempList.get(3);

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z2_WAY1 && isCanTurnZWay(data)) {
            return;
        }
        if (data.getCubeTurnWay() == CUBE_TURN_Z2_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_Z2_WAY2);
            CubeTool.getZ2CTurnWay2(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_Z2_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_Z2_WAY1);
            CubeTool.getZ2CTurnWay1(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList,gameViewTopY);
        }

        boolean isOverRight = false;
        //先判断是否有超出边界
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() >= gameViewRightX) {
                isOverRight = true;
                break;
            }
        }
        if (isOverRight) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() - latticeWidth);
                cubeData.setX(cubeData.getX() - latticeWidth);
            }
        }
        boolean isOverLeft = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() < gameViewLeftX) {
                isOverLeft = true;
                break;
            }
        }
        if (isOverLeft) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() + latticeWidth);
                cubeData.setX(cubeData.getX() + latticeWidth);
            }
        }
        boolean isOverTop = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getY() < gameViewTopY) {
                isOverTop = true;
                break;
            }
        }
        if (isOverTop){
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setY(cubeData.getCubeView().getY() + latticeHeight);
                cubeData.setY(cubeData.getY() + latticeHeight);
            }
        }


    }


    private void changeWayOfZ1Cube() {
        CubeData data = cubeTempList.get(3);

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z1_WAY1 && isCanTurnZWay(data)) {
            return;
        }
        if (data.getCubeTurnWay() == CUBE_TURN_Z1_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_Z1_WAY2);
            CubeTool.getZ1CTurnWay2(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList,gameViewTopY);
        } else if (data.getCubeTurnWay() == CUBE_TURN_Z1_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_Z1_WAY1);
            CubeTool.getZ1CTurnWay1(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList,gameViewTopY);
        }
        boolean isOverRight = false;
        //先判断是否有超出边界
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() >= gameViewRightX) {
                isOverRight = true;
                break;
            }
        }
        if (isOverRight) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() - latticeWidth);
                cubeData.setX(cubeData.getX() - latticeWidth);
            }
        }
        boolean isOverLeft = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() < gameViewLeftX) {
                isOverLeft = true;
                break;
            }
        }
        if (isOverLeft) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() + latticeWidth);
                cubeData.setX(cubeData.getX() + latticeWidth);
            }
        }
        boolean isOverTop = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getY() < gameViewTopY) {
                isOverTop = true;
                break;
            }
        }
        if (isOverTop){
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setY(cubeData.getCubeView().getY() + latticeHeight);
                cubeData.setY(cubeData.getY() + latticeHeight);
            }
        }


    }

    private boolean isCanTurnTWay(CubeData data) {
        int turnType = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() != cubeData.getY()) {
                continue;
            }
            for (CubeData cube : cubeDataList) {
                if (cubeData.getX() >= cube.getX()) {
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth > 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 1;
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth <= 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 2;
                    break;
                }
            }
            if (turnType == 2) {
                break;
            }
        }
        return turnType == 2;
    }

    private boolean isCanTurnZWay(CubeData data) {
        int turnType = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() != cubeData.getY()) {
                continue;
            }
            for (CubeData cube : cubeDataList) {
                if (cubeData.getX() >= cube.getX()) {
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth > 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 1;
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth <= 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 2;
                    break;
                }
            }
            if (turnType == 2) {
                break;
            }
        }
        return turnType == 2;
    }

    @Override
    public void onDownButtonClickListener() {
        if (!isCanMoveOrTurnCube) {
            return;
        }
        mView.getHandler().removeCallbacks(goingDownRunnable);
        mView.startPlayMoveMusic();
        mView.startVibrator(100);
        if (cubeDataList.isEmpty()) {
            cubeToBottom();
            return;
        }
        ArrayList<CompareY> touchYList = new ArrayList<>();
        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() - cubeData.getX()) < 50 && data.getY() < cubeData.getY()) {
                    data.setX(cubeData.getX());
                    touchYList.add(new CompareY(cubeData.getY(), data.getY(), data.getX()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList) {
            if (compareY == null) {
                compareY = new CompareY(data.getExistingCubeY(), data.getCubeY(), data.getCubeX());
                compareHeight = data.getExistingCubeY() - data.getCubeY();
                continue;
            }
            if (compareHeight > data.getExistingCubeY() - data.getCubeY()) {
                compareY.setExistingCubeY(data.getExistingCubeY());
                compareY.setCubeY(data.getCubeY());
                compareHeight = compareY.getExistingCubeY() - compareY.getCubeY();
            }
        }
        if (compareY == null) {
            cubeToBottom();
            return;
        }
        float moveSpace = Tool.convertDoubleWithTwoPercent(((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight));
        ArrayList<Float> yArray = new ArrayList<>();
        for (CubeData cubeData : cubeTempList) {
            yArray.add(cubeData.getY() + (latticeHeight * moveSpace));
        }
        int index = 0;
        boolean isFoundNeedToMove = false;
        for (Float y : yArray) {
            if (y > gameViewBottomY) {
                isFoundNeedToMove = true;
            }
            index++;
        }
        index = 0;
        for (CubeData cubeData : cubeTempList) {
            mView.moveDownCube(cubeData.getCubeView(), cubeData, isFoundNeedToMove ? yArray.get(index) - latticeHeight : yArray.get(index),index,cubeTempList.size() - 1);
            index++;
        }
        MichaelLog.i("Cube straight down");
        isCanMoveOrTurnCube = false;



    }

    @Override
    public void onFinishCubeStraightDown() {
        checkRemoveLine();
    }
    private void checkRemoveLine(){
        cubeDataList.addAll(cubeTempList);
        checkNeedToRemoveLines();
    }


    @SuppressLint("CheckResult")
    @Override
    public void onLeftPressDownListener() {
        pressDownTimeMillis = System.currentTimeMillis();
        disposableCountingKeepPress = Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res >= 100) {
                        disposableCountingKeepPress.dispose();
                        startMovingCubeKeepLeft();
                    }
                });
        compositeDisposable.add(disposableCountingKeepPress);

    }

    @Override
    public void onRightPressDownListener() {
        pressDownTimeMillis = System.currentTimeMillis();
        disposableCountingKeepPress = Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res >= 100) {
                        disposableCountingKeepPress.dispose();
                        startMovingCubeKeepRight();
                    }
                });
        compositeDisposable.add(disposableCountingKeepPress);
    }




    @SuppressLint("CheckResult")
    private void startMovingCubeKeepLeft() {
        disposableKeepMoving = Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeMillis -> moveCubeSingleLeft());
        compositeDisposable.add(disposableKeepMoving);
    }

    private void moveCubeSingleLeft() {
        if (!isCanMoveOrTurnCube) {
            return;
        }
        if (isReachLeft() || cubeTempList.isEmpty()) {
            return;
        }
        for (CubeData data : cubeTempList) {
            data.getCubeView().setX(data.getX() - latticeWidth);
            data.setX(data.getX() - latticeWidth);
        }
        moveSupportLine();
        for (CubeData data : supportCubeList) {
            data.getCubeView().setX(data.getX() - latticeWidth);
            data.setX(data.getX() - latticeWidth);
        }
        moveSupportCube();
    }

    private void moveSupportCube() {
        for (int i = 0; i < cubeTempList.size(); i++) {
            supportCubeList.get(i).getCubeView().setX(cubeTempList.get(i).getX());
            supportCubeList.get(i).setX(cubeTempList.get(i).getX());
        }

        if (cubeDataList.isEmpty()) {
            moveSupportCubeToBottom();
            return;
        }
        ArrayList<CompareY> touchYList = new ArrayList<>();
        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() - cubeData.getX()) < 5 && data.getY() < cubeData.getY()) {
                    touchYList.add(new CompareY(cubeData.getY(), data.getY(), data.getX()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList) {
            if (compareY == null) {
                compareY = new CompareY(data.getExistingCubeY(), data.getCubeY(), data.getCubeX());
                compareHeight = data.getExistingCubeY() - data.getCubeY();
                continue;
            }
            if (compareHeight > data.getExistingCubeY() - data.getCubeY()) {
                compareY.setExistingCubeY(data.getExistingCubeY());
                compareY.setCubeY(data.getCubeY());
                compareHeight = compareY.getExistingCubeY() - compareY.getCubeY();
            }
        }
        if (compareY == null) {
            moveSupportCubeToBottom();
            return;
        }

        float moveSpace = Tool.convertDoubleWithTwoPercent(((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight));
        ArrayList<Float> moveYArray = new ArrayList<>();
        for (CubeData cubeData : cubeTempList) {
            moveYArray.add(cubeData.getY() + (latticeHeight * moveSpace));
        }
        int index = 0;
        boolean isFoundNeedToMove = false;
        for (Float y : moveYArray) {
            if (y > gameViewBottomY) {
                isFoundNeedToMove = true;
            }
            index++;
        }
        index = 0;
        for (CubeData cubeData : supportCubeList) {
            cubeData.getCubeView().setY(isFoundNeedToMove ? moveYArray.get(index) - latticeHeight : moveYArray.get(index));
            cubeData.setY(isFoundNeedToMove ? moveYArray.get(index) - latticeHeight : moveYArray.get(index));
            index++;
        }
    }

    private boolean isClick() {
        return (Math.abs(pressUpTimeMillis - pressDownTimeMillis) < 200);
    }

    @Override
    public void onLeftPressUpListener() {
        pressUpTimeMillis = System.currentTimeMillis();
        if (disposableKeepMoving != null) {
            disposableKeepMoving.dispose();
        }
        if (disposableCountingKeepPress != null) {
            disposableCountingKeepPress.dispose();
        }
        if (isClick()) {
            moveCubeSingleLeft();

        }

    }


    private void startMovingCubeKeepRight() {
        disposableKeepMoving = Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeMillis -> moveCubeSingleRight());
        compositeDisposable.add(disposableKeepMoving);
    }

    private void moveCubeSingleRight() {
        if (!isCanMoveOrTurnCube) {
            return;
        }
        if (isReachRight() || cubeTempList.isEmpty()) {
            return;
        }
        for (CubeData data : cubeTempList) {
            data.getCubeView().setX(data.getX() + latticeWidth);
            data.setX(data.getX() + latticeWidth);
        }
        for (CubeData data : supportCubeList) {
            data.getCubeView().setX(data.getX() + latticeWidth);
            data.setX(data.getX() + latticeWidth);
        }
        moveSupportLine();
        moveSupportCube();

    }


    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
        mView.getHandler().removeCallbacks(goingDownRunnable);
    }

    @Override
    public void onRightPressUpListener() {
        pressUpTimeMillis = System.currentTimeMillis();
        if (disposableKeepMoving != null) {
            disposableKeepMoving.dispose();
        }
        if (disposableCountingKeepPress != null) {
            disposableCountingKeepPress.dispose();
        }
        if (isClick()) {
            moveCubeSingleRight();
        }
    }

    @Override
    public void onReplayClickListener() {
        CUBE_DOWN_SPEED = 500;
        Disposable disposableAll = Observable.fromIterable(cubeDataList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::clearCurrentCube)
                .forEach(data -> mView.removeSupportCube(data.getCubeView()));
        compositeDisposable.add(disposableAll);


    }

    private void clearCurrentCube() {
        Disposable disposableCurrent = Observable.fromIterable(cubeTempList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(this::clearAllDataAndRestart)
                .forEach(data -> mView.removeSupportCube(data.getCubeView()));
        compositeDisposable.add(disposableCurrent);

    }

    private void clearAllDataAndRestart() {
        removeAllSupportCube();
        cubeDataList.clear();
        cubeTempList.clear();
        mView.resetPoint();
        mView.showPoint(0);
        createCube();
    }

    @Override
    public void onExitClickListener() {
        mView.closePage();
    }

    @Override
    public void onBackPressedListener() {
        mView.showConfirmExitDialog();
    }

    private void cubeToBottom() {
        //先取出最高的Y
        float lastY = 0;
        for (CubeData data : cubeTempList) {
            if (lastY == 0) {
                lastY = data.getY();
                continue;
            }
            if (lastY < data.getY()) {
                lastY = data.getY();
            }
        }
        int index = 0;
        float moveSpace = Tool.convertDoubleWithTwoPercent(((gameViewBottomY - lastY) / latticeHeight));
        for (CubeData cubeData : cubeTempList) {
            mView.moveDownCube(cubeData.getCubeView(), cubeData, cubeData.getY() + latticeHeight * moveSpace, index, cubeTempList.size() - 1);
            index ++;
        }
    }


    private void moveSupportCubeToBottom() {
        //先取出最高的Y
        float lastY = 0;
        for (CubeData data : cubeTempList) {
            if (lastY == 0) {
                lastY = data.getY();
                continue;
            }
            if (lastY < data.getY()) {
                lastY = data.getY();
            }
        }
        float moveSpace = Tool.convertDoubleWithTwoPercent(((gameViewBottomY - lastY) / latticeHeight));
        ArrayList<Float> yArray = new ArrayList<>();
        for (CubeData cubeData : cubeTempList) {
            yArray.add(cubeData.getY() + latticeHeight * moveSpace);
        }

        for (int i = 0; i < yArray.size(); i++) {
            supportCubeList.get(i).getCubeView().setY(yArray.get(i));
            supportCubeList.get(i).setY(yArray.get(i));
        }
    }

    private void supportCubeToBottom() {
        //先取出最高的Y
        float lastY = 0;
        for (CubeData data : supportCubeList) {
            if (lastY == 0) {
                lastY = data.getY();
                continue;
            }
            if (lastY < data.getY()) {
                lastY = data.getY();
            }
        }
        float moveSpace = Tool.convertDoubleWithTwoPercent(((gameViewBottomY - lastY) / latticeHeight));
        for (CubeData cubeData : supportCubeList) {
            cubeData.setY(cubeData.getY() + latticeHeight * moveSpace);
            mView.showSupportCube(cubeData);
        }
    }


    private void changeWayOfL1Cube() {
        CubeData data = cubeTempList.get(1);
        if ((data.getCubeTurnWay() == CUBE_TURN_L1_WAY1 || data.getCubeTurnWay() == CUBE_TURN_L1_WAY3) && isCanL1TurnWay(data)) {
            return;
        }
        if (data.getCubeTurnWay() == CUBE_TURN_L1_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_L1_WAY2);
            CubeTool.getL1CTurnWay2(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_L1_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_L1_WAY3);
            CubeTool.getL1CTurnWay3(cubeTempList, latticeHeight, latticeWidth);
        } else if (data.getCubeTurnWay() == CUBE_TURN_L1_WAY3) {
            data.setCubeTurnWay(CUBE_TURN_L1_WAY4);
            CubeTool.getL1CTurnWay4(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else {
            data.setCubeTurnWay(CUBE_TURN_L1_WAY1);
            CubeTool.getL1CTurnWay1(cubeTempList, latticeHeight, latticeWidth);
        }
        boolean isOverRight = false;
        //先判断是否有超出边界
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() >= gameViewRightX) {
                isOverRight = true;
                break;
            }
        }
        if (isOverRight) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() - latticeWidth);
                cubeData.setX(cubeData.getX() - latticeWidth);
            }
        }
        boolean isOverLeft = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() < gameViewLeftX) {
                isOverLeft = true;
                break;
            }
        }
        if (isOverLeft) {
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() + latticeWidth);
                cubeData.setX(cubeData.getX() + latticeWidth);
            }
        }
        boolean isOverTop = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getY() < gameViewTopY) {
                isOverTop = true;
                break;
            }
        }
        if (isOverTop){
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setY(cubeData.getCubeView().getY() + latticeHeight);
                cubeData.setY(cubeData.getY() + latticeHeight);
            }
        }


    }


    private void changeWayOfL2Cube() {
        CubeData data = cubeTempList.get(1);

        if ((data.getCubeTurnWay() == CUBE_TURN_L2_WAY1 || data.getCubeTurnWay() == CUBE_TURN_L2_WAY3) && isCanL2TurnWay(data)) {
            return;
        }
        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_L2_WAY2);
            CubeTool.getL2TurnWay2(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_L2_WAY3);
            CubeTool.getL2TurnWay3(cubeTempList, latticeHeight, latticeWidth);
        } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY3) {
            data.setCubeTurnWay(CUBE_TURN_L2_WAY4);
            CubeTool.getL2TurnWay4(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else {
            data.setCubeTurnWay(CUBE_TURN_L2_WAY1);
            CubeTool.getL2TurnWay1(cubeTempList, latticeHeight, latticeWidth);
        }
        boolean isOverLeft = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() <= gameViewLeftX) {
                isOverLeft = true;
                break;
            }
        }
        if (isOverLeft) {
            for (int i = 0; i < cubeTempList.size(); i++) {
                CubeData cubeData = cubeTempList.get(i);
                cubeData.getCubeView().setX(cubeData.getX() + latticeWidth);
                cubeData.setX(cubeData.getX() + latticeWidth);
            }
        }
        boolean isOverRight = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getX() >= gameViewRightX) {
                isOverRight = true;
                break;
            }
        }
        if (isOverRight) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2) {
                for (CubeData cubeData : cubeTempList) {
                    cubeData.getCubeView().setX(cubeData.getX() - latticeWidth * 2);
                    cubeData.setX(cubeData.getX() - latticeWidth * 2);
                }
                return;
            }

            for (int i = 0; i < cubeTempList.size(); i++) {
                CubeData cubeData = cubeTempList.get(i);
                float x = Math.min(cubeData.getX() - latticeWidth, gameViewRightX - latticeWidth);
                cubeData.getCubeView().setX(x);
                cubeData.setX(x);
            }
        }

        boolean isOverTop = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getY() < gameViewTopY) {
                isOverTop = true;
                break;
            }
        }
        if (isOverTop){
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setY(cubeData.getCubeView().getY() + latticeHeight);
                cubeData.setY(cubeData.getY() + latticeHeight);
            }
        }


    }

    private boolean isCanL1TurnWay(CubeData data) {
        int turnType = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() != cubeData.getY()) {
                continue;
            }
            for (CubeData cube : cubeDataList) {
                if (cubeData.getX() >= cube.getX()) {
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth > 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 1;
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth <= 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 2;
                }
            }
            if (turnType != 0) {
                break;
            }
        }
        return turnType == 2;
    }


    private boolean isCanL2TurnWay(CubeData data) {
        int turnType = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() != cubeData.getY()) {
                continue;
            }
            for (CubeData cube : cubeDataList) {
                if (cubeData.getX() >= cube.getX()) {
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth > 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 1;
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth <= 3 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    turnType = 2;
                }
            }
            if (turnType != 0) {
                break;
            }
        }
        return turnType == 2;
    }

    private void changeWayOfLongCube() {
        CubeData data = cubeTempList.get(1);

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_LONG_WAY2 && isCanTurnWay(data)) {
            return;
        }

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_LONG_WAY1) {
            CubeTool.getLongCubeTurnWay1(data, latticeHeight, cubeTempList);
        } else {
            CubeTool.getLongCubeTurnWay(data, latticeWidth, cubeTempList, cubeDataList, gameViewLeftX, gameViewRightX);
        }
        boolean isOverTop = false;
        for (CubeData cubeData : cubeTempList) {
            if (cubeData.getY() < gameViewTopY) {
                isOverTop = true;
                break;
            }
        }
        if (isOverTop){
            for (CubeData cubeData : cubeTempList) {
                cubeData.getCubeView().setY(cubeData.getCubeView().getY() + latticeHeight);
                cubeData.setY(cubeData.getY() + latticeHeight);
            }
        }




    }

    private boolean isCanTurnWay(CubeData data) {
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() != cubeData.getY()) {
                continue;
            }
            for (CubeData cube : cubeDataList) {
                if (cubeData.getX() >= cube.getX()) {
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth != 5 && cubeData.getX() < data.getX() && cube.getX() > data.getX()) {
                    return true;
                }
            }
        }

        return false;
    }

    private void createCube() {
        removeAllSupportCube();
        mView.removeSupportLine();

        currentCubeType = nextCubeType == -1 ? getRandomCuteType() : nextCubeType;
        nextCubeType = getRandomCuteType();
        showPreCube();

        switch (currentCubeType) {
            case CUBE_TYPE_SQUIRE:
                cubeTempList = CubeTool.getSquareCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_LONG:
                cubeTempList = CubeTool.getLongCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_L1:
                cubeTempList = CubeTool.getLCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_L2:
                cubeTempList = CubeTool.getL2CubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_T:
                cubeTempList = CubeTool.getTCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_Z:
                cubeTempList = CubeTool.getZCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_Z2:
                cubeTempList = CubeTool.getZ2CubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
        }
        for (CubeData data : cubeTempList) {
            mView.showCube(data);
        }
        supportCubeList = new ArrayList<>();
        for (CubeData data : cubeTempList) {
            CubeData cubeData = new CubeData(data.getX(), data.getY(), getBackground(data), data.getCubeType(), data.getWidth(), data.getHeight(), data.getCubeTurnWay());
            supportCubeList.add(cubeData);
        }
        //輔助方塊
        createSupportCube();
        //輔助線 - 目前不會考慮
//        createSupportLine();

        for (CubeData data : cubeTempList) {
            MichaelLog.i("還沒往下移動的Y : "+data.getY() + " topY : "+gameViewTopY + " 每一個高 : "+latticeHeight);
        }



        //此次產出的方塊往下降
        makeCubeGoingDown();
        //可以開始移動或是轉向
        isCanMoveOrTurnCube = true;

    }

    private void showPreCube() {
        switch (nextCubeType) {
            case CUBE_TYPE_SQUIRE:
                mView.showPreCube(R.layout.square_cube_small_layout);
                break;
            case CUBE_TYPE_LONG:
                mView.showPreCube(R.layout.long_cube_small_layout);
                break;
            case CUBE_TYPE_L1:
                mView.showPreCube(R.layout.l_cube_small_layout);
                break;
            case CUBE_TYPE_L2:
                mView.showPreCube(R.layout.l2_cube_small_layout);
                break;
            case CUBE_TYPE_T:
                mView.showPreCube(R.layout.t_cube_small_layout);
                break;
            case CUBE_TYPE_Z:
                mView.showPreCube(R.layout.z_cube_small_layout);
                break;
            case CUBE_TYPE_Z2:
                mView.showPreCube(R.layout.z2_cube_small_layout);
                break;
        }
    }

    private int getBackground(CubeData data) {
        return !SharedPreferTool.getInstance().isActiveSupportCube() ? 0 : data.getBg();
    }

    private void removeAllSupportCube() {
        if (supportCubeList == null) {
            return;
        }
        for (CubeData cubeData : supportCubeList) {
            mView.removeSupportCube(cubeData.getCubeView());
        }
        supportCubeList.clear();
    }

    private void createSupportCube() {
        if (cubeDataList.isEmpty()) {
            supportCubeToBottom();
            return;
        }
        ArrayList<CompareY> touchYList = new ArrayList<>();
        for (CubeData data : supportCubeList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() - cubeData.getX()) < 5 && data.getY() < cubeData.getY()) {
                    touchYList.add(new CompareY(cubeData.getY(), data.getY(), data.getX()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList) {
            if (compareY == null) {
                compareY = new CompareY(data.getExistingCubeY(), data.getCubeY(), data.getCubeX());
                compareHeight = data.getExistingCubeY() - data.getCubeY();
                continue;
            }
            if (compareHeight > data.getExistingCubeY() - data.getCubeY()) {
                compareY.setExistingCubeY(data.getExistingCubeY());
                compareY.setCubeY(data.getCubeY());
                compareHeight = compareY.getExistingCubeY() - compareY.getCubeY();
            }
        }
        if (compareY == null) {
            supportCubeToBottom();
            return;
        }

        float moveSpace = Tool.convertDoubleWithTwoPercent(((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight));

        ArrayList<Float> yArray = new ArrayList<>();
        for (CubeData cubeData : cubeTempList) {
            yArray.add(cubeData.getY() + (latticeHeight * moveSpace));
        }
        int index = 0;
        boolean isFoundNeedToMove = false;
        for (Float y : yArray) {
            if (y > gameViewBottomY) {
                isFoundNeedToMove = true;
                break;
            }
            index++;
        }
        index = 0;
        for (CubeData cubeData : supportCubeList) {
            cubeData.setY(isFoundNeedToMove ? yArray.get(index) - latticeHeight : yArray.get(index));
            mView.showSupportCube(cubeData);
            index++;
        }
    }

    /**
     * 目前不考慮輔助線
     */
    private void createSupportLine() {
        if (!isActiveSupportLine) {
            return;
        }
        float leftX = 0f;
        float rightX = 0f;
        float topY = 0f;
        float bottomY = 0f;
        float rightBottomY = 0f;
        for (CubeData data : cubeTempList) {
            if (leftX == 0) {
                leftX = data.getX();
                topY = data.getY();
                continue;
            }
            if (leftX > data.getX()) {
                leftX = data.getX();
                topY = data.getY();
            }
        }
        for (CubeData data : cubeTempList) {
            if (rightX == 0) {
                rightX = data.getX() + latticeWidth;
                continue;
            }
            if (rightX < data.getX() + latticeWidth) {
                rightX = data.getX() + latticeWidth;
            }
        }
        if (leftX == rightX) {
            rightX = rightX + latticeWidth;
        }
        if (cubeDataList.isEmpty()) {
            bottomY = gameViewBottomY + latticeHeight;
            rightBottomY = gameViewBottomY + latticeHeight;
        } else {
            for (CubeData data : cubeDataList) {
                if (bottomY == 0 && data.getX() == leftX) {
                    bottomY = data.getY();
                    continue;
                }
                if (bottomY > data.getY() && data.getX() == leftX) {
                    bottomY = data.getY();
                }
            }
            for (CubeData data : cubeDataList) {
                if (rightBottomY == 0 && data.getX() + latticeWidth == rightX) {
                    rightBottomY = data.getY();
                    continue;
                }
                if (rightBottomY > data.getY() && data.getX() + latticeWidth == rightX) {
                    rightBottomY = data.getY();
                }
            }
        }
        if (bottomY == 0) {
            bottomY = gameViewBottomY + latticeHeight;
        }
        if (rightBottomY == 0) {
            rightBottomY = gameViewBottomY + latticeHeight;
        }
        mView.showSupportLine(leftX, rightX, topY, bottomY, rightBottomY);
    }


    private void makeCubeGoingDown() {
        MichaelLog.i("makeCubeGoingDown");
        mView.getHandler().postDelayed(goingDownRunnable,CUBE_DOWN_SPEED);
    }

    private final Runnable goingDownRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isReachBottom = false;
            int index = 0;
            int sameLocationCount = 0;
//            printAllXY();
            for (CubeData data : cubeTempList) {
                CubeData supportCube = supportCubeList.get(index);
                if (Math.abs(supportCube.getX() - data.getX()) < 10 && Math.abs(supportCube.getY() - data.getY()) < 10) {
                    sameLocationCount++;
                }
                index++;
            }
            if (sameLocationCount != 4) {
                MichaelLog.i("sameLocationCount 不滿4");
                for (CubeData data : cubeTempList) {
                    data.setY(data.getY() + latticeHeight);
                    data.getCubeView().setY(data.getY());
                    MichaelLog.i("往下移動的Y : "+data.getY() + " topY : "+gameViewTopY);
                }
            } else {
                MichaelLog.i("已到底部");
                isReachBottom = true;
            }
            boolean isGameOver = false;
            for (CubeData data : cubeTempList) {
                if (Math.abs(data.getY() - gameViewTopY) < 5) {
                    isGameOver = true;
                    break;
                }
            }

            if (isGameOver) {
                MichaelLog.i("game over");
                mView.getHandler().removeCallbacks(this);
                if (SharedPreferTool.getInstance().getPoint() == 0) {
                    mView.savePoint();
                    mView.showGameOver(mView.getGameOverContentWithoutHistoryScore());
                } else if (mView.getCurrentPoint() > SharedPreferTool.getInstance().getPoint()) {
                    mView.savePoint();
                    mView.showGameOver(mView.getGameOverContentWithHighScore());
                } else {
                    mView.showGameOver(mView.getGameOverContentWithHistoryScore());
                }
                return;
            }

            if (isReachBottom) {
                cubeDataList.addAll(cubeTempList);
                mView.getHandler().removeCallbacks(this);
                checkNeedToRemoveLines();
            } else {
                mView.getHandler().removeCallbacks(this);
                mView.getHandler().postDelayed(goingDownRunnable, CUBE_DOWN_SPEED);
                moveSupportLine();
            }
        }
    };

    private void printAllXY() {
        int index = 0;
        for (CubeData data : cubeTempList) {
            CubeData supportCube = supportCubeList.get(index);
            MichaelLog.i("cubeX : "+data.getX() + " supportCube : "+supportCube.getX() +
                    " cubeY : "+data.getY() + " supportCube : "+supportCube.getY());
            index++;
        }
    }

    private void checkNeedToRemoveLines() {
        Collections.sort(cubeDataList);
        float y = 0;
        int removeCount = 0;
        ArrayList<CubeData> removeData = new ArrayList<>();
        ArrayList<ArrayList<CubeData>> allRemoveData = new ArrayList<>();
        int removeLineCount = 0;
        for (CubeData data : cubeDataList) {
            if (y == 0) {
                y = data.getY();
                removeData.add(data);
                continue;
            }
            if (Math.abs(data.getY() - y) < 5) {
                removeCount++;
                removeData.add(data);
                if (removeCount >= 9) {
                    allRemoveData.add(removeData);
                    removeLineCount++;
                }
            } else {
                removeCount = 0;
                removeData = new ArrayList<>();
                y = data.getY();
                removeData.add(data);
            }
        }
        boolean isNeedToMove = false;
        ArrayList<Float> removedYList = new ArrayList<>();
        float removeY = 0;
        for (ArrayList<CubeData> remove : allRemoveData) {
            for (CubeData cubeData : remove) {
                Iterator<CubeData> iterator = cubeDataList.iterator();
                while (iterator.hasNext()) {
                    CubeData data = iterator.next();
                    if (Math.abs(data.getY() - cubeData.getY()) < 5) {
                        isNeedToMove = true;
                        mView.getRootView().removeView(data.getCubeView());
                        iterator.remove();
                        if (removeY == 0) {
                            removeY = cubeData.getY();
                            removedYList.add(removeY);
                        } else if (Math.abs(removeY - cubeData.getY()) > 5) {
                            removeY = cubeData.getY();
                            removedYList.add(removeY);
                        }
                        break;
                    }
                }
            }
        }
        if (isNeedToMove) {
            startToMoveAllCube(removedYList);
        } else {
            createCube();
        }
        if (removeLineCount == 0) {
            return;
        }
        mView.startPlayUpgradeMusic();
        mView.showPoint(removeLineCount * 1000);
        //速度會越變越快
        if (mView.getCurrentPoint() - currentPoint >= 5000 && mView.getCurrentPoint() - currentPoint != 0) {
            currentPoint = mView.getCurrentPoint();
            CUBE_DOWN_SPEED = CUBE_DOWN_SPEED -  50;
            if (CUBE_DOWN_SPEED <= 100){
                CUBE_DOWN_SPEED = 100;
            }
        }
    }

    private void startToMoveAllCube(ArrayList<Float> removedYList) {
        MichaelLog.i("removeList size : "+removedYList.size());
        for (Float y : removedYList) {
            for (CubeData cubeData : cubeDataList) {
                if (cubeData.getY() < y) {
                    cubeData.setY(cubeData.getY() + latticeHeight);
                }
            }
        }
        for (CubeData cubeData : cubeDataList) {
            cubeData.getCubeView().animate()
                    .y(cubeData.getY())
                    .setDuration(100)
                    .withEndAction(() -> cubeData.getCubeView().setY(cubeData.getY()))
                    .start();
        }
        createCube();

    }


    private int getRandomCuteType() {
        return (int) (Math.random() * 7);
//        return CUBE_TYPE_Z2;
    }

}
