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
    public static final int CUBE_TURN_WAY1 = 111;
    public static final int CUBE_TURN_WAY2 = 222;

    
    public static ArrayList<CubeData> getLCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(25).getX(), latticeDataList.get(25).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(24).getX(), latticeDataList.get(24).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_l_bg1, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getL2CubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(24).getX(), latticeDataList.get(24).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(25).getX(), latticeDataList.get(25).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_l_bg2, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getTCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(16).getX(), latticeDataList.get(16).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_t_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getZCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(16).getX(), latticeDataList.get(16).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(4).getX(), latticeDataList.get(4).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_z_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        return data;
    }

    public static ArrayList<CubeData> getZ2CubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        data.add(new CubeData(latticeDataList.get(14).getX(), latticeDataList.get(14).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(15).getX(), latticeDataList.get(15).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(6).getX(), latticeDataList.get(6).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        data.add(new CubeData(latticeDataList.get(5).getX(), latticeDataList.get(5).getY(), R.drawable.cube_z2_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY2));
        return data;
    }


    public static ArrayList<CubeData> getLongCubeData(int currentCubeType,ArrayList<LatticeData> latticeDataList,float latticeWidth , float latticeHeight) {
        ArrayList<CubeData> data = new ArrayList<>();
        for (int i = 3; i < 7; i++) {
            LatticeData latticeData = latticeDataList.get(i);
            data.add(new CubeData(latticeData.getX(), latticeData.getY(), R.drawable.cube_long_bg, currentCubeType, latticeWidth, latticeHeight,CUBE_TURN_WAY1));
        }
        return data;
    }
    
}
