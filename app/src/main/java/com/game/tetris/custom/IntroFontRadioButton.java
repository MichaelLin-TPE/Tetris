package com.game.tetris.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.game.tetris.battle.R;

public class IntroFontRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {


    public IntroFontRadioButton(Context context) {
        super(context);
        init();
    }

    public IntroFontRadioButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntroFontRadioButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.intro_rust);
        setTypeface(typeface);
    }


}
