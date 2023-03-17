package com.game.tetris.ui;

import com.game.tetris.bean.LatticeData;

import java.util.Collection;

public interface GamePresenter {
    void onCreateGameView();

    void createLatticeDataList(float x, float y,int right,int left , int bottom);

    void onTurnCubeButtonClickListener();

    void onDownButtonClickListener();

    void onLeftPressDownListener();

    void onLeftPressUpListener();

    void onRightPressDownListener();

    void onFinishCubeStraightDown();

    void onDestroy();


    void onRightPressUpListener();

    void onReplayClickListener();

    void onExitClickListener();

    void onBackPressedListener();

    void reCreateCube();
}
