package com.example.audrey.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.CardFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Audrey on 3/12/16.
 */
public class WatchFragment extends CardFragment {

    @Override
//    public void onActivityCreated (Bundle savedInstanceState) {
    public View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = super.onCreateContentView(inflater, container, savedInstanceState);
    Log.d("Watch Fragment", "I'm setting up your base.");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(WatchFragment.this.getActivity(), WatchToPhoneService.class); //Listen to the PhoneToWatchService
                serviceIntent.putExtra("REP_DATA", "dataaa"); //hash table with key and value
                WatchFragment.this.getActivity().startService(serviceIntent);
                Log.d("Watch Fragment", "I'm in your base.");
            }
        });
        return view;
    };

    // http://developer.android.com/reference/android/app/Fragment.html
    public static WatchFragment newInstance(String title, String text) {
        WatchFragment f = new WatchFragment();

        Bundle args = new Bundle();
        args.putString(CardFragment.KEY_TITLE, title);
        args.putString(CardFragment.KEY_TEXT, text);
        f.setArguments(args);

        return f;
    }
}
