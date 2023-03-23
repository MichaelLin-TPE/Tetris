package com.game.tetris.ui.welcome;

import com.github.javiersantos.appupdater.objects.Update;

public interface WelcomeVu {
    void startBreathAnimation();

    void setDisableTabToStart(boolean isShow);

    void startToMoveTitle();

    void stopTabToStartAnimation();

    void showMenu();

    void finishApp();

    void goToGamePage(int mode);

    void showSettingDialog();

    void showGameModeDialog();

    void showToast();

    void showDifficultyLeveDialog();

    void onCheckAppVersionUpdate();

    void showUpdateDialog(Update update);

    void goGooglePlayTetrisPage();
}
