package com.iamdeveloper.musicplay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by IamDeveloper on 9/18/2016.
 */
public class ListActivity  extends AppCompatActivity{

    public static String GET_PATH = "com.iamdeveloper.PATH";
    private static final int PICK_SOMETHING = 1 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("audio/*");
        startActivityForResult(i,PICK_SOMETHING);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == PICK_SOMETHING){


                String realPath = GetRealPath.getRealPathFromAPI19(this,data.getData());

                if(realPath == null){
                    realPath = GetRealPath.getRealPathFromAPI11_to18(this,data.getData());
                }

                if (realPath==null){
                    realPath = GetRealPath.getRealPathFromSD_CARD(data.getData());
                }



                Intent intent = new Intent();
                intent.setAction(GET_PATH);
                intent.putExtra("DATA",realPath);
                sendBroadcast(intent);
                finish();
            }
        }
    }
}
