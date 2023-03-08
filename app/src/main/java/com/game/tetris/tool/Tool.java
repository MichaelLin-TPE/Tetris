package com.game.tetris.tool;

import android.util.TypedValue;

import com.game.tetris.application.MyApplication;

public class Tool {
    public static int getDP(int dp){
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, MyApplication.instance.getApplicationContext().getResources().getDisplayMetrics()));
    }

    public static int convertToInt(float value){
        return (int)value;
    }

}
