package com.game.tetris.tool;

import android.app.Activity;
import android.media.MediaPlayer;

import com.game.tetris.R;

public class MusicTool {

    private static MediaPlayer soundEffectPlayer;
    private static MediaPlayer soundUpgradePlayer;

    public static void playSoundEffect(Activity activity) {
        if (soundEffectPlayer == null) {
            soundEffectPlayer = MediaPlayer.create(activity, R.raw.move_cube);
            soundEffectPlayer.setVolume(0.5f,0.5f);
            soundEffectPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.release();
                    soundEffectPlayer = null;
                }
            });
        }
        if (!soundEffectPlayer.isPlaying()) {
            soundEffectPlayer.start();
        }
    }

    public static void onDestroy(){
        if (soundEffectPlayer != null){
            soundEffectPlayer.release();
            soundEffectPlayer = null;
        }
        if (soundUpgradePlayer != null){
            soundUpgradePlayer.release();
            soundUpgradePlayer = null;
        }
    }

    public static void playUpgradeMusic(Activity activity) {
        if (soundUpgradePlayer == null) {
            soundUpgradePlayer = MediaPlayer.create(activity, R.raw.upgrade);
            soundUpgradePlayer.setVolume(0.5f,0.5f);
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
