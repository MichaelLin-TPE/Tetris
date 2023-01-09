package com.game.tetris.ui;

import com.game.tetris.bean.LatticeData;

import java.util.Collection;

public interface MainPresenter {
    void onCreateGameView();

    void createLatticeDataList(float x, float y,int right,int left , int bottom);

    void onLeftButtonClickListener();

    void onRightButtonClickListener();

    void onTurnCubeButtonClickListener();
}
