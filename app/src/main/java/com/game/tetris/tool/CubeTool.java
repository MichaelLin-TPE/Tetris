package com.game.tetris.tool;


import android.util.Log;

import com.game.tetris.MichaelLog;
import com.game.tetris.battle.R;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;

import java.util.ArrayList;

public class CubeTool {

    public static final int CUBE_TYPE_LONG = 0;
    public static final int CUBE_TYPE_Z2 = 4;
    public static final int CUBE_TYPE_Z = 3;
    public static final int CUBE_TYPE_L1 = 2;
    public static final int CUBE_TYPE_L2 = 1;
    public static final int CUBE_TYPE_SQUIRE = 5;
    public static final int CUBE_TYPE_T = 6;

    //長型方塊轉向
    public static final int CUBE_TURN_LONG_WAY1 = 111;
    public static final int CUBE_TURN_LONG_WAY2 = 222;

    //L2型方塊轉向
    public static final int CUBE_TURN_L2_WAY1 = 333;
    public static final int CUBE_TURN_L2_WAY2 = 444;
    public static final int CUBE_TURN_L2_WAY3 = 555;
    public static final int CUBE_TURN_L2_WAY4 = 666;
    //L1型方塊轉向
    public static final int CUBE_TURN_L1_WAY1 = 777;
    public static final int CUBE_TURN_L1_WAY2 = 888;
    public static final int CUBE_TURN_L1_WAY3 = 999;
    public static final int CUBE_TURN_L1_WAY4 = 1000;

    public static final int CUBE_TURN_Z1_WAY1 = 123;
    public static final int CUBE_TURN_Z1_WAY2 = 456;

    public static final int CUBE_TURN_Z2_WAY1 = 789;
    public static final int CUBE_TURN_Z2_WAY2 = 11;

    public static final int CUBE_TURN_T_WAY1 = 1111;
    public static final int CUBE_TURN_T_WAY2 = 2222;
    public static final int CUBE_TURN_T_WAY3 = 3333;
    public static final int CUBE_TURN_T_WAY4 = 4444;


    public static final int EASY_MODE_SPEED = 1500;
    public static final int NORMAL_MODE_SPEED = 1000;
    public static final int HARD_MODE_SPEED = 500;
    public static final int ADVANCE_MODE_SPEED = 300;
    public static final int EXPERT_MODE_SPEED = 100;

    public static final int PRACTISE_MODE = 2222;
    public static final int LEVEL_MODE = 3333;


