package com.game.tetris.ui;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Vibrator;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.game.tetris.MichaelLog;
import com.game.tetris.R;
import com.game.tetris.base.BaseActivity;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;
import com.game.tetris.dialog.GameOverDialog;
import com.game.tetris.service.MyMusicService;
import com.game.tetris.tool.MusicTool;

public class GameActivity extends BaseActivity implements GameVu {

    private GamePresenter presenter;
    private ConstraintLayout gameView, rootView;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TextView tvPoint;
    private View leftSupportLine, rightSupportLine;
    private Intent serviceIntent;
    private MyMusicService myMusicService;
    private final MusicTool musicTool = new MusicTool();
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        initView();

    }

    private void initPresenter() {
        presenter = new GamePresenterImpl(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        tvPoint = findViewById(R.id.point);
        tvPoint.setTag(0);
        gameView = findViewById(R.id.game_view);
        rootView = findViewById(R.id.root);
        gameView.post(new Runnable() {
            @Override
            public void run() {
                presenter.onCreateGameView();
            }
        });

        TextView tvTurn = findViewById(R.id.game_button);
        ImageView ivLeft = findViewById(R.id.iv_left);
        ImageView ivRight = findViewById(R.id.iv_right);
        TextView tvDown = findViewById(R.id.down_button);
        String point = getString(R.string.point) + tvPoint.getTag();
        tvPoint.setText(point);

        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDownButtonClickListener();
            }
        });

        ivLeft.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        presenter.onLeftPressDownListener();
                        break;
                    case MotionEvent.ACTION_UP:
                        presenter.onLeftPressUpListener();
                        break;
                }

                return true;
            }
        });

        ivRight.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        presenter.onRightPressDownListener();
                        break;
                    case MotionEvent.ACTION_UP:
                        presenter.onRightPressUpListener();
                        break;
                }

                return true;
            }
        });

        tvTurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onTurnCubeButtonClickListener();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }


        musicTool.onDestroy();
    }

    @Override
    public void createGameViewBackground() {
        presenter.createLatticeDataList(gameView.getX(), gameView.getY(), gameView.getRight(), gameView.getLeft(), gameView.getBottom());
    }

    @Override
    public void showLattice(LatticeData data, float latticeSize, float latticeHeight) {
        View latticeView = View.inflate(this, R.layout.item_lattice_bg, null);
        View lattice = latticeView.findViewById(R.id.lattice);
//        lattice.setBackground(ContextCompat.getDrawable(this, data.getColor()));
        ViewGroup.LayoutParams params = lattice.getLayoutParams();
        params.height = (int) latticeSize;
        params.width = (int) latticeHeight;
        latticeView.setX(data.getX());
        latticeView.setY(data.getY());
        rootView.addView(latticeView);
    }

    @Override
    public void showCube(CubeData data) {
        View cubeView = View.inflate(this, R.layout.item_cube_layout, null);
        ImageView cube = cubeView.findViewById(R.id.cube);
        cube.setImageResource(data.getBg());
        ViewGroup.LayoutParams params = cube.getLayoutParams();
        params.height = (int) data.getHeight();
        params.width = (int) data.getWidth();
        cubeView.setX(data.getX());
        cubeView.setY(data.getY());
        rootView.addView(cubeView);
        data.setCubeView(cubeView);
    }

    @Override
    public void showSupportCube(CubeData data) {
        View cubeView = View.inflate(this, R.layout.item_cube_layout, null);
        ImageView cube = cubeView.findViewById(R.id.cube);
        cube.setImageResource(data.getBg());
        ViewGroup.LayoutParams params = cube.getLayoutParams();
        params.height = (int) data.getHeight();
        params.width = (int) data.getWidth();
        cubeView.setX(data.getX());
        cubeView.setY(data.getY());
        rootView.addView(cubeView);
        data.setCubeView(cubeView);
        cubeView.setAlpha(0.4f);
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void showGameOver(String title) {
        GameOverDialog dialog = new GameOverDialog();
        dialog.setTitle(title);
        dialog.show(getSupportFragmentManager(), "dialog");
        dialog.setOnGameOverDialogListener(new GameOverDialog.OnGameOverDialogListener() {
            @Override
            public void onRePlayClick() {
                presenter.onReplayClickListener();
            }

            @Override
            public void onExitClick() {
                presenter.onExitClickListener();
            }
        });

    }

    @Override
    public ConstraintLayout getRootView() {
        return rootView;
    }

    @Override
    public void showPoint(int point) {
        String pointContent = getString(R.string.point) + ((Integer) (tvPoint.getTag()) + point);
        tvPoint.setText(pointContent);
        tvPoint.setTag((Integer) (tvPoint.getTag()) + point);
    }

    @Override
    public void showSupportLine(float leftX, float rightX, float topY, float bottomY, float rightBottomY) {

        leftSupportLine = View.inflate(this, R.layout.support_line_layout, null);
        rightSupportLine = View.inflate(this, R.layout.support_line_layout, null);
        leftSupportLine.setVisibility(View.INVISIBLE);
        rightSupportLine.setVisibility(View.INVISIBLE);
        rootView.addView(leftSupportLine);
        rootView.addView(rightSupportLine);
        leftSupportLine.post(new Runnable() {
            @Override
            public void run() {
                leftSupportLine.setX(leftX);
                leftSupportLine.setY(topY);
                leftSupportLine.getLayoutParams().height = (int) (bottomY - topY);
                leftSupportLine.requestLayout();
                leftSupportLine.setVisibility(View.VISIBLE);
            }
        });
        rightSupportLine.post(new Runnable() {
            @Override
            public void run() {
                rightSupportLine.setX(rightX);
                rightSupportLine.setY(topY);
                rightSupportLine.getLayoutParams().height = (int) (rightBottomY - topY);
                rightSupportLine.requestLayout();
                rightSupportLine.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void moveSupportLine(float leftX, float rightX, float topY, float bottomY, float rightBottomY) {
        leftSupportLine.setX(leftX);
        rightSupportLine.setX(rightX);
        leftSupportLine.setY(topY);
        rightSupportLine.setY(topY);
        rightSupportLine.getLayoutParams().height = (int) (rightBottomY - topY);
        rightSupportLine.requestLayout();
        leftSupportLine.getLayoutParams().height = (int) (bottomY - topY);
        leftSupportLine.requestLayout();
    }

    @Override
    public void removeSupportLine() {
        if (leftSupportLine != null && rightSupportLine != null) {
            rootView.removeView(leftSupportLine);
            rootView.removeView(rightSupportLine);
        }
    }

    @Override
    public void removeSupportCube(View cubeView) {
        rootView.removeView(cubeView);
    }

    @Override
    public void startPlayBackgroundMusic() {
        MichaelLog.i("startPlayBackgroundMusic");
        serviceIntent = new Intent(this, MyMusicService.class);
        serviceIntent.putExtra("musicResourceId", R.raw.game_music);
        startService(serviceIntent);

        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void startPlayMoveMusic() {
        musicTool.playSoundEffect(this);
    }

    @Override
    public void startPlayUpgradeMusic() {
        musicTool.playUpgradeMusic(this);
    }

    @Override
    public void startVibrator(long timeMillis) {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(timeMillis);
    }

    @Override
    public String getGameOverContent() {
        return getString(R.string.game_over) + " " + tvPoint.getTag();
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void resetPoint() {
        tvPoint.setTag(0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        myMusicService.restoreMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myMusicService.pauseMusic();
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyMusicService.LocalBinder binder = (MyMusicService.LocalBinder) service;
            myMusicService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myMusicService = null;
        }
    };


}