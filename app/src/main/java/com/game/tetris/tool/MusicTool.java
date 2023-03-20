package com.game.tetris.tool;

import android.app.Activity;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.game.tetris.battle.R;

public class MusicTool {
    private MediaPlayer soundBackgroundPlayer,soundWinPlayer;

    private SoundPool soundEffectPool,soundUpgradePool;
    private int effectSoundId,upgradeSoundId;

    public void initSoundPool(Activity activity){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundEffectPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
            soundUpgradePool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            soundEffectPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
            soundUpgradePool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        }
        effectSoundId = soundEffectPool.load(activity,R.raw.move_cube,1);
        upgradeSoundId = soundUpgradePool.load(activity,R.raw.upgrade,1);
    }


    public void playSoundBackground(Activity activity) {
        if (soundBackgroundPlayer == null) {
            soundBackgroundPlayer = MediaPlayer.create(activity, R.raw.game_music);
            soundBackgroundPlayer.setVolume(0.6f, 0.6f);
            soundBackgroundPlayer.setLooping(true);
        }
        soundBackgroundPlayer.start();
    }


    public void playSoundWin(Activity activity) {
        if (soundWinPlayer == null) {
            soundWinPlayer = MediaPlayer.create(activity, R.raw.win);
            soundWinPlayer.setVolume(0.6f, 0.6f);
        }
        soundWinPlayer.start();
    }


    public void playSoundEffect(Activity activity) {

        soundEffectPool.play(effectSoundId,0.5f,0.5f,1,0,1);
    }

    public void onDestroy() {
        if (soundBackgroundPlayer != null) {
            soundBackgroundPlayer.release();
            soundBackgroundPlayer = null;
        }
        if (soundWinPlayer != null){
            soundWinPlayer.release();
            soundWinPlayer = null;
        }
    }

    public void playUpgradeMusic(Activity activity) {
        soundUpgradePool.play(upgradeSoundId,0.5f,0.5f,1,0,1);
    }

    public void restartMusic() {
        if (soundBackgroundPlayer != null) {
            soundBackgroundPlayer.start();
        }
    }

    public void pauseMusic() {
        if (soundBackgroundPlayer != null) {
            soundBackgroundPlayer.pause();
        }
    }
}
