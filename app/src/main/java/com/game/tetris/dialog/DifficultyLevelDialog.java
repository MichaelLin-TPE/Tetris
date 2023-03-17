package com.game.tetris.dialog;

import static android.content.Context.VIBRATOR_SERVICE;
import static com.game.tetris.tool.CubeTool.ADVANCE_MODE_SPEED;
import static com.game.tetris.tool.CubeTool.EASY_MODE_SPEED;
import static com.game.tetris.tool.CubeTool.EXPERT_MODE_SPEED;
import static com.game.tetris.tool.CubeTool.HARD_MODE_SPEED;
import static com.game.tetris.tool.CubeTool.NORMAL_MODE_SPEED;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.game.tetris.battle.R;
import com.game.tetris.tool.SharedPreferTool;

public class DifficultyLevelDialog extends DialogFragment {


    private OnDifficultyConfirmClickListener onDifficultyConfirmClickListener;

    public void setOnDifficultyConfirmClickListener(OnDifficultyConfirmClickListener onDifficultyConfirmClickListener){
        this.onDifficultyConfirmClickListener = onDifficultyConfirmClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.difficulty_level_dialog, null);
        Dialog dialog = new Dialog(getActivity());
        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        initView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300, getActivity().getResources().getDisplayMetrics()));
        wlp.height = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 400, getActivity().getResources().getDisplayMetrics()));
        dialog.getWindow().setWindowAnimations(R.style.MyDialogFragmentStyle);
        return dialog;
    }

    private void initView(View view) {
        RadioButton easyBtn = view.findViewById(R.id.easy_btn);
        RadioButton normalBtn = view.findViewById(R.id.normal_btn);
        RadioButton hardBtn = view.findViewById(R.id.difficult_btn);
        RadioButton advanceBtn = view.findViewById(R.id.advanced_btn);
        RadioButton expertBtn = view.findViewById(R.id.expert_btn);
        TextView tvConfirm = view.findViewById(R.id.confirm_button);

        easyBtn.setChecked(SharedPreferTool.getInstance().getGameSpeed() == EASY_MODE_SPEED);
        normalBtn.setChecked(SharedPreferTool.getInstance().getGameSpeed() == NORMAL_MODE_SPEED);
        hardBtn.setChecked(SharedPreferTool.getInstance().getGameSpeed() == HARD_MODE_SPEED);
        advanceBtn.setChecked(SharedPreferTool.getInstance().getGameSpeed() == ADVANCE_MODE_SPEED);
        expertBtn.setChecked(SharedPreferTool.getInstance().getGameSpeed() == EXPERT_MODE_SPEED);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDifficultyConfirmClickListener.onConfirmClick();
                dismiss();
            }
        });

        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalBtn.setChecked(false);
                hardBtn.setChecked(false);
                advanceBtn.setChecked(false);
                expertBtn.setChecked(false);
                SharedPreferTool.getInstance().saveGameSpeed(EASY_MODE_SPEED);
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(50);
            }
        });
        normalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyBtn.setChecked(false);
                hardBtn.setChecked(false);
                advanceBtn.setChecked(false);
                expertBtn.setChecked(false);
                SharedPreferTool.getInstance().saveGameSpeed(NORMAL_MODE_SPEED);
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(50);
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalBtn.setChecked(false);
                easyBtn.setChecked(false);
                advanceBtn.setChecked(false);
                expertBtn.setChecked(false);
                SharedPreferTool.getInstance().saveGameSpeed(HARD_MODE_SPEED);
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(50);
            }
        });
        advanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalBtn.setChecked(false);
                hardBtn.setChecked(false);
                easyBtn.setChecked(false);
                expertBtn.setChecked(false);
                SharedPreferTool.getInstance().saveGameSpeed(ADVANCE_MODE_SPEED);
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(50);
            }
        });
        expertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                normalBtn.setChecked(false);
                hardBtn.setChecked(false);
                advanceBtn.setChecked(false);
                easyBtn.setChecked(false);
                SharedPreferTool.getInstance().saveGameSpeed(EXPERT_MODE_SPEED);
                Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(50);
            }
        });




    }

    public interface OnDifficultyConfirmClickListener{
        void onConfirmClick();
    }

}
