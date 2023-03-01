package com.game.tetris.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.game.tetris.R;
import com.game.tetris.base.BaseActivity;
import com.game.tetris.bean.CubeData;
import com.game.tetris.bean.LatticeData;

public class GameActivity extends BaseActivity implements GameVu {

    private GamePresenter presenter;
    private ConstraintLayout gameView, rootView;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TextView tvPoint;



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
        String point = getString(R.string.point) +tvPoint.getTag();
        tvPoint.setText(point);

        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDownButtonClickListener();
            }
        });
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onLeftButtonClickListener();
            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRightButtonClickListener();
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
    public void createGameViewBackground() {
        presenter.createLatticeDataList(gameView.getX() ,gameView.getY(),gameView.getRight(),gameView.getLeft(),gameView.getBottom());
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
    public Handler getHandler() {
        return handler;
    }

    @Override
    public void showGameOver() {
        Toast.makeText(this,"GameOver",Toast.LENGTH_LONG).show();
    }

    @Override
    public ConstraintLayout getRootView() {
        return rootView;
    }

    @Override
    public void showPoint(int point) {
        String pointContent = getString(R.string.point) + ((Integer)(tvPoint.getTag()) + point);
        tvPoint.setText(pointContent);
        tvPoint.setTag((Integer)(tvPoint.getTag()) + point);
    }

}