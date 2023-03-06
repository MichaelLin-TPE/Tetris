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
import android.util.Log;

import com.game.tetris.MichaelLog;
import com.game.tetris.R;
import com.game.tetris.bean.CompareY;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;
import com.game.tetris.tool.CubeTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class GamePresenterImpl implements GamePresenter {

    private final GameVu mView;

    private final ArrayList<LatticeData> latticeDataList = new ArrayList<>();
    private final ArrayList<CubeData> cubeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeTempList,supportCubeList;
    private float latticeWidth, latticeHeight; // 格子的寬跟高
    private float gameViewBottomY, gameViewTopY, gameViewLeftX, gameViewRightX;
    private int currentCubeType;
    private static final int CUBE_DOWN_SPEED = 500;
    private boolean isCanMoveOrTurnCube = false;
    private long pressDownTimeMillis = 0, pressUpTimeMillis = 0;
    private Disposable disposableKeepMoving; //控制左右按鈕按壓後的TIMER
    private Disposable disposableCountingKeepPress;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isActiveSupport = false;
    public GamePresenterImpl(GameVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCreateGameView() {
        mView.startPlayBackgroundMusic();
        mView.createGameViewBackground();

    }

    @Override
    public void createLatticeDataList(float x, float y, int right, int left, int bottom) {
        float gameViewX = x + 30;
        float gameViewY = y + 30;
        latticeWidth = ((right - 30) - (left + 30)) / 10f;
        latticeHeight = ((bottom - 30) - (y + 30)) / 20f;
        MichaelLog.i("gameViewX : " + gameViewX + " latticeWidth : " + latticeWidth + " latticeHeight : " + latticeHeight);

        ArrayList<LatticeData> dataList = new ArrayList<>();
        for (int straight = 0; straight < 20; straight++) {
            for (int row = 0; row < 10; row++) {
                dataList.add(new LatticeData(gameViewX + latticeWidth * row, gameViewY + (latticeHeight * straight),
                        straight,
                        straight % 2 == 0 ?
                                row % 2 == 0 ? R.drawable.lattice_bg : R.drawable.lattice_bg1 :
                                row % 2 == 0 ? R.drawable.lattice_bg1 : R.drawable.lattice_bg
                ));
            }
        }

        latticeDataList.addAll(dataList);
        for (LatticeData data : latticeDataList) {
            mView.showLattice(data, latticeWidth, latticeHeight);
        }
        gameViewBottomY = latticeDataList.get(latticeDataList.size() - 1).getY();
        gameViewTopY = latticeDataList.get(0).getY();
        gameViewLeftX = left + 30;
        gameViewRightX = right - 30;
        //開始產出方塊
        createCube();


    }


    private boolean isReachLeft() {
        boolean isReachLeft = false;
        for (CubeData data : cubeTempList) {
            if (data.getX() <= gameViewLeftX) {
                isReachLeft = true;
                break;
            }
        }
        for (CubeData movingCube : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (movingCube.getX() - latticeWidth == cubeData.getX() && cubeData.getY() == movingCube.getY()) {
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
            if (data.getX() + latticeWidth >= gameViewRightX) {
                isReachRight = true;
                break;
            }
        }

        for (CubeData movingCube : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (movingCube.getX() + latticeWidth == cubeData.getX() && cubeData.getY() == movingCube.getY()) {
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
        if (!isActiveSupport){
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
            MichaelLog.i("不能轉");
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
    }


    private void changeWayOfZ2Cube() {
        CubeData data = cubeTempList.get(3);

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z2_WAY1 && isCanTurnZWay(data)) {
            MichaelLog.i("不能轉");
            return;
        }
        if (data.getCubeTurnWay() == CUBE_TURN_Z2_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_Z2_WAY2);
            CubeTool.getZ2CTurnWay2(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_Z2_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_Z2_WAY1);
            CubeTool.getZ2CTurnWay1(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
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

    }


    private void changeWayOfZ1Cube() {
        CubeData data = cubeTempList.get(3);

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z1_WAY1 && isCanTurnZWay(data)) {
            MichaelLog.i("不能轉");
            return;
        }
        if (data.getCubeTurnWay() == CUBE_TURN_Z1_WAY1) {
            data.setCubeTurnWay(CUBE_TURN_Z1_WAY2);
            CubeTool.getZ1CTurnWay2(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
        } else if (data.getCubeTurnWay() == CUBE_TURN_Z1_WAY2) {
            data.setCubeTurnWay(CUBE_TURN_Z1_WAY1);
            CubeTool.getZ1CTurnWay1(data, cubeTempList, latticeHeight, latticeWidth, cubeDataList);
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
                    MichaelLog.i("size : " + (cube.getX() - cubeData.getX()) / latticeWidth);
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
        MichaelLog.i("turnType : " + turnType);
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
                    MichaelLog.i("size : " + (cube.getX() - cubeData.getX()) / latticeWidth);
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
        MichaelLog.i("turnType : " + turnType);
        return turnType == 2;
    }

    @Override
    public void onDownButtonClickListener() {
        if (!isCanMoveOrTurnCube) {
            return;
        }

        mView.startPlayMoveMusic();
        mView.startVibrator(100);
        if (cubeDataList.isEmpty()) {
            cubeToBottom();
            return;
        }
        ArrayList<CompareY> touchYList = new ArrayList<>();
        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (data.getX() == cubeData.getX() && data.getY() < cubeData.getY()) {
                    touchYList.add(new CompareY(cubeData.getY(), data.getY()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList) {
            if (compareY == null) {
                compareY = new CompareY(data.getExistingCubeY(), data.getCubeY());
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
        int moveSpace = (int) ((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight);
        for (CubeData cubeData : cubeTempList) {
            cubeData.getCubeView().setY(cubeData.getY() + (latticeHeight * moveSpace));
            cubeData.setY(cubeData.getY() + (latticeHeight * moveSpace));
        }

        isCanMoveOrTurnCube = false;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onLeftPressDownListener() {
        pressDownTimeMillis = System.currentTimeMillis();
        MichaelLog.i("keepLeft");
        disposableCountingKeepPress = Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res >= 100){
                        disposableCountingKeepPress.dispose();
                        startMovingCubeKeepLeft();
                    }
                });
        compositeDisposable.add(disposableCountingKeepPress);

    }
    @Override
    public void onRightPressDownListener() {
        pressDownTimeMillis = System.currentTimeMillis();
        MichaelLog.i("keepLeft");
        disposableCountingKeepPress = Observable.interval(1, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    if (res >= 100){
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
        for (CubeData data : supportCubeList){
            data.getCubeView().setX(data.getX() - latticeWidth);
            data.setX(data.getX() - latticeWidth);
        }
        moveSupportCube();
    }

    private void moveSupportCube() {

        for (int i = 0 ; i < cubeTempList.size() ; i++){
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
                if (data.getX() == cubeData.getX() && data.getY() < cubeData.getY()) {
                    touchYList.add(new CompareY(cubeData.getY(), data.getY()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList) {
            if (compareY == null) {
                compareY = new CompareY(data.getExistingCubeY(), data.getCubeY());
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
        int moveSpace = (int) ((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight);
        ArrayList<Float> moveYArray = new ArrayList<>();
        for (CubeData cubeData : cubeTempList) {
            moveYArray.add(cubeData.getY() + (latticeHeight * moveSpace));
        }
        int index = 0;
        for (CubeData cubeData : supportCubeList){
            cubeData.getCubeView().setY(moveYArray.get(index));
            cubeData.setY(moveYArray.get(index));
            index ++;
        }
    }

    private boolean isClick() {
        MichaelLog.i("間距 : " + Math.abs(pressUpTimeMillis - pressDownTimeMillis));
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
            MichaelLog.i("單下點擊");
            moveCubeSingleLeft();

        }

    }


    private void startMovingCubeKeepRight() {
        disposableKeepMoving = Observable.interval(100, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeMillis -> {
                    moveCubeSingleRight();
                });
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
        for (CubeData data : supportCubeList){
            data.getCubeView().setX(data.getX() + latticeWidth);
            data.setX(data.getX() + latticeWidth);
        }
        moveSupportLine();
        moveSupportCube();

    }


    @Override
    public void onDestroy() {
        compositeDisposable.dispose();
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
            MichaelLog.i("單下點擊");
            moveCubeSingleRight();
        }
    }

    @Override
    public void onReplayClickListener() {
        Disposable disposableAll = Observable.just(cubeDataList)
                .subscribeOn(Schedulers.io())
                .map(new Function<ArrayList<CubeData>, CubeData>() {
                    @Override
                    public CubeData apply(ArrayList<CubeData> cubeData) throws Exception {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res ->{
                    mView.removeSupportCube(res.getCubeView());
                });
        compositeDisposable.add(disposableAll);

        Disposable disposableCurrent = Observable.just(cubeTempList)
                .subscribeOn(Schedulers.io())
                .map(new Function<ArrayList<CubeData>, CubeData>() {
                    @Override
                    public CubeData apply(ArrayList<CubeData> cubeData) throws Exception {
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res ->{
                    mView.removeSupportCube(res.getCubeView());
                });
        compositeDisposable.add(disposableCurrent);
        removeAllSupportCube();
    }

    private CubeData createView(CubeData cubeData) {
        return null;
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
        int moveSpace = (int) ((gameViewBottomY - lastY) / latticeHeight);
        for (CubeData cubeData : cubeTempList) {
            cubeData.getCubeView().setY(cubeData.getY() + latticeHeight * moveSpace);
            cubeData.setY(cubeData.getY() + latticeHeight * moveSpace);
            MichaelLog.i("cubeY : " + cubeData.getY());
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
        int moveSpace = (int) ((gameViewBottomY - lastY) / latticeHeight);
        ArrayList<Float> yArray = new ArrayList<>();
        for (CubeData cubeData : cubeTempList) {
            yArray.add(cubeData.getY() + latticeHeight * moveSpace);
        }
        
        for (int i = 0 ; i < yArray.size() ; i++){
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
        int moveSpace = (int) ((gameViewBottomY - lastY) / latticeHeight);
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
        Log.i("Michael", "turnWay type : " + data.getCubeTurnWay());
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
                MichaelLog.i("size : " + ((cube.getX() - cubeData.getX()) / latticeWidth) + " first : " + cube.getX() + " second : " + cubeData.getX() + " dataX : " + data.getX());
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
                MichaelLog.i("size : " + ((cube.getX() - cubeData.getX()) / latticeWidth) + " first : " + cube.getX() + " second : " + cubeData.getX() + " dataX : " + data.getX());
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
        currentCubeType = getRandomCuteType();
        MichaelLog.i("currentCubeType : " + currentCubeType);
        switch (currentCubeType) {
            case CUBE_TYPE_SQUIRE:
                cubeTempList = CubeTool.getSquareCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getSquareCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_LONG:
                cubeTempList = CubeTool.getLongCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getLongCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_L1:
                cubeTempList = CubeTool.getLCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getLCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_L2:
                cubeTempList = CubeTool.getL2CubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getL2CubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_T:
                cubeTempList = CubeTool.getTCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getTCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_Z:
                cubeTempList = CubeTool.getZCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getZCubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
            case CUBE_TYPE_Z2:
                cubeTempList = CubeTool.getZ2CubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                supportCubeList = CubeTool.getZ2CubeData(currentCubeType, latticeDataList, latticeWidth, latticeHeight);
                break;
        }
        for (CubeData data : cubeTempList) {
            mView.showCube(data);
        }
        //此次產出的方塊往下降
        makeCubeGoingDown();
        //可以開始移動或是轉向
        isCanMoveOrTurnCube = true;
        //輔助方塊
        createSupportCube();
        //輔助線
        createSupportLine();
    }

    private void removeAllSupportCube() {
        if (supportCubeList == null){
            return;
        }
        for (CubeData cubeData : supportCubeList){
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
                if (data.getX() == cubeData.getX() && data.getY() < cubeData.getY()) {
                    touchYList.add(new CompareY(cubeData.getY(), data.getY()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList) {
            if (compareY == null) {
                compareY = new CompareY(data.getExistingCubeY(), data.getCubeY());
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
        int moveSpace = (int) ((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight);
        for (CubeData cubeData : supportCubeList) {
            cubeData.setY(cubeData.getY() + (latticeHeight * moveSpace));
            mView.showSupportCube(cubeData);
        }
    }

    private void createSupportLine() {
        if (!isActiveSupport){
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
        mView.getHandler().post(goingDownRunnable);
    }

    private final Runnable goingDownRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isReachBottom = false;
            for (CubeData data : cubeTempList) {
                if (data.getCubeType() == CUBE_TYPE_SQUIRE) {

                    if (isSquareTouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanSquareMoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_LONG) {

                    if (isTouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanLongMoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_L2) {
                    if (isL2TouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanL2MoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_L1) {
                    if (isL1TouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanL1MoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_Z) {

                    if (isZ1TouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanZ1MoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_Z2) {
                    if (isZ2TouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanZ2MoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);
                } else if (data.getCubeType() == CUBE_TYPE_T) {
                    if (isTTouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanTMoveDown()) {
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);
                }
            }
            boolean isGameOver = false;
            for (CubeData data : cubeTempList) {
                if (data.getY() <= gameViewTopY) {
                    isGameOver = true;
                    break;
                }
            }

            if (isGameOver) {
                mView.getHandler().removeCallbacks(this);
                mView.showGameOver(mView.getGameOverContent());
                return;
            }

            if (isReachBottom) {
                cubeDataList.addAll(cubeTempList);
                mView.getHandler().removeCallbacks(this);
                checkNeedToRemoveLines();

            } else {
                mView.getHandler().postDelayed(goingDownRunnable, CUBE_DOWN_SPEED);
                moveSupportLine();
            }
        }
    };

    private boolean isCanL1MoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        int index = 0;
        for (CubeData data : cubeDataList) {
            index = 0;
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
                index++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            cubeTempList.get(1).getCubeView().setY(CubeTool.getMainL1Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));
            cubeTempList.get(1).setY(CubeTool.getMainL1Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));

            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY1) {

                CubeTool.getL1CTurnWay1(cubeTempList, latticeHeight, latticeWidth);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY2) {

                CubeTool.getL1CTurnWay2(cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY3) {

                CubeTool.getL1CTurnWay3(cubeTempList, latticeHeight, latticeWidth);

            } else {

                CubeTool.getL1CTurnWay4(cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            }

        }
        return isCanMoveDown;
    }


    private boolean isCanZ1MoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        int index = 0;
        for (CubeData data : cubeDataList) {
            index = 0;
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
                index++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            cubeTempList.get(3).getCubeView().setY(CubeTool.getMainZ1Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));
            cubeTempList.get(3).setY(CubeTool.getMainZ1Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));

            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z1_WAY1) {

                CubeTool.getZ1CTurnWay1(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z1_WAY2) {

                CubeTool.getZ1CTurnWay2(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            }

        }
        return isCanMoveDown;
    }


    private boolean isCanTMoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        int index = 0;
        for (CubeData data : cubeDataList) {
            index = 0;
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    MichaelLog.i("moving cube Y : " + cubeData.getY() + " cubeY : " + data.getY() + " index : " + index);
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
                index++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            cubeTempList.get(1).getCubeView().setY(CubeTool.getMainZ1Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));
            cubeTempList.get(1).setY(CubeTool.getMainZ1Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));

            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY1) {

                CubeTool.getTTurnWay1(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY2) {

                CubeTool.getTTurnWay2(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY3) {

                CubeTool.getTTurnWay3(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY4) {

                CubeTool.getTTurnWay4(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            }

        }
        return isCanMoveDown;
    }

    private boolean isCanZ2MoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        int index = 0;
        for (CubeData data : cubeDataList) {
            index = 0;
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    MichaelLog.i("moving cube Y : " + cubeData.getY() + " cubeY : " + data.getY() + " index : " + index);
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
                index++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            cubeTempList.get(3).getCubeView().setY(CubeTool.getMainZ2Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));
            cubeTempList.get(3).setY(CubeTool.getMainZ2Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));

            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z2_WAY1) {

                CubeTool.getZ2CTurnWay1(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z2_WAY2) {

                CubeTool.getZ2CTurnWay2(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            }

        }
        return isCanMoveDown;
    }


    private boolean isCanL2MoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        int index = 0;
        for (CubeData data : cubeDataList) {
            index = 0;
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
                index++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            cubeTempList.get(1).getCubeView().setY(CubeTool.getMainL2Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));
            cubeTempList.get(1).setY(CubeTool.getMainL2Cube(cubeY, cubeTempList.get(index), latticeHeight, latticeWidth, index));
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY1) {
                CubeTool.getL2TurnWay1(cubeTempList, latticeHeight, latticeWidth);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2) {
                CubeTool.getL2TurnWay2(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY3) {
                CubeTool.getL2TurnWay3(cubeTempList, latticeHeight, latticeWidth);
            } else {
                CubeTool.getL2TurnWay4(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            }

        }
        return isCanMoveDown;
    }

    private boolean isL2TouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                Log.i("Michael", "Y : " + data.getY() + " height : " + latticeHeight + " bottomY : " + gameViewBottomY);
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY1) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL2TurnWay1(cubeTempList, latticeHeight, latticeWidth);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2) {
                CubeTool.getL2TurnWay2(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY3) {
                CubeTool.getL2TurnWay3(cubeTempList, latticeHeight, latticeWidth);
            } else {
                CubeTool.getL2TurnWay4(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            }
        }
        return isReachBottom;
    }

    private boolean isL1TouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                Log.i("Michael", "Y : " + data.getY() + " height : " + latticeHeight + " bottomY : " + gameViewBottomY);
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY1) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL1CTurnWay1(cubeTempList, latticeHeight, latticeWidth);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY2) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(1).setY(gameViewBottomY - latticeHeight);
                CubeTool.getL1CTurnWay2(cubeTempList, latticeHeight, latticeWidth, cubeDataList);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY3) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL1CTurnWay3(cubeTempList, latticeHeight, latticeWidth);
            } else {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL1CTurnWay4(cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            }
        }
        return isReachBottom;
    }

    private boolean isZ1TouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z1_WAY1) {
                cubeTempList.get(3).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(3).setY(gameViewBottomY - latticeHeight);
                CubeTool.getZ1CTurnWay1(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z1_WAY2) {
                cubeTempList.get(3).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(3).setY(gameViewBottomY - latticeHeight);
                CubeTool.getZ1CTurnWay2(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            }
        }
        return isReachBottom;
    }

    private boolean isZ2TouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z2_WAY1) {
                cubeTempList.get(3).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(3).setY(gameViewBottomY - latticeHeight);
                CubeTool.getZ2CTurnWay1(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_Z2_WAY2) {
                cubeTempList.get(3).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(3).setY(gameViewBottomY - latticeHeight);
                CubeTool.getZ2CTurnWay2(cubeTempList.get(3), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            }
        }
        return isReachBottom;
    }

    private boolean isTTouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY1) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getTTurnWay1(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY2) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(1).setY(gameViewBottomY - latticeHeight);
                CubeTool.getTTurnWay2(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_T_WAY3) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(1).setY(gameViewBottomY - latticeHeight);
                CubeTool.getTTurnWay3(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            } else {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(1).setY(gameViewBottomY - latticeHeight);
                CubeTool.getTTurnWay4(cubeTempList.get(1), cubeTempList, latticeHeight, latticeWidth, cubeDataList);
            }
        }
        return isReachBottom;
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

            if (y == data.getY()) {
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
                    if (data.getY() == cubeData.getY()) {
                        isNeedToMove = true;
                        mView.getRootView().removeView(data.getCubeView());
                        iterator.remove();
                        if (removeY == 0) {
                            removeY = cubeData.getY();
                            removedYList.add(removeY);
                        } else if (removeY != cubeData.getY()) {
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


    }

    private void startToMoveAllCube(ArrayList<Float> removedYList) {
        for (Float y : removedYList) {
            for (CubeData cubeData : cubeDataList) {
                if (cubeData.getY() < y) {
                    cubeData.getCubeView().setY(cubeData.getY() + latticeHeight);
                    cubeData.setY(cubeData.getY() + latticeHeight);
                }
            }
        }
        createCube();

    }


    private boolean isTouchBottom() {
        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_LONG_WAY1) {
                Log.i("Michael", "reach bottom : CUBE_TURN_LONG_WAY1");
                for (CubeData data : cubeTempList) {
                    if (data.getCubeTurnWay() == CUBE_TURN_LONG_WAY1) {
                        data.setY(gameViewBottomY);
                        data.getCubeView().setY(gameViewBottomY);
                    }
                }
            } else {
                Log.i("Michael", "reach bottom : CUBE_TURN_LONG_WAY2");
                float x = cubeTempList.get(1).getX();
                for (int i = 0; i < cubeTempList.size(); i++) {
                    CubeData data = cubeTempList.get(i);
                    data.getCubeView().setY(gameViewBottomY - (latticeHeight * i));
                    data.setY(gameViewBottomY - (latticeHeight * i));
                    data.getCubeView().setX(x);
                    data.setX(x);
                }
            }

        }
        return isReachBottom;
    }


    private boolean isSquareTouchBottom() {
        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom) {
            CubeTool.getSquareCube(gameViewBottomY, cubeTempList, latticeHeight);
        }
        return isReachBottom;
    }


    private boolean isCanSquareMoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        for (CubeData data : cubeDataList) {
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            CubeTool.getSquareCube(cubeY, cubeTempList, latticeHeight);
        }

        return isCanMoveDown;
    }

    private boolean isCanLongMoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        for (CubeData data : cubeDataList) {
            for (CubeData cubeData : cubeTempList) {
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown) {
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_LONG_WAY1) {
                for (CubeData cubeData : cubeTempList) {
                    cubeData.getCubeView().setY(cubeY);
                    cubeData.setY(cubeY);
                }
            } else {
                for (int i = 0; i < cubeTempList.size(); i++) {
                    CubeData data = cubeTempList.get(i);
                    data.getCubeView().setY(cubeY - (latticeHeight * i));
                    data.setY(cubeY - (latticeHeight * i));
                }
            }

        }

        return isCanMoveDown;
    }


    private int getRandomCuteType() {
        return (int) (Math.random() * 2) + 3;
//        return (int) (Math.random() * 7);

    }
}
