package com.example.audrey.represent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

import java.nio.charset.StandardCharsets;

/**
 * Created by Audrey on 3/3/16.
 */

public class WatchListenerService extends WearableListenerService {
    // In PhoneToWatchService, we passed in a path
    // These paths serve to differentiate different phone-to-watch messages

    private static final String REP_FEED = "/repData";
//    private static final String TOAST = "/send_toast";


    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        Log.d("T", "in WatchListenerService, got: " + messageEvent.getPath());
        //use the 'path' field in send message to differentiate use cases

        if( messageEvent.getPath().equalsIgnoreCase( REP_FEED ) ) {
            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);

            //TODO: display rep image, name, and party

            //Intent intent = new Intent(this, MainActivity.class );
            //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //you need to add this flag since you're starting a new activity from a service
            //intent.putExtra("REP_DATA", "rep string response");
            Log.d("T", "watch received data: " + value);
            //startActivity(intent); starts the MainActivity class

            // Make a toast with the String
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, value, duration);
            toast.show();

//        } else if (messageEvent.getPath().equalsIgnoreCase( LEXY_FEED )) {
//            String value = new String(messageEvent.getData(), StandardCharsets.UTF_8);
//            Intent intent = new Intent(this, MainActivity.class );
//            intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
//            //you need to add this flag since you're starting a new activity from a service
//            intent.putExtra("CAT_NAME", "Lexy");
//            Log.d("T", "about to start watch MainActivity with CAT_NAME: Lexy");
//            startActivity(intent);
        } else {
            super.onMessageReceived( messageEvent );
        }

    }
}
