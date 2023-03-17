package com.game.tetris.ui;

public interface GamePresenter {
    void onCreateGameView(int mode);

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
