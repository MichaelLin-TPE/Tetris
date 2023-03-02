package com.game.tetris.ui.welcome;

public interface WelcomeVu {
    void startBreathAnimation();

    void setDisableTabToStart(boolean isShow);

    void startToMoveTitle();

    void stopTabToStartAnimation();

    void showMenu();

    void finishApp();

    void goToGamePage();
}
