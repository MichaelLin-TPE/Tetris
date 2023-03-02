package com.game.tetris.ui.welcome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.game.tetris.R;
import com.game.tetris.base.BaseActivity;
import com.game.tetris.ui.GameActivity;

public class WelcomeActivity extends BaseActivity implements WelcomeVu {

    private WelcomePresenter presenter;
    private TextView tvTitle,tvTapToStart;
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
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.breath);
        tvTapToStart.startAnimation(animation);
    }

    @Override
    public void setDisableTabToStart(boolean isShow) {
        tvTapToStart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void startToMoveTitle() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvTitle,"translationY",-800f);
        animator.setDuration(1000);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                presenter.onStartToShowMenu();
            }
        });
        animator.start();
    }

    @Override
    public void stopTabToStartAnimation() {
        tvTapToStart.clearAnimation();
    }

    @Override
    public void showMenu() {
        menuView.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.menu_show_animation);
        menuView.startAnimation(animation);
    }

    @Override
    public void finishApp() {
        finish();
    }

    @Override
    public void goToGamePage() {
        Intent it = new Intent(this, GameActivity.class);
        startActivity(it);
    }
}