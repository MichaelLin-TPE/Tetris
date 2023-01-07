package com.game.tetris.ui;

import com.game.tetris.MichaelLog;
import com.game.tetris.R;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;
import com.game.tetris.tool.CubeTool;

import java.util.ArrayList;

public class MainPresenterImpl implements MainPresenter {

    private final MainVu mView;

    private final ArrayList<LatticeData> latticeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeTempList;
    private float latticeWidth, latticeHeight; // 格子的寬跟高
    private final int CUBE_TYPE_LONG = 0;
    private final int CUBE_TYPE_Z2 = 5;
    private final int CUBE_TYPE_Z = 4;
    private final int CUBE_TYPE_L1 = 1;
    private final int CUBE_TYPE_L2 = 2;
    private final int CUBE_TYPE_T = 3;
    private float gameViewBottomY, gameViewTopY, gameViewLeftX, gameViewRightX;


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
        MichaelLog.i("gameViewX : " + gameViewX + " latticeWidth : " + latticeWidth);

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

    private void createCube() {
        int currentCubeType = getRandomCuteType();
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

                    if ((data.getY() + latticeHeight) > gameViewBottomY) {
                        isReachBottom = true;
                        continue;
                    }
                    if (!isCanMoveDown()){
                        isReachBottom = true;
                        continue;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_L1 || data.getCubeType() == CUBE_TYPE_L2 ||
                        data.getCubeType() == CUBE_TYPE_T || data.getCubeType() == CUBE_TYPE_Z ||
                        data.getCubeType() == CUBE_TYPE_Z2) {
                    if (((data.getY() + latticeHeight) > gameViewBottomY && !isReachBottom)) {
                        isReachBottom = true;
                        continue;
                    }
                    if (isReachBottom) {
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
                saveCubeDataToGameView();
                mView.getHandler().removeCallbacks(this);
                createCube();
            } else {
                mView.getHandler().postDelayed(goingDownRunnable, 200);
            }
        }
    };

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

    private boolean isCanMoveDown() {
        boolean isCanMoveDown = true;
        float cubeY = 0;
        for (CubeData data : cubeDataList) {
            for (CubeData cubeData : cubeTempList){
                if (data.getX() == cubeData.getX() && data.getY() == cubeData.getY() + latticeHeight) {
                    cubeY = data.getY() - latticeHeight;
                    isCanMoveDown = false;
                    break;
                }
            }
            if (!isCanMoveDown){
                break;
            }
        }
        if (!isCanMoveDown){
            for (CubeData cubeData : cubeTempList){
                cubeData.getCubeView().setY(cubeY);
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
        return 0;
//        return (int) (Math.random() * ((5) + 1));
    }
}
