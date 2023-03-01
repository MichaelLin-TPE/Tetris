package com.game.tetris.ui;

import com.game.tetris.bean.LatticeData;

import java.util.Collection;

public interface GamePresenter {
    void onCreateGameView();

    void createLatticeDataList(float x, float y,int right,int left , int bottom);

    void onLeftButtonClickListener();

    void onRightButtonClickListener();

    void onTurnCubeButtonClickListener();

    void onDownButtonClickListener();
}
