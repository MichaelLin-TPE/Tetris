package com.game.tetris.tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.game.tetris.application.MyApplication;

public class SharedPreferTool {

    private static volatile SharedPreferTool instance = null;
    private static SharedPreferences sharedPreferences;

    public static SharedPreferTool getInstance(){
        if (instance == null){
            synchronized (SharedPreferTool.class){
                if (instance == null){
                    instance = new SharedPreferTool();
                }
            }
        }
        return instance;
    }

    private static SharedPreferences getSharedPreferences(){
        if (sharedPreferences == null){
            sharedPreferences = MyApplication.instance.getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE);
            return sharedPreferences;
        }
        return sharedPreferences;
    }
    public void saveIsActiveSupportCube(Boolean isSupport){
        getSharedPreferences().edit().putBoolean("isActiveSupportCube",isSupport).apply();
    }
    public boolean isActiveSupportCube(){
        return getSharedPreferences().getBoolean("isActiveSupportCube",false);
    }

}
