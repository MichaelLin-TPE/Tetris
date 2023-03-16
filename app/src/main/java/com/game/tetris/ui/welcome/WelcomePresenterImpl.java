package com.game.tetris.ui.welcome;

public class WelcomePresenterImpl implements WelcomePresenter {

    private WelcomeVu mView;

    public WelcomePresenterImpl(WelcomeVu mView) {
        this.mView = mView;
    }

    @Override
    public void onTapToStartClickListener() {
        mView.stopTabToStartAnimation();
        mView.setDisableTabToStart(false);
        mView.startToMoveTitle();
    }

    @Override
    public void onCreate() {
        mView.startBreathAnimation();
    }

    @Override
    public void onStartToShowMenu() {
        mView.showMenu();
    }

    @Override
    public void onExitGameClickListener() {
        mView.finishApp();
    }

    @Override
    public void onPlayGameClickListener() {
        mView.showGameModeDialog();
    }

    @Override
    public void onSettingClickListener() {
        mView.showSettingDialog();
    }

    @Override
    public void onLevelStartClick() {
        mView.showToast();
    }

    @Override
    public void onPractiseClick() {
        mView.goToGamePage();
    }
}
