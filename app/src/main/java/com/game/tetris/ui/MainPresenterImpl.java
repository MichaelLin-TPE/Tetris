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
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_L1;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_L2;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_LONG;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_T;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_Z;
import static com.game.tetris.tool.CubeTool.CUBE_TYPE_Z2;

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

public class MainPresenterImpl implements MainPresenter {

    private final MainVu mView;

    private final ArrayList<LatticeData> latticeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeDataList = new ArrayList<>();
    private ArrayList<CubeData> cubeTempList;
    private float latticeWidth, latticeHeight; // 格子的寬跟高
    private float gameViewBottomY, gameViewTopY, gameViewLeftX, gameViewRightX;
    private int currentCubeType;
    private static final int CUBE_DOWN_SPEED = 500;
    private boolean isCanMoveOrTurnCube = false;

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
        if (!isCanMoveOrTurnCube){
            return;
        }
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
        //找出最左邊的X
        float leftX = 0;
        float y = 0;
        for (CubeData cubeData : cubeTempList){
            if (leftX == 0){
                leftX = cubeData.getX();
                continue;
            }
            if (leftX > cubeData.getX()){
                leftX = cubeData.getX();
            }
            y = cubeData.getY();
        }
        MichaelLog.i("leftX : "+leftX);
        for (CubeData cubeData : cubeDataList){
            if (leftX - latticeWidth == cubeData.getX() && y == cubeData.getY()){
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
        //找出最右邊的X
        float rightX = 0;
        float y = 0;
        for (CubeData cubeData : cubeTempList){
            if (rightX == 0){
                rightX = cubeData.getX();
                continue;
            }
            if (rightX < cubeData.getX()){
                rightX = cubeData.getX();
            }
            y = cubeData.getY();
        }
        MichaelLog.i("rightX : "+rightX);
        for (CubeData cubeData : cubeDataList){
            if (rightX + latticeWidth == cubeData.getX() && y == cubeData.getY()){
                isReachRight = true;
                break;
            }
        }

        return isReachRight;
    }

    @Override
    public void onRightButtonClickListener() {
        if (!isCanMoveOrTurnCube){
            return;
        }
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
        if (!isCanMoveOrTurnCube){
            return;
        }
        if (currentCubeType == CUBE_TYPE_LONG) {
            changeWayOfLongCube();
        }
        if (currentCubeType == CUBE_TYPE_L2) {
            changeWayOfL2Cube();
        }
        if (currentCubeType == CUBE_TYPE_L1){
            changeWayOfL1Cube();
        }
    }

    @Override
    public void onDownButtonClickListener() {
        if (!isCanMoveOrTurnCube){
            return;
        }
        if (cubeDataList.isEmpty()){
            cubeToBottom();
            return;
        }
        ArrayList<CompareY> touchYList = new ArrayList<>();
        for (CubeData data : cubeTempList){
            for (CubeData cubeData : cubeDataList){
                if (data.getX() == cubeData.getX() && data.getY() < cubeData.getY()){
                    touchYList.add(new CompareY(cubeData.getY(),data.getY()));
                    break;
                }
            }
        }
        CompareY compareY = null;
        float compareHeight = 0;
        for (CompareY data : touchYList){
            if (compareY == null){
                compareY = new CompareY(data.getExistingCubeY(),data.getCubeY());
                compareHeight = data.getExistingCubeY() - data.getCubeY();
                continue;
            }
            if (compareHeight > data.getExistingCubeY() - data.getCubeY()){
                compareY.setExistingCubeY(data.getExistingCubeY());
                compareY.setCubeY(data.getCubeY());
                compareHeight = compareY.getExistingCubeY() - compareY.getCubeY();
            }
        }
        if (compareY == null){
            cubeToBottom();
            return;
        }
        int moveSpace = (int) ((compareY.getExistingCubeY() - latticeHeight - compareY.getCubeY()) / latticeHeight);
        for (CubeData cubeData : cubeTempList){
            cubeData.getCubeView().setY(cubeData.getY() + (latticeHeight * moveSpace));
            cubeData.setY(cubeData.getY() + (latticeHeight * moveSpace));
        }
        isCanMoveOrTurnCube = false;
    }

    private void cubeToBottom() {
        //先取出最高的Y
        float lastY = 0;
        for (CubeData data : cubeTempList){
            if (lastY == 0){
                lastY = data.getY();
                continue;
            }
            if (lastY < data.getY()){
                lastY = data.getY();
            }
        }
        int moveSpace = (int) ((gameViewBottomY - lastY) / latticeHeight);
        for (CubeData cubeData : cubeTempList){
            cubeData.getCubeView().setY(cubeData.getY() + latticeHeight * moveSpace);
            cubeData.setY(cubeData.getY() + latticeHeight * moveSpace);
            MichaelLog.i("cubeY : "+cubeData.getY());
        }
    }

    private void changeWayOfL1Cube() {
        CubeData data = cubeTempList.get(1);
        if (isCanL1TurnWay(data)) {
            return;
        }
        Log.i("Michael","turnWay type : "+data.getCubeTurnWay());
        if (data.getCubeTurnWay() == CUBE_TURN_L1_WAY1){
            data.setCubeTurnWay(CUBE_TURN_L1_WAY2);
            CubeTool.getL1CTurnWay2(cubeTempList,latticeHeight,latticeWidth);
        }else if (data.getCubeTurnWay() == CUBE_TURN_L1_WAY2){
            data.setCubeTurnWay(CUBE_TURN_L1_WAY3);
            CubeTool.getL1CTurnWay3(cubeTempList,latticeHeight,latticeWidth);
        }else if (data.getCubeTurnWay() == CUBE_TURN_L1_WAY3){
            data.setCubeTurnWay(CUBE_TURN_L1_WAY4);
            CubeTool.getL1CTurnWay4(cubeTempList,latticeHeight,latticeWidth);
        }else {
            data.setCubeTurnWay(CUBE_TURN_L1_WAY1);
            CubeTool.getL1CTurnWay1(cubeTempList,latticeHeight,latticeWidth);
        }
        boolean isOverRight = false;
        //先判断是否有超出边界
        for (CubeData cubeData : cubeTempList){
            if (cubeData.getX() >= gameViewRightX){
                isOverRight = true;
                break;
            }
        }
        //再判断有没有超出现有的方块
        for (CubeData cube : cubeTempList){
            for (CubeData cubeData : cubeDataList){
                if (cube.getX() >= cubeData.getX() && cube.getY() == cubeData.getY()){
                    isOverRight = true;
                    break;
                }
            }
        }

        if (isOverRight){
            for (CubeData cubeData : cubeTempList){
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() - latticeWidth);
                cubeData.setX(cubeData.getX() - latticeWidth);
            }
        }
        boolean isOverLeft = false;
        for (CubeData cubeData : cubeTempList){
            if (cubeData.getX() < gameViewLeftX){
                isOverLeft = true;
                break;
            }
        }
        for (CubeData cube : cubeTempList){
            for (CubeData cubeData : cubeDataList){
                if (cube.getX() < cubeData.getX() && cube.getY() == cubeData.getY()){
                    isOverLeft = true;
                    break;
                }
            }
        }

        if (isOverLeft){
            for (CubeData cubeData : cubeTempList){
                cubeData.getCubeView().setX(cubeData.getCubeView().getX() + latticeWidth);
                cubeData.setX(cubeData.getX() + latticeWidth);
            }
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

    private boolean isCanL1TurnWay(CubeData data) {
        for (CubeData cubeData : cubeDataList){
           for (CubeData cube : cubeDataList){
               if (cubeData.getY() == cube.getY() && cubeData.getX() > cube.getX()){
                   continue;
               }
               if (cubeData.getY() == data.getY() && cubeData.getX() <= data.getX() && data.getX() + latticeWidth * 2 >= cube.getX() && data.getCubeTurnWay() == CUBE_TURN_L1_WAY1){
                   return true;
               }
           }
        }
        return false;
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
            CubeTool.getLongCubeTurnWay1(data,latticeHeight,cubeTempList);
        } else {
            CubeTool.getLongCubeTurnWay(data,latticeWidth,cubeTempList,cubeDataList,gameViewLeftX,gameViewRightX);
        }

    }

    private boolean isCanTurnWay(CubeData data) {
        for (CubeData cubeData : cubeDataList){
            if (data.getY() != cubeData.getY()){
                continue;
            }
            for (CubeData cube : cubeDataList){
                if (cubeData.getX() >= cube.getX()){
                    continue;
                }
                if ((cube.getX() - cubeData.getX()) / latticeWidth != 5 && cubeData.getX() < data.getX() && cube.getX() > data.getX()){
                    return true;
                }
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
        //可以開始移動或是轉向
        isCanMoveOrTurnCube = true;
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

                } else if (data.getCubeType() == CUBE_TYPE_L1){
                    if (isL1TouchBottom()) {
                        isReachBottom = true;
                        break;
                    }
                    if (!isCanL1MoveDown()){
                        isReachBottom = true;
                        break;
                    }
                    data.getCubeView().setY(data.getY() + latticeHeight);
                    data.setY(data.getY() + latticeHeight);

                } else if (data.getCubeType() == CUBE_TYPE_T || data.getCubeType() == CUBE_TYPE_Z ||
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
                mView.getHandler().postDelayed(goingDownRunnable, CUBE_DOWN_SPEED);
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
                index ++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown){
            cubeTempList.get(1).getCubeView().setY(CubeTool.getMainL1Cube(cubeY,cubeTempList.get(index),latticeHeight,latticeWidth,index));
            cubeTempList.get(1).setY(CubeTool.getMainL1Cube(cubeY,cubeTempList.get(index),latticeHeight,latticeWidth,index));

            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY1){

                CubeTool.getL1CTurnWay1(cubeTempList,latticeHeight,latticeWidth);

            }else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY2){

                CubeTool.getL1CTurnWay2(cubeTempList,latticeHeight,latticeWidth);

            }else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY3){

                CubeTool.getL1CTurnWay3(cubeTempList,latticeHeight,latticeWidth);

            }else {

                CubeTool.getL1CTurnWay4(cubeTempList,latticeHeight,latticeWidth);

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
                index ++;
            }
            if (!isCanMoveDown) {
                break;
            }
        }
        if (!isCanMoveDown){
            cubeTempList.get(1).getCubeView().setY(CubeTool.getMainL2Cube(cubeY,cubeTempList.get(index),latticeHeight,latticeWidth,index));
            cubeTempList.get(1).setY(CubeTool.getMainL2Cube(cubeY,cubeTempList.get(index),latticeHeight,latticeWidth,index));
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY1){
                CubeTool.getL2TurnWay1(cubeTempList,latticeHeight,latticeWidth);
            }else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY2){
                CubeTool.getL2TurnWay2(cubeTempList,latticeHeight,latticeWidth);
            }else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L2_WAY3){
                CubeTool.getL2TurnWay3(cubeTempList,latticeHeight,latticeWidth);
            }else {
                CubeTool.getL2TurnWay4(cubeTempList,latticeHeight,latticeWidth);
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

    private boolean isL1TouchBottom() {

        boolean isReachBottom = false;
        for (CubeData data : cubeTempList) {
            if ((data.getY() + latticeHeight) > gameViewBottomY) {
                Log.i("Michael","Y : "+data.getY() +" height : "+latticeHeight+ " bottomY : "+gameViewBottomY);
                isReachBottom = true;
                break;
            }
        }
        if (isReachBottom){
            if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY1) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL1CTurnWay1(cubeTempList, latticeHeight, latticeWidth);
            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY2) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY - latticeHeight);
                cubeTempList.get(1).setY(gameViewBottomY - latticeHeight);
                CubeTool.getL1CTurnWay2(cubeTempList, latticeHeight, latticeWidth);

            } else if (cubeTempList.get(0).getCubeTurnWay() == CUBE_TURN_L1_WAY3) {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL1CTurnWay3(cubeTempList, latticeHeight, latticeWidth);
            } else {
                cubeTempList.get(1).getCubeView().setY(gameViewBottomY);
                cubeTempList.get(1).setY(gameViewBottomY);
                CubeTool.getL1CTurnWay4(cubeTempList, latticeHeight, latticeWidth);
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
        return CUBE_TYPE_L1;
//        return (int) (Math.random() * ((2) + 1));
    }
}