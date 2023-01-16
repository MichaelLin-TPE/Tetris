package com.game.tetris.tool;

import com.game.tetris.R;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;

import java.util.ArrayList;

public class CubeTool {

    public static final int CUBE_TYPE_LONG = 0;
    public static final int CUBE_TYPE_Z2 = 5;
    public static final int CUBE_TYPE_Z = 4;
    public static final int CUBE_TYPE_L1 = 1;
    public static final int CUBE_TYPE_L2 = 2;
    public static final int CUBE_TYPE_T = 3;

    //長型方塊轉向
    public static final int CUBE_TURN_LONG_WAY1 = 111;
    public static final int CUBE_TURN_LONG_WAY2 = 222;

    //L2型方塊轉向
    public static final int CUBE_TURN_L2_WAY1 = 333;
    public static final int CUBE_TURN_L2_WAY2 = 444;
    public static final int CUBE_TURN_L2_WAY3 = 555;
    public static final int CUBE_TURN_L2_WAY4 = 666;
    

    
    public static ArrayList<CubeData> getLCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(25).getX(), latticeDataList.get(25).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(24).getX(), latticeDataList.get(24).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getL2CubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(24).getX(), latticeDataList.get(24).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_L2_WAY1));
        data.add(new CubeData(latticeDataList.get(25).getX(), latticeDataList.get(25).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_L2_WAY1));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_L2_WAY1));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_L2_WAY1));
        return data;
    }

    public static ArrayList<CubeData> getTCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(16).getX(), latticeDataList.get(16).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getZCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(16).getX(), latticeDataList.get(16).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getZ2CubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(6).getX(), latticeDataList.get(6).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY2));
        return data;
    }


    public static ArrayList<CubeData> getLongCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        for (int i = 3; i < 7; i++) {
            LatticeData latticeData = latticeDataList.get(i);
            data.add(new CubeData(latticeData.getX(), latticeData.getY(), R.drawable.cube_long_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_LONG_WAY1));
        }
        return data;
    }

    public static void getL2TurnWay2(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth) {
        CubeData data = cubeTempList.get(1);
        cubeTempList.get(0).getCubeView().setY(data.getY() - latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(data.getY() - latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY2);

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + +latticeWidth);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY2);

        cubeTempList.get(3).getCubeView().setY(data.getY());
        cubeTempList.get(3).getCubeView().setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(3).setY(data.getY());
        cubeTempList.get(3).setX(data.getX() + +latticeWidth * 2);
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY2);
    }

    public static void getL2TurnWay3(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth) {
        CubeData data = cubeTempList.get(1);
        cubeTempList.get(0).getCubeView().setY(data.getY() - latticeHeight);
        cubeTempList.get(0).getCubeView().setX(data.getX());
        cubeTempList.get(0).setY(data.getY() - latticeHeight);
        cubeTempList.get(0).setX(data.getX());
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY3);

        cubeTempList.get(2).getCubeView().setY(data.getY() - latticeHeight * 2);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY() - latticeHeight * 2);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY3);

        cubeTempList.get(3).getCubeView().setY(cubeTempList.get(2).getY());
        cubeTempList.get(3).getCubeView().setX(cubeTempList.get(2).getX() + latticeWidth);
        cubeTempList.get(3).setY(cubeTempList.get(2).getY());
        cubeTempList.get(3).setX(cubeTempList.get(2).getX() + latticeWidth);
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY3);
    }

    public static void getL2TurnWay4(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth) {
        CubeData data = cubeTempList.get(1);
        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() + latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() + latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY4);

        cubeTempList.get(2).getCubeView().setY(data.getY());
        cubeTempList.get(2).getCubeView().setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(2).setY(data.getY());
        cubeTempList.get(2).setX(data.getX() + latticeWidth * 2);
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY4);

        cubeTempList.get(3).getCubeView().setY(cubeTempList.get(2).getY() + latticeHeight);
        cubeTempList.get(3).getCubeView().setX(cubeTempList.get(2).getX());
        cubeTempList.get(3).setY(cubeTempList.get(2).getY() + latticeHeight);
        cubeTempList.get(3).setX(cubeTempList.get(2).getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY4);
    }

    public static void getL2TurnWay1(ArrayList<CubeData> cubeTempList, float latticeHeight, float latticeWidth) {
        CubeData data = cubeTempList.get(1);
        cubeTempList.get(0).getCubeView().setY(data.getY());
        cubeTempList.get(0).getCubeView().setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setY(data.getY());
        cubeTempList.get(0).setX(data.getX() - latticeWidth);
        cubeTempList.get(0).setCubeTurnWay(CUBE_TURN_L2_WAY1);

        cubeTempList.get(2).getCubeView().setY(data.getY() - latticeHeight);
        cubeTempList.get(2).getCubeView().setX(data.getX());
        cubeTempList.get(2).setY(data.getY()- latticeHeight);
        cubeTempList.get(2).setX(data.getX());
        cubeTempList.get(2).setCubeTurnWay(CUBE_TURN_L2_WAY1);

        cubeTempList.get(3).getCubeView().setY(data.getY() - latticeHeight * 2);
        cubeTempList.get(3).getCubeView().setX(data.getX());
        cubeTempList.get(3).setY(data.getY()- latticeHeight * 2);
        cubeTempList.get(3).setX(data.getX());
        cubeTempList.get(3).setCubeTurnWay(CUBE_TURN_L2_WAY1);
    }
}
