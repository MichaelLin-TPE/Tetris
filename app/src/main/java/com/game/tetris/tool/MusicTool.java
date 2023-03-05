package com.game.tetris.tool;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import com.game.tetris.MichaelLog;
import com.game.tetris.R;

public class MusicTool {

    private MediaPlayer soundEffectPlayer;
    private MediaPlayer soundUpgradePlayer;

    public void playSoundEffect(Activity activity) {
        if (soundEffectPlayer == null) {
            soundEffectPlayer = MediaPlayer.create(activity, R.raw.move_cube);
            soundEffectPlayer.setVolume(0.5f, 0.5f);
            soundEffectPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    MichaelLog.i("完成播放");
                    mediaPlayer.release();
                    soundEffectPlayer = null;
                }
            });
        }
        if (!soundEffectPlayer.isPlaying()) {
            MichaelLog.i("play music");
            soundEffectPlayer.start();
        }
    }

    public void onDestroy() {
        if (soundEffectPlayer != null) {
            soundEffectPlayer.release();
            soundEffectPlayer = null;
        }
        if (soundUpgradePlayer != null) {
            soundUpgradePlayer.release();
            soundUpgradePlayer = null;
        }
    }

    public void playUpgradeMusic(Activity activity) {
        if (soundUpgradePlayer == null) {
            soundUpgradePlayer = MediaPlayer.create(activity, R.raw.upgrade);
            soundUpgradePlayer.setVolume(0.5f, 0.5f);
            soundUpgradePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                    soundUpgradePlayer = null;
                }
            });
        }
        if (!soundUpgradePlayer.isPlaying()) {
            soundUpgradePlayer.start();
        }
    }
}
