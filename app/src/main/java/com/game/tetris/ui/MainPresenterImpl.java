package com.game.tetris.ui;

import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY3;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_L2_WAY4;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_LONG_WAY1;
import static com.game.tetris.tool.CubeTool.CUBE_TURN_LONG_WAY2;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_L1;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_L2;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_LONG;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_T;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_Z;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_Z2;

import android.util.Log;

import com.game.tetris.MichaelLog;
import com.game.tetris.R;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;
import com.game.tetris.tool.CubeTool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MainPresenterImpl implements MainPresenter {

    private final MainVu mView;

    private final ArrayList<LatticeData> latticeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeTempList;
    private float latticeWidth, latticeHeight; // 格子的寬跟高
    private float gameViewBottomY, gameViewTopY, gameViewLeftX, gameViewRightX;
    private int currentCubeType;


    public MainPresenterImpl(MainVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCreateGameView() {
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

    @Override
    public void onLeftButtonClickListener() {
        if (isReachLeft() || cubeTempList.isEmpty()) {
            return;
        }
        for (CubeData data : cubeTempList) {
            data.getCubeView().setX(data.getX() - latticeWidth);
            data.setX(data.getX() - latticeWidth);
        }
    }

    private boolean isReachLeft() {
        boolean isReachLeft = false;
        for (CubeData data : cubeTempList) {
            if (data.getX() <= gameViewLeftX) {
                isReachLeft = true;
                break;
            }
        }
        for (CubeData cubeData : cubeTempList) {
            for (CubeData data : cubeDataList) {
                if (cubeData.getX() - latticeWidth <= data.getX() && cubeData.getY() == data.getY()) {
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
        for (CubeData cubeData : cubeTempList) {
            for (CubeData data : cubeDataList) {
                if (cubeData.getX() + latticeWidth >= data.getX() && cubeData.getY() == data.getY()) {
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

    @Override
    public void onRightButtonClickListener() {
        if (isReachRight() || cubeTempList.isEmpty()) {
            return;
        }
        for (CubeData data : cubeTempList) {
            data.getCubeView().setX(data.getX() + latticeWidth);
            data.setX(data.getX() + latticeWidth);
        }
    }

    @Override
    public void onTurnCubeButtonClickListener() {
        if (currentCubeType == CUBE_TYPE_LONG) {
            changeWayOfLongCube();
        }
        if (currentCubeType == CUBE_TYPE_L2) {
            changeWayOfL2Cube();
        }
    }

    private void changeWayOfL2Cube() {
        CubeData data = cubeTempList.get(1);

        if (isCanL2TurnWay(data)) {
            return;
        }
        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY1) {
            CubeTool.getL2TurnWay2(cubeTempList, latticeHeight, latticeWidth);
        } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2) {
            CubeTool.getL2TurnWay3(cubeTempList, latticeHeight, latticeWidth);
        } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY3) {
            CubeTool.getL2TurnWay4(cubeTempList, latticeHeight, latticeWidth);
        } else {
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

    private boolean isCanL2TurnWay(CubeData data) {

        for (CubeData cubeData : cubeDataList) {
            if (data.getCubeTurnWay() == CUBE_TURN_L2_WAY1 && cubeData.getX() <= data.getX() + latticeWidth * 2 && data.getY() == cubeData.getY()) {
                return true;
            }
            if (data.getCubeTurnWay() == CUBE_TURN_L2_WAY2 && cubeData.getX() <= data.getX() + latticeWidth && data.getY() == cubeData.getY()) {
                return true;
            }
            if (data.getCubeTurnWay() == CUBE_TURN_L2_WAY3 && cubeData.getX() <= data.getX() - latticeWidth * 2 && data.getY() == cubeData.getY()) {
                return true;
            }
            if (data.getCubeTurnWay() == CUBE_TURN_L2_WAY4 && cubeData.getX() <= data.getX() + latticeWidth && data.getY() == cubeData.getY()) {
                return true;
            }
        }

        return false;
    }

    private void changeWayOfLongCube() {
        CubeData data = cubeTempList.get(1);

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_LONG_WAY2 && isCanTurnWay(data)) {
            return;
        }

        if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_LONG_WAY1) {
            cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight);
            cubeTempList.get(0).getCubeView().setX(data.getX());
            cubeTempList.get(0).setY(data.getY() + latticeHeight);
            cubeTempList.get(0).setX(data.getX());
            cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_LONG_WAY2);

            cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight * 2);
            cubeTempList.get(2).getCubeView().setX(data.getX());
            cubeTempList.get(2).setY(data.getY() + latticeHeight * 2);
            cubeTempList.get(2).setX(data.getX());
            cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_LONG_WAY2);

            cubeTempList.get(3).getCubeView().setY(data.getY() + latticeHeight * 3);
            cubeTempList.get(3).getCubeView().setX(data.getX());
            cubeTempList.get(3).setY(data.getY() + latticeHeight * 3);
            cubeTempList.get(3).setX(data.getX());
            cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_LONG_WAY2);
        } else {
            cubeTempList.get(0).getCubeView().setY(data.getY());
            cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
            cubeTempList.get(0).setY(data.getY());
            cubeTempList.get(0).setX(data.getX() - latticeWidth);
            cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_LONG_WAY1);

            cubeTempList.get(2).getCubeView().setY(data.getY());
            cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
            cubeTempList.get(2).setY(data.getY());
            cubeTempList.get(2).setX(data.getX() + latticeWidth);
            cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_LONG_WAY1);

            cubeTempList.get(3).getCubeView().setY(data.getY());
            cubeTempList.get(3).getCubeView().setX(data.getX() + latticeWidth * 2);
            cubeTempList.get(3).setY(data.getY());
            cubeTempList.get(3).setX(data.getX() + latticeWidth * 2);
            cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_LONG_WAY1);
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
                cubeData.getCubeView().setX(gameViewLeftX + (latticeHeight * i));
                cubeData.setX(gameViewLeftX + (latticeHeight * i));
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
            for (int i = 0; i < cubeTempList.size(); i++) {
                CubeData cubeData = cubeTempList.get(i);
                cubeData.getCubeView().setX((gameViewRightX - latticeWidth) - (latticeWidth * i));
                cubeData.setX((gameViewRightX - latticeWidth) - (latticeWidth * i));
            }
        }

    }

    private boolean isCanTurnWay(CubeData data) {
        float firstX = data.getX() - latticeWidth;
        float thirdX = data.getX() + latticeWidth;
        float fourthX = data.getX() + latticeWidth * 2;
        for (CubeData cubeData : cubeDataList) {
            if (firstX == cubeData.getX() && data.getY() == cubeData.getY()) {
                return true;
            }
            if (thirdX == cubeData.getX() && data.getY() == cubeData.getY()) {
                return true;
            }
            if (fourthX == cubeData.getX() && data.getY() == cubeData.getY()) {
                return true;
            }
        }

        return false;
    }

    private void createCube() {
        currentCubeType = getRandomCuteType();
        MichaelLog.i("currentCubeType : " + currentCubeType);
        switch (currentCubeType) {
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
        //此次產出的方塊往下降
        makeCubeGoingDown();
    }


    private void makeCubeGoingDown() {
        mView.getHandler().post(goingDownRunnable);
    }

    private final Runnable goingDownRunnable = new Runnable() {
        @Override
        public void run() {
            boolean isReachBottom = false;
            for (CubeData data : cubeTempList) {
                if (data.getCubeType() == CUBE_TYPE_LONG) {

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
                    if (!isCanL2MoveDown()){
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);
                } else if (data.getCubeType() == CUBE_TYPE_L1 ||
                        data.getCubeType() == CUBE_TYPE_T || data.getCubeType() == CUBE_TYPE_Z ||
                        data.getCubeType() == CUBE_TYPE_Z2) {
                    if (((data.getY() + latticeHeight) > gameViewBottomY || isReachBottom)) {
                        isReachBottom = true;
                        continue;
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
                mView.showGameOver();
                return;
            }

            if (isReachBottom) {
                cubeDataList.addAll(cubeTempList);
                mView.getHandler().removeCallbacks(this);
                checkNeedToRemoveLines();

            } else {
                mView.getHandler().postDelayed(goingDownRunnable, 200);
            }
        }
    };

    private boolean isCanL2MoveDown() {
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




        return isCanMoveDown;
    }

    private boolean isL2TouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                Log.i("Michael","Y : "+data.getY() +" height : "+latticeHeight+ " bottomY : "+gameViewBottomY);
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom){
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY1) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL2TurnWay1(cubeTempList, latticeHeight, latticeWidth);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2) {
                CubeTool.getL2TurnWay2(cubeTempList, latticeHeight, latticeWidth);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY3) {
                CubeTool.getL2TurnWay3(cubeTempList, latticeHeight, latticeWidth);
            } else {
                CubeTool.getL2TurnWay4(cubeTempList, latticeHeight, latticeWidth);
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

    private void printCubeLog() {
        for (CubeData data : cubeDataList) {
            MichaelLog.i("cube location x : " + data.getX() + " y : " + data.getY());
        }

    }

    //暫時用不到
    private void saveCubeDataToGameView() {
        for (CubeData data : cubeDataList) {
            for (LatticeData latticeData : latticeDataList) {
                if (data.getY() == latticeData.getY() && data.getX() == latticeData.getX()) {
                    latticeData.setHasCube(true);
                }
                break;
            }
        }
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

    private float getBottomY() {
        float y = 0;
        for (CubeData data : cubeDataList) {
            if (y == 0) {
                y = data.getY();
                continue;
            }
            if (data.getY() < y) {
                y = data.getY();
            }
        }
        return y - latticeHeight;
    }


    private int getRandomCuteType() {
        return CUBE_TYPE_L2;
//        return (int) (Math.random() * ((5) + 1));
    }
}
