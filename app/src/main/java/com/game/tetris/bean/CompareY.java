package com.game.tetris.bean;

public class CompareY {

    private float existingCubeY;
    private float cubeY;
    private float cubeX;

    public float getCubeX() {
        return cubeX;
    }

    public void setCubeX(float cubeX) {
        this.cubeX = cubeX;
    }

    public CompareY(){}

    public CompareY(float existingCubeY, float cubeY,float cubeX) {
        this.existingCubeY = existingCubeY;
        this.cubeY = cubeY;
        this.cubeX = cubeX;
    }

    public float getExistingCubeY() {
        return existingCubeY;
    }

    public void setExistingCubeY(float existingCubeY) {
        this.existingCubeY = existingCubeY;
    }

    public float getCubeY() {
        return cubeY;
    }

    public void setCubeY(float cubeY) {
        this.cubeY = cubeY;
    }
}
