package com.example.audrey.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Audrey on 3/4/16.
 */
public class VoteViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_view);

        Log.d("TAG","vote view activity started");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

    }
}
