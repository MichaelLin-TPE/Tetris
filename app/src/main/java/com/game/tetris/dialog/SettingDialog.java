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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;

import com.game.tetris.battle.R;
import com.game.tetris.tool.SharedPreferTool;

public class SettingDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.setting_dialog_layout, null);
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
                TypedValue.COMPLEX_UNIT_DIP, 250, getActivity().getResources().getDisplayMetrics()));
        wlp.height = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 225, getActivity().getResources().getDisplayMetrics()));
        dialog.getWindow().setWindowAnimations(R.style.MyDialogFragmentStyle);
        return dialog;
    }

    private void initView(View view) {
        SwitchCompat switchCompat = view.findViewById(R.id.switch_support_cube);
        switchCompat.setChecked(SharedPreferTool.getInstance().isActiveSupportCube());

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                SharedPreferTool.getInstance().saveIsActiveSupportCube(isChecked);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
