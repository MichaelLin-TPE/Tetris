package com.game.tetris.ui;

import static com.game.tetris.tool.CubeTool.LEVEL_MODE;
import static com.game.tetris.tool.CubeTool.PRACTISE_MODE;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.game.tetris.MichaelLog;
import com.game.tetris.base.BaseActivity;
import com.game.tetris.battle.R;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;
import com.game.tetris.dialog.GameOverDialog;
import com.game.tetris.dialog.TetrisDialog;
import com.game.tetris.tool.MusicTool;
import com.game.tetris.tool.SharedPreferTool;
import com.game.tetris.tool.Tool;

public class GameActivity extends BaseActivity implements GameVu {

    private GamePresenter presenter;
    private ConstraintLayout gameView, rootView;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TextView tvPoint,tvTarget,tvTimer;
    private View leftSupportLine, rightSupportLine;
    //    private Intent serviceIntent;
//    private MyMusicService myMusicService;
    private final MusicTool musicTool = new MusicTool();
    private LinearLayout preView;
    private boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        musicTool.initSoundPool(this);
        initPresenter();
        initView();
        int currentGameMode = getIntent().getExtras().getInt("mode",PRACTISE_MODE);
        showTargetView(currentGameMode == LEVEL_MODE);
        gameView.post(new Runnable() {
            @Override
            public void run() {
                presenter.onCreateGameView(currentGameMode);
            }
        });
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressedListener();
    }

    private void initPresenter() {
        presenter = new GamePresenterImpl(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        tvTarget = findViewById(R.id.target);
        preView = findViewById(R.id.pre_view);
        tvPoint = findViewById(R.id.point);
        tvPoint.setTag(0);
        gameView = findViewById(R.id.game_view);
        rootView = findViewById(R.id.root);
        tvTimer = findViewById(R.id.timer);

        TextView tvTurn = findViewById(R.id.game_button);
        TextView ivLeft = findViewById(R.id.tv_left);
        TextView ivRight = findViewById(R.id.tv_right);
        TextView tvDown = findViewById(R.id.down_button);
        String point = getString(R.string.point) + " " + tvPoint.getTag();
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
                        ivLeft.setActivated(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        ivLeft.setActivated(false);
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
                        ivRight.setActivated(true);
                        presenter.onRightPressDownListener();
                        break;
                    case MotionEvent.ACTION_UP:
                        ivRight.setActivated(false);
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
//        if (isBound) {
//            unbindService(serviceConnection);
//            isBound = false;
//        }


        musicTool.onDestroy();
    }

    @Override
    public void createGameViewBackground() {
        int[] location = new int[2];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            gameView.getLocationInWindow(location);
        } else {
            gameView.getLocationOnScreen(location);
        }

        presenter.createLatticeDataList(location[0], location[1], location[0] + gameView.getWidth(), location[0], location[1] + gameView.getHeight());
    }

    @Override
    public void showLattice(LatticeData data, float latticeSize, float latticeHeight) {
        View latticeView = View.inflate(this, R.layout.item_lattice_bg, null);
        rootView.addView(latticeView);

        latticeView.post(() -> {
            View lattice = latticeView.findViewById(R.id.lattice);
//            lattice.setBackground(ContextCompat.getDrawable(this, data.getColor()));
            int heightInPixels = Tool.convertToPixels(latticeHeight);
            int widthInPixels = Tool.convertToPixels(latticeSize);
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) latticeView.getLayoutParams();
            params.height = (int) latticeHeight;
            params.width = (int) latticeSize;
            latticeView.setX(data.getX());
            latticeView.setY(data.getY());
        });

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
        if (isDestroyed() || isFinishing()) {
            return;
        }
        //先暫時這樣處理
        try {
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
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public ConstraintLayout getRootView() {
        return rootView;
    }

    @Override
    public void showPoint(int point) {
        String pointContent = getString(R.string.point) + Tool.numberFormat(((Integer) (tvPoint.getTag()) + point));
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
//        serviceIntent = new Intent(this, MyMusicService.class);
//        serviceIntent.putExtra("musicResourceId", R.raw.game_music);
//        startService(serviceIntent);
//
//        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);
        musicTool.playSoundBackground(this);
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
    public String getGameOverContentWithHistoryScore() {
        return getString(R.string.game_over) + " " + Tool.numberFormat((Integer) tvPoint.getTag()) + getString(R.string.highest_score) + " " + Tool.numberFormat(SharedPreferTool.getInstance().getPoint());
    }

    @Override
    public String getGameOverContentWithoutHistoryScore() {
        return getString(R.string.game_over) + " " + Tool.numberFormat((Integer) tvPoint.getTag());
    }

    @Override
    public String getGameOverContentWithHighScore() {
        return getString(R.string.win_high_score) + " " + Tool.numberFormat(getCurrentPoint());
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
    public void showConfirmExitDialog() {
        showTetrisDialog(getString(R.string.confirm_exit), getString(R.string.cancel), getString(R.string.exit), new TetrisDialog.OnTetrisDialogListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onPositiveButton() {
                presenter.onExitClickListener();
            }
        });

    }

    @Override
    public void moveDownCube(View cubeView, CubeData cubeData, float y, int index, int lastIndex) {
        cubeData.getCubeView().animate()
                .y(y)
                .setDuration(100)
                .withEndAction(() -> {
                    cubeData.getCubeView().setY(y);
                    cubeData.setY(y);
                    if (index == lastIndex) {
                        presenter.onFinishCubeStraightDown();
                    }

                }).start();
    }

    @Override
    public int getCurrentPoint() {
        return (int) tvPoint.getTag();
    }

    @Override
    public void savePoint() {
        SharedPreferTool.getInstance().savePoint((Integer) tvPoint.getTag());
    }

    @Override
    public void showPreCube(int layoutId) {
        preView.removeAllViews();
        View view = View.inflate(this,layoutId,null);
        preView.addView(view);
    }

    @Override
    public void moveCube(CubeData cubeData, int index, int lastIndex) {
        cubeData.getCubeView().animate()
                .y(cubeData.getY())
                .setDuration(100)
                .withEndAction(() -> {
                    cubeData.getCubeView().setY(cubeData.getY());
                    if (index == lastIndex) {
                        presenter.reCreateCube();
                    }
                }).start();
    }

    @Override
    public void showTargetView(boolean isShow) {
        tvTarget.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showTargetPoint(int targetPoint) {
        tvTarget.setText(getString(R.string.traget)+" "+Tool.convertToInt(targetPoint));
    }

    @Override
    public void showWinLevelDialog() {
        String content = "Congratulations!!\nYou have passed the "+SharedPreferTool.getInstance().getGameLevel()+" level!";
        showTetrisDialog(content, getString(R.string.exit), getString(R.string.next_level), new TetrisDialog.OnTetrisDialogListener() {
            @Override
            public void onCancel() {
                presenter.onExitClickListener();
            }

            @Override
            public void onPositiveButton() {
                presenter.onReplayClickListener();
            }
        });
    }

    @Override
    public String getWannaTryAgain() {
        return getString(R.string.wanna_try_again);
    }

    @Override
    public void playWinMusic() {
        musicTool.playSoundWin(this);
    }

    @Override
    public void startPlayLevelMusic() {
        musicTool.playLevelMusic(this);
    }

    @Override
    public void showCountDownTime(Long timeMillis) {
        tvTimer.setText(Tool.getTime(timeMillis));
    }

    @Override
    public void showCountDownTime(boolean isShow) {
        tvTimer.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        myMusicService.restoreMusic();
        musicTool.restartMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        myMusicService.pauseMusic();
        musicTool.pauseMusic();
    }

//    private final ServiceConnection serviceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            MyMusicService.LocalBinder binder = (MyMusicService.LocalBinder) service;
//            myMusicService = binder.getService();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            myMusicService = null;
//        }
//    };


}