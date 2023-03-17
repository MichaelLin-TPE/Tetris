package com.game.tetris.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.game.tetris.battle.R;
import com.game.tetris.tool.SharedPreferTool;

public class GameModeDialog extends DialogFragment {


    private OnGameModeItemClickListener onGameModeItemClickListener;

    public void setOnGameModeItemClickListener(OnGameModeItemClickListener onGameModeItemClickListener) {
        this.onGameModeItemClickListener = onGameModeItemClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_mode_dialog_layout, null);
        Dialog dialog = new Dialog(getActivity());
        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        initView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 300, getActivity().getResources().getDisplayMetrics()));
        wlp.height = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 255, getActivity().getResources().getDisplayMetrics()));
        dialog.getWindow().setWindowAnimations(R.style.MyDialogFragmentStyle);
        return dialog;
    }

    private void initView(View view) {
        TextView tvLevel = view.findViewById(R.id.title1);
        TextView tvPractise = view.findViewById(R.id.title2);
        tvLevel.setText(getString(R.string.level)+" "+SharedPreferTool.getInstance().getGameLevel());
        tvLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGameModeItemClickListener.onLevelStart();
                dismiss();
            }
        });

        tvPractise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGameModeItemClickListener.onPractise();
                dismiss();
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public interface OnGameModeItemClickListener{
        void onLevelStart();
        void onPractise();
    }


}
