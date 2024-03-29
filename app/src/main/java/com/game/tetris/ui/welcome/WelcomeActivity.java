package com.game.tetris.ui.welcome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.game.tetris.MichaelLog;
import com.game.tetris.base.BaseActivity;
import com.game.tetris.battle.BuildConfig;
import com.game.tetris.battle.R;
import com.game.tetris.dialog.DifficultyLevelDialog;
import com.game.tetris.dialog.GameModeDialog;
import com.game.tetris.dialog.SettingDialog;
import com.game.tetris.dialog.TetrisDialog;
import com.game.tetris.ui.GameActivity;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;

public class WelcomeActivity extends BaseActivity implements WelcomeVu {

    private WelcomePresenter presenter;
    private TextView tvTitle, tvTapToStart;
    private LinearLayout menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initPresenter();
        initView();
        presenter.onCreate();
    }

    private void initView() {
        menuView = findViewById(R.id.menu_view);
        tvTitle = findViewById(R.id.title);
        tvTapToStart = findViewById(R.id.tap_to_start);
        tvTapToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTapToStartClickListener();
            }
        });
        TextView tvPlay = findViewById(R.id.play_game);
        TextView tvExit = findViewById(R.id.exit);
        TextView tvSetting = findViewById(R.id.setting);
        tvSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onSettingClickListener();
            }
        });
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onPlayGameClickListener();
            }
        });
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onExitGameClickListener();
            }
        });
    }

    private void initPresenter() {
        presenter = new WelcomePresenterImpl(this);
    }

    @Override
    public void startBreathAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.breath);
        tvTapToStart.startAnimation(animation);
    }

    @Override
    public void setDisableTabToStart(boolean isShow) {
        tvTapToStart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void startToMoveTitle() {
        tvTitle.animate()
                .y(menuView.getY() - tvTitle.getHeight())
                .setDuration(500)
                .withEndAction(() -> presenter.onStartToShowMenu())
                .start();
    }

    @Override
    public void stopTabToStartAnimation() {
        tvTapToStart.clearAnimation();
    }

    @Override
    public void showMenu() {
        menuView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.menu_show_animation);
        menuView.startAnimation(animation);
    }

    @Override
    public void finishApp() {
        finish();
    }

    @Override
    public void goToGamePage(int mode) {
        Intent it = new Intent(this, GameActivity.class);
        it.putExtra("mode", mode);
        startActivity(it);
    }

    @Override
    public void showSettingDialog() {
        SettingDialog dialog = new SettingDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void showGameModeDialog() {
        GameModeDialog dialog = new GameModeDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setOnGameModeItemClickListener(new GameModeDialog.OnGameModeItemClickListener() {
            @Override
            public void onLevelStart() {
                presenter.onLevelStartClick();

            }

            @Override
            public void onPractise() {
                presenter.onPractiseClick();
            }
        });
    }

    @Override
    public void showToast() {
        Toast.makeText(this, getString(R.string.coming_soon), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDifficultyLeveDialog() {
        DifficultyLevelDialog dialog = new DifficultyLevelDialog();
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setOnDifficultyConfirmClickListener(new DifficultyLevelDialog.OnDifficultyConfirmClickListener() {
            @Override
            public void onConfirmClick() {
                presenter.onConfirmGameLevelClickListener();
            }
        });
    }

    @Override
    public void onCheckAppVersionUpdate() {
        AppUpdaterUtils updaterUtils = new AppUpdaterUtils(this)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON("https://raw.githubusercontent.com/MichaelLin-TPE/Tetris/master/release_note.json")
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
                        MichaelLog.i("version : " + update.getLatestVersion() + " version code : " + update.getLatestVersionCode());
                        MichaelLog.i("release note : " + update.getReleaseNotes());
                        presenter.onCheckAppVersionListener(update);
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        MichaelLog.i("onFailed : " + error.toString());
                        presenter.onCheckUpdateFail();
                    }
                });
        updaterUtils.start();
    }

    @Override
    public void showUpdateDialog(Update update) {
        String title = "New Update : \n"+update.getReleaseNotes();
        showTetrisDialog(title, getString(R.string.next_time), getString(R.string.go_update), new TetrisDialog.OnTetrisDialogListener() {
            @Override
            public void onCancel() {
                presenter.onNextTimeClickListener();
            }

            @Override
            public void onPositiveButton() {
                presenter.onGoUpdateClickListener();
            }
        });
    }

    @Override
    public void goGooglePlayTetrisPage() {
        String appPackageName = "com.game.tetris.battle";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        startActivity(intent);
    }
}