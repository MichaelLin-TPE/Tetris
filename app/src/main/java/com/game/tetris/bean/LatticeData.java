package com.game.tetris.bean;

public class LatticeData {

    private float x;
    private float y;
    private int row;
    private int color;
    private boolean isHasCube;


    public LatticeData(float x, float y, int row, int color) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.color = color;
    }

    public boolean isHasCube() {
        return isHasCube;
    }

    public void setHasCube(boolean hasCube) {
        isHasCube = hasCube;
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
