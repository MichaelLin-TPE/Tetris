package com.game.tetris.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.game.tetris.MichaelLog;

public class MyMusicService extends Service implements MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new LocalBinder();

    public void restoreMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    public class LocalBinder extends Binder {
        public MyMusicService getService() {
            // 返回當前的MyMusicService對象
            return MyMusicService.this;
        }
    }

    public MyMusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(0.3f,0.3f);
    }

    public void pauseMusic(){
        if (mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MichaelLog.i("onStartCommand");
        int musicResourceId = intent.getIntExtra("musicResourceId",0);
        AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(musicResourceId);
        try {
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepareAsync();
        }catch (Exception e){
            MichaelLog.i("有錯 : "+e);
            e.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        MichaelLog.i("onPrepared");

        mediaPlayer.start();
    }
}
