package com.game.tetris.custom;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.game.tetris.R;

public class IntroFontTextView extends androidx.appcompat.widget.AppCompatTextView {

    public IntroFontTextView(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.intro_rust);
        setTypeface(typeface);
    }

    public IntroFontTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntroFontTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
}
