package com.example.audrey.represent;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audrey on 3/3/16.
 */
public class WatchToPhoneService extends Service implements GoogleApiClient.ConnectionCallbacks {

    private GoogleApiClient mWatchApiClient;
    private List<Node> nodes = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        //initialize the googleAPIClient for message passing
        mWatchApiClient = new GoogleApiClient.Builder( this )
                .addApi( Wearable.API )
                .addConnectionCallbacks(this)
                .build();
        //and actually connect it
        mWatchApiClient.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWatchApiClient.disconnect();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) { //onStartCommand runs when the service starts. Has access to intent. OnCreate cannot get intent
        // Which zip code do we want to search? Grab this info from sendIntent in Main Activity
        // which was passed over when we called startService
        if (intent != null) {
            Bundle extras = intent.getExtras();
            final String repData = extras.getString("REP_DATA"); //what is value for key REP_NAME

            // Send the message with the zip code
            new Thread(new Runnable() {  //starting new thread with method run. It sends the message according to the API
                @Override
                public void run() {
                    //first, connect to the api client
                    mWatchApiClient.connect();
                    //now that you're connected, send a massage with the zip code. There is a / because it's like accessing a URI
                    sendMessage("/repData", repData);
                }
            }).start();
        }
        return START_REDELIVER_INTENT;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override //alternate method to connecting: no longer create this in a new thread, but as a callback
    public void onConnected(Bundle bundle) {
        Log.d("T", "in onConnected");
        Wearable.NodeApi.getConnectedNodes(mWatchApiClient)
                .setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        nodes = getConnectedNodesResult.getNodes();
                        Log.d("T", "found nodes");
                        //when we find a connected node, we populate the list declared above
                        //finally, we can send a message
                        sendMessage("/repData", new String("REP_DATA"));
                        Log.d("T", "sent");
                    }
                });
    }

    @Override //we need this to implement GoogleApiClient.ConnectionsCallback
    public void onConnectionSuspended(int i) {}

    private void sendMessage(final String path, final String text ) {

        new Thread( new Runnable() {
            @Override
            public void run() {
                NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes( mWatchApiClient ).await();
                for(Node node : nodes.getNodes()) {
                    //we find 'nodes', which are nearby bluetooth devices (aka emulators)
                    //send a message for each of these nodes (just one, for an emulator)
                    MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(
                            //4 arguments: api client, the node ID, the path (for the listener to parse), and the message itself (you need to convert it to bytes.)
                            mWatchApiClient, node.getId(), path, text.getBytes() ).await();

                }
            }
        }).start();
//        for (Node node : nodes) {
//            Wearable.MessageApi.sendMessage(
//                    mWatchApiClient, node.getId(), path, text.getBytes());
//        }
    }
}