    public static ArrayList<CubeData> getSquareCubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_square_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TYPE_SQUIRE, 1));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_square_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TYPE_SQUIRE, 1));
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_square_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TYPE_SQUIRE, 2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_square_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TYPE_SQUIRE, 2));
        return data;
    }

    public static ArrayList<CubeData> getLCubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(25).getX(), latticeDataList.get(25).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L1_WAY1, 3));
        data.add(new CubeData(latticeDataList.get(24).getX(), latticeDataList.get(24).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L1_WAY1, 3));
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L1_WAY1, 2));
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L1_WAY1, 1));
        return data;
    }

    public static ArrayList<CubeData> getL2CubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(24).getX(), latticeDataList.get(24).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L2_WAY1, 3));
        data.add(new CubeData(latticeDataList.get(25).getX(), latticeDataList.get(25).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L2_WAY1, 3));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L2_WAY1, 2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_L2_WAY1, 1));
        return data;
    }

    public static ArrayList<CubeData> getTCubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_T_WAY1, 2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_T_WAY1, 2));
        data.add(new CubeData(latticeDataList.get(16).getX(), latticeDataList.get(16).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_T_WAY1, 2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_T_WAY1, 1));
        return data;
    }

    public static ArrayList<CubeData> getZCubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(16).getX(), latticeDataList.get(16).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z1_WAY2, 2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z1_WAY2, 2));
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z1_WAY2, 2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z1_WAY2, 1));
        return data;
    }

    public static ArrayList<CubeData> getZ2CubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z2_WAY2, 2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z2_WAY2, 2));
        data.add(new CubeData(latticeDataList.get(6).getX(), latticeDataList.get(6).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z2_WAY2, 1));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_Z2_WAY2, 1));
        return data;
    }


    public static ArrayList<CubeData> getLongCubeData(int currentCubeType, ArrayList<LatticeData> latticeDataList, float latticeWidth, float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        for (int i = 3; i < 7; i++) {
            LatticeData latticeData = latticeDataList.get(i);
            data.add(new CubeData(latticeData.getX(), latticeData.getY(), R.drawable.cube_long_bg, currentCubeType, latticeWidth, latticeHeight, CUBE_TURN_LONG_WAY1, 1));
        }
        return data;
    }

    public static void getL2TurnWay2(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewRightX, float gameViewLeftX) {
        boolean isHoldLeft = false, isHoldRight = false;
        for (CubeData data : cubeTempList) {

            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
            }
        }
        CubeData cubeData = cubeTempList.get(2);
        cubeData.setCubeTurnWay(CUBE_TURN_L2_WAY2);

        if (!isHoldLeft && !isHoldRight) {
            cubeData.setX(cubeData.getX() + latticeWidth);
            cubeData.getCubeView().setX(cubeData.getX());
        } else if (isHoldLeft) {
            cubeData.setX(cubeData.getX() + latticeWidth);
            cubeData.getCubeView().setX(cubeData.getX());
        }
        cubeTempList.get(0).getCubeView().setY(cubeData.getY() - latticeHeight);
        cubeTempList.get(0).getCubeView().setX(cubeData.getX() - latticeWidth * 2);
        cubeTempList.get(0).setY(cubeData.getY() - latticeHeight);
        cubeTempList.get(0).setX(cubeData.getX() - latticeWidth * 2);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY2);

        cubeTempList.get(1).getCubeView().setY(cubeData.getY());
        cubeTempList.get(1).getCubeView().setX(cubeData.getX() - latticeWidth * 2);
        cubeTempList.get(1).setY(cubeData.getY());
        cubeTempList.get(1).setX(cubeData.getX() - latticeWidth * 2);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L2_WAY2);

        cubeTempList.get(3).getCubeView().setY(cubeData.getY());
        cubeTempList.get(3).getCubeView().setX(cubeData.getX() - latticeWidth);
        cubeTempList.get(3).setY(cubeData.getY());
        cubeTempList.get(3).setX(cubeData.getX() - latticeWidth);
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY2);

    }

    public static void getL2TurnWay3(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewRightX, float gameViewLeftX) {

        boolean isHoldRight = false, isHoldLeft = false;
        for (CubeData data : cubeTempList) {
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
            }
        }


        CubeData data = cubeTempList.get(0);
        data.setCubeTurnWay(CUBE_TURN_L2_WAY3);

        if (isHoldRight) {
            data.setX(data.getX() + latticeWidth);
            data.getCubeView().setX(data.getX());
        }
        cubeTempList.get(1).getCubeView().setY(data.getY());
        cubeTempList.get(1).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(1).setY(data.getY());
        cubeTempList.get(1).setX(data.getX() + latticeWidth);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L2_WAY3);

        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() + latticeHeight);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY3);

        cubeTempList.get(3).getCubeView().setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY3);

    }

    public static void getL2TurnWay4(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewRightX, float gameViewLeftX) {

        boolean isHoldRight = false, isHoldLeft = false;
        for (CubeData data : cubeTempList) {
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
            }
        }
        CubeData cubeData = cubeTempList.get(1);
        cubeData.setCubeTurnWay(CUBE_TURN_L2_WAY4);

        if (!isHoldLeft && !isHoldRight) {
            Log.i("Michael", "兩邊都沒靠邊");
            cubeData.setX(cubeData.getX() + latticeWidth);
            cubeData.getCubeView().setX(cubeData.getX());
        } else if (isHoldLeft) {
            Log.i("Michael", "靠左邊");
            cubeData.setX(cubeData.getX() + latticeWidth);
            cubeData.getCubeView().setX(cubeData.getX());
        }

        cubeTempList.get(0).getCubeView().setY(cubeData.getY() + latticeHeight);
        cubeTempList.get(0).getCubeView().setX(cubeData.getX());
        cubeTempList.get(0).setY(cubeData.getY() + latticeHeight);
        cubeTempList.get(0).setX(cubeData.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY4);

        cubeTempList.get(2).getCubeView().setY(cubeData.getY());
        cubeTempList.get(2).getCubeView().setX(cubeData.getX() - latticeWidth);
        cubeTempList.get(2).setY(cubeData.getY());
        cubeTempList.get(2).setX(cubeData.getX() - latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY4);

        cubeTempList.get(3).getCubeView().setY(cubeData.getY());
        cubeTempList.get(3).getCubeView().setX(cubeData.getX() - latticeWidth * 2);
        cubeTempList.get(3).setY(cubeData.getY());
        cubeTempList.get(3).setX(cubeData.getX() - latticeWidth * 2);
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY4);

    }

    public static void getL2TurnWay1(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewRightX, float gameViewLeftX) {

        boolean isHoldLeft = false, isHoldRight = false;
        for (CubeData data : cubeTempList) {
            MichaelLog.i("x : " + data.getX() + " leftX : " + gameViewLeftX);
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
            }
        }
        CubeData cubeData = cubeTempList.get(3);
        cubeData.setCubeTurnWay(CUBE_TURN_L2_WAY1);

        if (!isHoldLeft && !isHoldRight) {
            cubeData.setX(cubeData.getX() + latticeWidth);
            cubeData.getCubeView().setX(cubeData.getX());
        } else if (isHoldLeft) {
            cubeData.setX(cubeData.getX() + latticeWidth);
            cubeData.getCubeView().setX(cubeData.getX());
        } else if (isHoldRight) {
            cubeData.setX(cubeData.getX() + latticeWidth * 2);
            cubeData.getCubeView().setX(cubeData.getX());
        }

        cubeTempList.get(2).getCubeView().setY(cubeData.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(cubeData.getX());
        cubeTempList.get(2).setY(cubeData.getY() + latticeHeight);
        cubeTempList.get(2).setX(cubeData.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY1);

        cubeTempList.get(1).getCubeView().setY(cubeData.getY() + latticeHeight * 2);
        cubeTempList.get(1).getCubeView().setX(cubeData.getX());
        cubeTempList.get(1).setY(cubeData.getY() + latticeHeight * 2);
        cubeTempList.get(1).setX(cubeData.getX());
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L2_WAY1);

        cubeTempList.get(0).getCubeView().setY(cubeData.getY() + latticeHeight * 2);
        cubeTempList.get(0).getCubeView().setX(cubeData.getX() - latticeWidth);
        cubeTempList.get(0).setY(cubeData.getY() + latticeHeight * 2);
        cubeTempList.get(0).setX(cubeData.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY1);

    }

    public static void getL1CTurnWay2(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewLeftX, float gameViewRightX) {

        boolean isHoldLeft = false, isHoldRight = false;
        for (CubeData data : cubeTempList) {
            MichaelLog.i("x : " + data.getX() + " leftX : " + gameViewLeftX);
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
            }
        }
        CubeData data = cubeTempList.get(3);
        data.setCubeTurnWay(CUBE_TURN_L1_WAY2);
        if (isHoldRight) {
            data.setX(data.getX() - latticeWidth);
            data.getCubeView().setX(data.getX());
        }
        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L1_WAY2);

        cubeTempList.get(1).getCubeView().setY(data.getY());
        cubeTempList.get(1).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(1).setY(data.getY());
        cubeTempList.get(1).setX(data.getX() + latticeWidth);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L1_WAY2);

        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() + latticeHeight);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L1_WAY2);

    }

    public static void getL1CTurnWay3(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewLeftX, float gameViewRightX) {


        boolean isHoldLeft = false, isHoldRight = false;
        for (CubeData data : cubeTempList) {
            MichaelLog.i("x : " + data.getX() + " leftX : " + gameViewLeftX);
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
            }
        }
        CubeData data = cubeTempList.get(3);
        data.setCubeTurnWay(CUBE_TURN_L1_WAY3);
        if (isHoldLeft) {
            data.setX(data.getX() + latticeWidth);
            data.getCubeView().setX(data.getX());
        } else if (isHoldRight) {
            data.setX(data.getX() + latticeWidth * 2);
            data.getCubeView().setX(data.getX());
        }

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() - latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L1_WAY3);

        cubeTempList.get(1).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(1).getCubeView().setX(data.getX());
        cubeTempList.get(1).setY(data.getY() + latticeHeight);
        cubeTempList.get(1).setX(data.getX());
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L1_WAY3);

        cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L1_WAY3);

    }

    public static void getL1CTurnWay4(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewLeftX, float gameViewRightX) {
        boolean isHoldLeft = false, isHoldRight = false;
        for (CubeData data : cubeTempList) {
            MichaelLog.i("x : " + data.getX() + " leftX : " + gameViewLeftX);
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
            }
        }
        CubeData data = cubeTempList.get(3);
        data.setCubeTurnWay(CUBE_TURN_L1_WAY4);

        if (!isHoldLeft && !isHoldRight) {
            Log.i("Michael", "兩邊都沒靠邊");
            data.setX(data.getX() + latticeWidth);
            data.getCubeView().setX(data.getX());
        } else if (isHoldLeft){
            data.setX(data.getX() + latticeWidth);
            data.getCubeView().setX(data.getX());
        }
        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() + latticeHeight);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L1_WAY4);

        cubeTempList.get(1).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(1).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(1).setY(data.getY() + latticeHeight);
        cubeTempList.get(1).setX(data.getX() - latticeWidth);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L1_WAY4);

        cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth * 2);
        cubeTempList.get(0).setY(data.getY() + latticeHeight);
        cubeTempList.get(0).setX(data.getX() - latticeWidth * 2);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L1_WAY4);

    }

    public static void getL1CTurnWay1(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewLeftX, float gameViewRightX) {

        boolean isHoldLeft = false, isHoldRight = false;
        for (CubeData data : cubeTempList) {
            MichaelLog.i("x : " + data.getX() + " leftX : " + gameViewLeftX);
            if (Math.abs(data.getX() + latticeWidth - gameViewRightX) < 10) {
                isHoldRight = true;
            }
            if (Math.abs(data.getX() - gameViewLeftX) < 10) {
                isHoldLeft = true;
            }
        }

        for (CubeData data : cubeTempList) {
            for (CubeData cubeData : cubeDataList) {
                if (Math.abs(data.getX() + latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldRight = true;
                }
                if (Math.abs(data.getX() - latticeWidth - cubeData.getX()) < 10 && data.getY() == cubeData.getY()) {
                    isHoldLeft = true;
                }
            }
        }

        CubeData data = cubeTempList.get(3);
        data.setCubeTurnWay(CUBE_TURN_L1_WAY1);
        if (isHoldLeft){
            data.setX(data.getX() - latticeWidth * 2);
            data.getCubeView().setX(data.getX());
        }
        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() + latticeHeight);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L1_WAY1);

        cubeTempList.get(1).getCubeView().setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(1).getCubeView().setX(data.getX());
        cubeTempList.get(1).setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(1).setX(data.getX());
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_L1_WAY1);

        cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(0).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(0).setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(0).setX(data.getX() + latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L1_WAY1);
    }

    public static void getLongCubeTurnWay(CubeData data, float latticeWidth, ArrayList<CubeData> cubeTempList, ArrayList<CubeData> cubeDataList, float gameViewLeftX, float gameViewRightX) {
        //偵測若右邊方塊超出已經放置好的方塊來決定要移動幾格
        int moveSpace = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() == cubeData.getY() && data.getX() + latticeWidth * 2 == cubeData.getX() + latticeWidth && data.getX() + latticeWidth == cubeData.getX()) {
                moveSpace = -2;
                break;
            }
            if (data.getY() == cubeData.getY() && data.getX() + latticeWidth * 2 == cubeData.getX() + latticeWidth) {
                moveSpace = -1;
                break;
            }
        }
        data.setRow(1);
        float moveX = data.getX() + (latticeWidth * moveSpace);
        MichaelLog.i("moveSpace : " + moveSpace + " moveX : " + moveX);
        data.setX(moveX);
        data.getCubeView().setX(moveX);
        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_LONG_WAY1);
        cubeTempList.get(0).setRow(1);


        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_LONG_WAY1);
        cubeTempList.get(2).setRow(1);


        cubeTempList.get(3).getCubeView().setY(data.getY());
        cubeTempList.get(3).getCubeView().setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(3).setY(data.getY());
        cubeTempList.get(3).setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_LONG_WAY1);
        cubeTempList.get(3).setRow(1);


        //檢查是否有超出螢幕
        checkHasOverScreen(cubeTempList, gameViewLeftX, gameViewRightX, latticeWidth);

    }

    private static void checkHasOverScreen(ArrayList<CubeData> cubeTempList, float gameViewLeftX, float gameViewRightX, float latticeWidth) {
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
                cubeData.getCubeView().setX(gameViewLeftX + (latticeWidth * i));
                cubeData.setX(gameViewLeftX + (latticeWidth * i));
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

    public static void getLongCubeTurnWay1(CubeData data, float latticeHeight, ArrayList<CubeData> cubeTempList) {
        data.setRow(1);
        cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(data.getY() + latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_LONG_WAY2);
        cubeTempList.get(0).setRow(2);

        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() + latticeHeight * 2);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_LONG_WAY2);
        cubeTempList.get(2).setRow(3);

        cubeTempList.get(3).getCubeView().setY(data.getY() + latticeHeight * 3);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY() + latticeHeight * 3);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_LONG_WAY2);
        cubeTempList.get(3).setRow(4);

    }

    public static void getZ1CTurnWay2(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewTopY) {
        //偵測若右邊方塊超出已經放置好的方塊來決定要移動幾格
        int moveSpace = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() + latticeHeight == cubeData.getY() && data.getX() + latticeWidth == cubeData.getX()) {
                moveSpace = -1;
            }
            if (data.getY() == cubeData.getY() && data.getX() - latticeWidth == cubeData.getX()) {
                moveSpace = 1;
            }
        }
        float moveX = data.getX() + (latticeWidth * moveSpace);
        data.setX(moveX);
        data.getCubeView().setX(moveX);

        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_Z1_WAY2);

        cubeTempList.get(1).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(1).getCubeView().setX(data.getX());
        cubeTempList.get(1).setY(data.getY() + latticeHeight);
        cubeTempList.get(1).setX(data.getX());
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_Z1_WAY2);

        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY() + latticeHeight);
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_Z1_WAY2);


    }

    public static void getZ1CTurnWay1(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewTopY) {


        float cubeY = data.getY() <= gameViewTopY ? data.getY() + latticeHeight : data.getY();
        MichaelLog.i("cubeY : " + cubeY + " topY : " + gameViewTopY);
        data.setY(cubeY);
        data.getCubeView().setY(cubeY);


        cubeTempList.get(0).getCubeView().setY(cubeY - latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(cubeY - latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_Z1_WAY1);

        cubeTempList.get(1).getCubeView().setY(cubeY);
        cubeTempList.get(1).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(1).setY(cubeY);
        cubeTempList.get(1).setX(data.getX() - latticeWidth);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_Z1_WAY1);

        cubeTempList.get(2).getCubeView().setY(cubeY + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(2).setY(cubeY + latticeHeight);
        cubeTempList.get(2).setX(data.getX() - latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_Z1_WAY1);

    }

    public static void getZ2CTurnWay1(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList, float gameViewTopY) {

        float cubeY = data.getY() <= gameViewTopY ? data.getY() + latticeHeight : data.getY();
        MichaelLog.i("cubeY : " + cubeY + " topY : " + gameViewTopY);
        data.setY(cubeY);
        data.getCubeView().setY(cubeY);

        cubeTempList.get(0).getCubeView().setY(cubeY - latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(cubeY - latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_Z2_WAY1);

        cubeTempList.get(1).getCubeView().setY(cubeY);
        cubeTempList.get(1).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(1).setY(cubeY);
        cubeTempList.get(1).setX(data.getX() + latticeWidth);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_Z2_WAY1);

        cubeTempList.get(2).getCubeView().setY(cubeY + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(cubeY + latticeHeight);
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_Z2_WAY1);

    }


    public static void getZ2CTurnWay2(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList) {
        //偵測若右邊方塊超出已經放置好的方塊來決定要移動幾格
        int moveSpace = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() == cubeData.getY() && data.getX() + latticeWidth == cubeData.getX()) {
                moveSpace = -1;
            }
            if (data.getY() + latticeHeight == cubeData.getY() && data.getX() - latticeWidth == cubeData.getX()) {
                moveSpace = 1;
            }
        }
        float moveX = data.getX() + (latticeWidth * moveSpace);
        data.setX(moveX);
        data.getCubeView().setX(moveX);

        cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(data.getY() + latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_Z2_WAY2);

        cubeTempList.get(1).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(1).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(1).setY(data.getY() + latticeHeight);
        cubeTempList.get(1).setX(data.getX() - latticeWidth);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TURN_Z2_WAY2);

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_Z2_WAY2);


    }

    public static void getSquareCube(float cubeY, ArrayList<CubeData> cubeTempList, float latticeHeight) {
        cubeTempList.get(0).getCubeView().setY(cubeY - latticeHeight);
        cubeTempList.get(0).setY(cubeY - latticeHeight);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TYPE_SQUIRE);

        cubeTempList.get(1).getCubeView().setY(cubeY - latticeHeight);
        cubeTempList.get(1).setY(cubeY - latticeHeight);
        cubeTempList.get(1).setCubeTurnWay(CUBE_TYPE_SQUIRE);

        cubeTempList.get(2).getCubeView().setY(cubeY);
        cubeTempList.get(2).setY(cubeY);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TYPE_SQUIRE);

        cubeTempList.get(3).getCubeView().setY(cubeY);
        cubeTempList.get(3).setY(cubeY);
        cubeTempList.get(3).setCubeTurnWay(CUBE_TYPE_SQUIRE);
    }

    public static void getTTurnWay2(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList) {
        cubeTempList.get(0).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(data.getY() + latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_T_WAY2);

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_T_WAY2);

        cubeTempList.get(3).getCubeView().setY(data.getY() - latticeHeight);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY() - latticeHeight);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_T_WAY2);

    }

    public static void getTTurnWay4(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList) {
        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_T_WAY4);

        cubeTempList.get(2).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() + latticeHeight);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_T_WAY4);

        cubeTempList.get(3).getCubeView().setY(data.getY() - latticeHeight);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY() - latticeHeight);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_T_WAY4);
    }

    public static void getTTurnWay1(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList) {
        int moveSpace = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() == cubeData.getY() && data.getX() + latticeWidth == cubeData.getX()) {
                moveSpace = -1;
            }
            if (data.getY() == cubeData.getY() && data.getX() - latticeWidth == cubeData.getX()) {
                moveSpace = 1;
            }
        }
        float moveX = data.getX() + (latticeWidth * moveSpace);
        data.setX(moveX);
        data.getCubeView().setX(moveX);
        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_T_WAY1);

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_T_WAY1);

        cubeTempList.get(3).getCubeView().setY(data.getY() - latticeHeight);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY() - latticeHeight);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_T_WAY1);
    }

    public static void getTTurnWay3(CubeData data, ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth, ArrayList<CubeData> cubeDataList) {
        //偵測若右邊方塊超出已經放置好的方塊來決定要移動幾格
        int moveSpace = 0;
        for (CubeData cubeData : cubeDataList) {
            if (data.getY() == cubeData.getY() && data.getX() + latticeWidth == cubeData.getX()) {
                moveSpace = -1;
            }
            if (data.getY() == cubeData.getY() && data.getX() - latticeWidth == cubeData.getX()) {
                moveSpace = 1;
            }
        }
        float moveX = data.getX() + (latticeWidth * moveSpace);
        data.setX(moveX);
        data.getCubeView().setX(moveX);

        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_T_WAY3);

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_T_WAY3);

        cubeTempList.get(3).getCubeView().setY(data.getY() + latticeHeight);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY() + latticeHeight);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_T_WAY3);
    }

}
