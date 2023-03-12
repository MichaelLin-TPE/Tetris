package com.game.tetris.tool;

import android.util.TypedValue;

import com.game.tetris.application.MyApplication;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Tool {
    public static int getDP(int dp){
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, MyApplication.instance.getApplicationContext().getResources().getDisplayMetrics()));
    }

    public static int convertToInt(float value){
        return (int)value;
    }


    public static float convertDoubleWithTwoPercent(float value){
        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.DOWN);
        return Float.parseFloat(format.format(value));
    }

    public static float convertDoubleWithOnePercent(float value){
        DecimalFormat format = new DecimalFormat("#.#");
        format.setRoundingMode(RoundingMode.DOWN);
        return Float.parseFloat(format.format(value));
    }

    public static float convertDoubleWithoutPercent(float value){
        return (int)value;
    }
}
