package com.game.tetris.bean;

import android.view.View;

public class CubeData {

    private float x;
    private float y;
    private int bg;
    private int cubeType;
    private float width;
    private float height;
    private View cubeView;


    public CubeData(float x, float y, int bg, int cubeType,float width,float height) {
        this.x = x;
        this.y = y;
        this.bg = bg;
        this.cubeType = cubeType;
        this.width = width;
        this.height = height;
    }

    public View getCubeView() {
        return cubeView;
    }

    public void setCubeView(View cubeView) {
        this.cubeView = cubeView;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public int getCubeType() {
        return cubeType;
    }

    public void setCubeType(int cubeType) {
        this.cubeType = cubeType;
    }
}
