package com.game.tetris.ui;

import android.os.Handler;

import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;

public interface MainVu {
    void createGameViewBackground();

    void showLattice(LatticeData data, float latticeSize, float latticeHeight);

    void showCube(CubeData data);

    Handler getHandler();

    void showGameOver();
}
