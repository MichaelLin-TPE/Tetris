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

    Handler getHandler();

    void showGameOver();

    ConstraintLayout getRootView();

    void showPoint(int point);
}
