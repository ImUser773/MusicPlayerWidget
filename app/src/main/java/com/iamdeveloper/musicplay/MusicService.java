package com.iamdeveloper.musicplay;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by IamDeveloper on 9/11/2016.
 */
public class MusicService extends Service {
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Log.i("bundle", bundle.toString());
            String path = bundle.getString("PATH");
            String action = bundle.getString("ACTION");
            Log.i("Music", "Action : " + action);

            if (path != null) {
                Log.i("path", path);
                try {
                    mediaPlayer.stop();
                    mediaPlayer.reset();

                    Log.i("setDataSource", path);
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.setVolume(100, 100);
                    mediaPlayer.prepare();
                    mediaPlayer.start();


                } catch (IOException e) {
                    Log.d("Exception",e.toString());
                    e.printStackTrace();

                }
            }


            if (action != null && action.equals("PLAY")) {
                Log.i("Music", "Music play");
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }


            } else if (action != null && action.equals("PAUSE")) {
                Log.i("Music", "Music pause");
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
            }else if (action!=null&&action.equals("STOP")){
                Log.i("Music","Music STOP");
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.reset();

                }
            }
        }


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("onDestroy", "onDestroy");
    }
}
