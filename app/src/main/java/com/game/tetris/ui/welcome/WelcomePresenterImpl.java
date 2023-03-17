package com.game.tetris.ui.welcome;

import static com.game.tetris.tool.CubeTool.LEVEL_MODE;
import static com.game.tetris.tool.CubeTool.PRACTISE_MODE;

public class WelcomePresenterImpl implements WelcomePresenter {

    private WelcomeVu mView;
    private int mode;
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
        mode = LEVEL_MODE;
        mView.showDifficultyLeveDialog();
    }

    @Override
    public void onPractiseClick() {
        mode = PRACTISE_MODE;
        mView.showDifficultyLeveDialog();
    }

    @Override
    public void onConfirmGameLevelClickListener() {
        mView.goToGamePage(mode);
    }
}
