package com.game.tetris.bean;

public class CompareY {

    private float existingCubeY;
    private float cubeY;

    public CompareY(){}

    public CompareY(float existingCubeY, float cubeY) {
        this.existingCubeY = existingCubeY;
        this.cubeY = cubeY;
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
