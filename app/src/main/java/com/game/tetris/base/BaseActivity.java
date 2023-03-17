package com.game.tetris.base;

import androidx.appcompat.app.AppCompatActivity;

import com.game.tetris.dialog.TetrisDialog;

public class BaseActivity extends AppCompatActivity {


    public void showTetrisDialog(String title, String cancelButtonContent, String positiveButtonContent, TetrisDialog.OnTetrisDialogListener onTetrisDialogListener) {
        TetrisDialog dialog = new TetrisDialog();
        dialog.setContent(title,cancelButtonContent,positiveButtonContent);
        dialog.setOnTetrisDialogListener(new TetrisDialog.OnTetrisDialogListener() {
            @Override
            public void onCancel() {
                onTetrisDialogListener.onCancel();
            }

            @Override
            public void onPositiveButton() {
                onTetrisDialogListener.onPositiveButton();
            }
        });
        dialog.show(getSupportFragmentManager(), "dialog");
    }

}
