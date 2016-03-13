package com.example.audrey.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;
    private Button toPhoneBtn;
    private Button toWatchCountyBtn;
    private Button toPhoneDetailBtn;

    public static final String TAG = "Watch MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toPhoneDetailBtn = (Button) findViewById(R.id.toPhoneBtn);
        toWatchCountyBtn = (Button) findViewById(R.id.toWatchCountyBtn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String repData = extras.getString("REP_DATA"); //getting REP_DATA string
            toWatchCountyBtn.setText("2012 County Vote"); //on click of button, put blob of text on button
            Log.d(TAG, repData);
        }

        toWatchCountyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(getBaseContext(), VoteViewActivity.class);
                startActivity(sendIntent);
                Log.d(TAG,"clicked toWatchCountyBtn");
                }
        });

//        toPhoneBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent sendIntent = new Intent(getBaseContext(), WatchToPhoneService.class);
//                startService(sendIntent);
//            }
//        });

        //connect pager to layouts adapter
        final GridViewPager pager = (GridViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyGridPagerAdapter(this, getFragmentManager()));

        }
    }
