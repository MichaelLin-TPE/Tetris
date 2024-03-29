package com.game.tetris.tool;

import android.content.res.Resources;
import android.util.TypedValue;

import com.game.tetris.application.MyApplication;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

public class Tool {
    public static int getDP(int dp){
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, MyApplication.instance.getApplicationContext().getResources().getDisplayMetrics()));
    }

    public static int convertToInt(float value){
        return (int)value;
    }

    public static String numberFormat(int point){
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        return numberFormat.format(point);
    }

    public static float convertDoubleWithTwoPercent(float value){
        try {
            DecimalFormat format = new DecimalFormat("#.##");
            format.setRoundingMode(RoundingMode.DOWN);
            return Float.parseFloat(format.format(value));
        }catch (Exception e){
            throw new RuntimeException("convertDoubleWithTwoPercent value : "+value);
        }
    }

    public static float convertDoubleWithOnePercent(float value){
        DecimalFormat format = new DecimalFormat("#.#");
        format.setRoundingMode(RoundingMode.DOWN);
        return Float.parseFloat(format.format(value));
    }

    public static float convertDoubleWithoutPercent(float value){
        return (int)value;
    }

    public static int convertToPixels(float latticeHeight) {
       return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, latticeHeight, getResource().getDisplayMetrics());
    }

    private static Resources getResource(){
        return MyApplication.instance.getResources();
    }

    public static String getTime(Long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss",Locale.getDefault());
        return sdf.format(new Date(timeMillis));
    }
}
