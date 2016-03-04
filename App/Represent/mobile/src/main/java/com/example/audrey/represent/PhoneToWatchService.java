package com.example.audrey.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by Audrey on 3/3/16.
 */
public class PhoneToWatchService extends Service { //step 1: declare listener

    private GoogleApiClient mApiClient;

    @Override
    public void onCreate() { //start the API Client - can copy this syntax
        super.onCreate();
        //step 2: initialize the googleAPIClient for message passing
        mApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint) {
                    }

                    @Override
                    public void onConnectionSuspended(int cause) {
                    }
                })
                .build();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //onStartCommand runs when the service starts. Has access to intent. OnCreate cannot get intent
        // Which zip code do we want to search? Grab this info from sendIntent in Main Activity
        // which was passed over when we called startService
        Bundle extras = intent.getExtras();
        final String zipCode = extras.getString("ZIP_CODE"); //what is value for key ZIP_CODE

        // Send the message with the zip code
        new Thread(new Runnable() {  //starting new thread with method run. It sends the message according to the API
            @Override
            public void run() {
                //first, connect to the api client
                mApiClient.connect();
                //now that you're connected, send a massage with the zip code. There is a / because ??
                sendMessage("/" + zipCode, zipCode);
            }
        }).start();

        return START_STICKY;
    }

    @Override //remember, all services need to implement an IBinder
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void sendMessage( final String path, final String text ) {  //finds all nodes and sends the command. Use Google API client to send message. Can copy this code for Prog02B
        //one way to send message: start a new thread and call .await() - see watchtophoneservice for another way to send a message from watch to phone
        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            //4 arguments: api client, the node ID, the path (for the listener to parse), and the message itself (you need to convert it to bytes.)
                            mApiClient, node.getId(), path, text.getBytes() ).await();

                }
            }
        }).start();
    }

}
