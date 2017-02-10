package com.dextos.xavier.R2D2.fragments;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Dextos on 10/02/2017.
 */

public class Music extends Service implements MediaPlayer.OnCompletionListener {

    boolean paused = true;
    private static final int REQUEST_CODE_CONSTANT_EXTERNAL_STORAGE = 0;
    MediaPlayer mediaPlayer = new MediaPlayer();
    Uri myUri;

    @Override
    public void onCreate() {
        myUri =Uri.parse(this.getApplicationContext().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE)
                .getString("uri",null));
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myUri =Uri.parse(this.getApplicationContext().getSharedPreferences("com.dextos.xavier.R2D2", Context.MODE_PRIVATE)
                .getString("uri",null));
        try {
            mediaPlayer.setDataSource(getApplicationContext(),myUri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();}
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.release();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
    }
}
