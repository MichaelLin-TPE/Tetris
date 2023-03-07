package com.game.tetris.tool;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import com.game.tetris.MichaelLog;
import com.game.tetris.R;

public class MusicTool {

    private MediaPlayer soundEffectPlayer;
    private MediaPlayer soundUpgradePlayer;
    private MediaPlayer soundBackgroundPlayer;

    public void playSoundBackground(Activity activity){
        if (soundBackgroundPlayer == null){
            soundBackgroundPlayer = MediaPlayer.create(activity,R.raw.game_music);
            soundBackgroundPlayer.setVolume(0.6f,0.6f);
            soundBackgroundPlayer.setLooping(true);
        }
        soundBackgroundPlayer.start();
    }

    public void playSoundEffect(Activity activity) {
        if (soundEffectPlayer == null) {
            soundEffectPlayer = MediaPlayer.create(activity, R.raw.move_cube);
            soundEffectPlayer.setVolume(0.5f, 0.5f);
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

    public void onDestroy() {
        if (soundEffectPlayer != null) {
            soundEffectPlayer.release();
            soundEffectPlayer = null;
        }
        if (soundUpgradePlayer != null) {
            soundUpgradePlayer.release();
            soundUpgradePlayer = null;
        }
        if (soundBackgroundPlayer != null){
            soundBackgroundPlayer.release();
            soundBackgroundPlayer = null;
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

    public void restartMusic(){
        if (soundBackgroundPlayer != null){
            soundBackgroundPlayer.start();
        }
    }

    public void pauseMusic() {
        if (soundBackgroundPlayer != null){
            soundBackgroundPlayer.pause();
        }
    }
}
