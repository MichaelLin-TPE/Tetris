package com.game.tetris.ui.welcome;

import com.github.javiersantos.appupdater.objects.Update;

public interface WelcomePresenter {
    void onTapToStartClickListener();

    void onCreate();

    void onStartToShowMenu();

    void onExitGameClickListener();

    void onPlayGameClickListener();

    void onSettingClickListener();

    void onLevelStartClick();

    void onPractiseClick();

    void onConfirmGameLevelClickListener();

    void onCheckUpdateFail();

    void onCheckAppVersionListener(Update update);

    void onGoUpdateClickListener();

    void onNextTimeClickListener();
}
