package com.iamdeveloper.musicplay;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;


public class MainActivity extends AppWidgetProvider {


    public static String PLAY_MUSIC = "play";
    public static String PAUSE_MUSIC = "pause";
    public static String STOP_MUSIC = "stop";
    public static String LIST_MUSIC = "list";
    public static String GET_PATH = "com.iamdeveloper.PATH";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {




        int currentWidgetId = appWidgetIds.length;

        for(int i = 0; i < currentWidgetId;i++){
            int widgetId = appWidgetIds[i];
            Log.i("currentWidgetId",""+currentWidgetId);
            Toast.makeText(context,"onUpdate",Toast.LENGTH_SHORT).show();

            RemoteViews remoteView = new RemoteViews
                    (context.getPackageName(),R.layout.activity_main);





            Intent intent = new Intent(context,MainActivity.class);
            intent.setAction(PLAY_MUSIC);
            PendingIntent pendingIntent = PendingIntent.getBroadcast
                    (context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btn_play,pendingIntent);

            intent.setAction(PAUSE_MUSIC);
            pendingIntent = PendingIntent.getBroadcast
                    (context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btn_pause,pendingIntent);

            intent.setAction(STOP_MUSIC);
            pendingIntent = PendingIntent.getBroadcast
                    (context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btn_stop,pendingIntent);

            intent.setAction(LIST_MUSIC);
            pendingIntent = PendingIntent.getBroadcast
                    (context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setOnClickPendingIntent(R.id.btn_list,pendingIntent);


            appWidgetManager.updateAppWidget(widgetId,remoteView);

        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Bundle bundle = intent.getExtras();
        String path = null;


        if (intent.getAction().equals(GET_PATH)){
            if(bundle!=null){
                path = bundle.getString("DATA");
                Toast.makeText(context,path,Toast.LENGTH_SHORT).show();
                Intent serviceIntent = new Intent(context,MusicService.class);

                Bundle extra = new Bundle();
                extra.putString("PATH",path);
                serviceIntent.putExtras(extra);
                context.getApplicationContext().startService(serviceIntent);
            }

        }

        if (intent.getAction().equals(PLAY_MUSIC)){

            Intent serviceIntent = new Intent(context,MusicService.class);
            serviceIntent.putExtra("ACTION","PLAY");
            context.getApplicationContext().startService(serviceIntent);

            Toast.makeText(context,"PLAY_MUSIC",Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().equals(PAUSE_MUSIC)){
            Intent serviceIntent = new Intent(context,MusicService.class);
            serviceIntent.putExtra("ACTION","PAUSE");
            context.getApplicationContext().startService(serviceIntent);
            Toast.makeText(context,"PAUSE_MUSIC",Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().equals(STOP_MUSIC)){
            Intent serviceIntent = new Intent(context,MusicService.class);
            serviceIntent.putExtra("ACTION","STOP");
            context.getApplicationContext().startService(serviceIntent);
            Toast.makeText(context,"STOP_MUSIC",Toast.LENGTH_SHORT).show();
        }else if (intent.getAction().equals(LIST_MUSIC)){
            Intent i = new Intent(context,ListActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(i);
            Toast.makeText(context,"LIST_MUSIC",Toast.LENGTH_SHORT).show();
        }




    }

}
