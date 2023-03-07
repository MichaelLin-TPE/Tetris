package com.game.tetris.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.game.tetris.battle.R;
import com.game.tetris.custom.IntroFontTextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TetrisDialog extends DialogFragment {

    private String title, cancelButtonContent, positiveButtonContent;
    private IntroFontTextView tvTitle;
    private int index = 0;
    private Disposable disposable;
    private OnTetrisDialogListener onTetrisDialogListener;

    public void setOnTetrisDialogListener(OnTetrisDialogListener onTetrisDialogListener) {
        this.onTetrisDialogListener = onTetrisDialogListener;
    }

    public void setContent(String title, String cancelButtonContent, String positiveButtonContent) {
        this.title = title;
        this.cancelButtonContent = cancelButtonContent;
        this.positiveButtonContent = positiveButtonContent;
    }

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.game_over_dialog_layout, null);
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
                TypedValue.COMPLEX_UNIT_DIP, 225, getActivity().getResources().getDisplayMetrics()));
        dialog.getWindow().setWindowAnimations(R.style.MyDialogFragmentStyle);
        setCancelable(false);
        return dialog;
    }

    private void initView(View view) {
        IntroFontTextView tvCancel = view.findViewById(R.id.cancel_button);
        IntroFontTextView tvPlay = view.findViewById(R.id.replay_button);
        tvTitle = view.findViewById(R.id.title);
        tvCancel.setText(cancelButtonContent);
        tvPlay.setText(positiveButtonContent);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTetrisDialogListener.onCancel();
                dismiss();
            }
        });
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTetrisDialogListener.onPositiveButton();
                dismiss();
            }
        });

        startAnimation();

    }

    private void startAnimation() {

        disposable = Observable.interval(50, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    tvTitle.setText(title.substring(0, index++));
                    if (index > title.length()) {
                        disposable.dispose();
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    public interface OnTetrisDialogListener {
        void onCancel();

        void onPositiveButton();
    }

}
