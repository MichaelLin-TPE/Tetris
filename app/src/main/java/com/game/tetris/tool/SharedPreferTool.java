package com.game.tetris.tool;

import static com.game.tetris.tool.CubeTool.EASY_MODE_SPEED;

import android.content.Context;
import android.content.SharedPreferences;

import com.game.tetris.application.MyApplication;

public class SharedPreferTool {

    private static volatile SharedPreferTool instance = null;
    private static SharedPreferences sharedPreferences;

    public static SharedPreferTool getInstance() {
        if (instance == null) {
            synchronized (SharedPreferTool.class) {
                if (instance == null) {
                    instance = new SharedPreferTool();
                }
            }
        }
        return instance;
    }

    private static SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            sharedPreferences = MyApplication.instance.getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            return sharedPreferences;
        }
        return sharedPreferences;
    }

    public void saveIsActiveSupportCube(Boolean isSupport) {
        getSharedPreferences().edit().putBoolean("isActiveSupportCube", isSupport).apply();
    }

    public boolean isActiveSupportCube() {
        return getSharedPreferences().getBoolean("isActiveSupportCube", false);
    }

    public void savePoint(int point) {
        getSharedPreferences().edit().putInt("point", point).apply();
    }

    public int getPoint() {
        return getSharedPreferences().getInt("point", 0);
    }

    public boolean isActiveMusic() {
        return getSharedPreferences().getBoolean("isActiveMusic", false);
    }

    public void saveActiveMusic(boolean isActive) {
        getSharedPreferences().edit().putBoolean("isActiveMusic", isActive).apply();
    }

    public void saveGameSpeed(int level) {
        getSharedPreferences().edit().putInt("level", level).apply();
    }

    public int getGameSpeed() {
        return getSharedPreferences().getInt("level", EASY_MODE_SPEED);
    }

    public void setGameLevel(int level){
        getSharedPreferences().edit().putInt("cubeLevel",level).apply();
    }
    public int getGameLevel(){
        return getSharedPreferences().getInt("cubeLevel",1);
    }

}
