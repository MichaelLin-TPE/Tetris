package com.game.tetris.ui;

import android.os.Handler;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;

public interface GameVu {
    void createGameViewBackground();

    void showLattice(LatticeData data, float latticeSize, float latticeHeight);

    void showCube(CubeData data);

    void showSupportCube(CubeData data);

    Handler getHandler();

    void showGameOver(String title);

    ConstraintLayout getRootView();

    void showPoint(int point);

    void showSupportLine(float leftX, float rightX, float topY, float bottomY, float rightBottomY);

    void moveSupportLine(float leftX, float rightX, float topY, float bottomY, float rightBottomY);

    void removeSupportLine();

    void removeSupportCube(View cubeView);

    void startPlayBackgroundMusic();

    void startPlayMoveMusic();

    void startPlayUpgradeMusic();

    void startVibrator(long timeMillis);

    String getGameOverContentWithHistoryScore();
    String getGameOverContentWithoutHistoryScore();
    String getGameOverContentWithHighScore();

    void closePage();

    void resetPoint();

    void showConfirmExitDialog();

    void moveDownCube(View cubeView, CubeData cubeData, float y, int index, int lastIndex);

    int getCurrentPoint();

    void savePoint();

    void showPreCube(int layoutId);

    void moveCube(CubeData cubeData, int index, int lastIndex);

    void showTargetView(boolean isShow);

    void showTargetPoint(int targetPoint);

    void showWinLevelDialog();

    String getWannaTryAgain();

    void playWinMusic();

    void startPlayLevelMusic();
}
