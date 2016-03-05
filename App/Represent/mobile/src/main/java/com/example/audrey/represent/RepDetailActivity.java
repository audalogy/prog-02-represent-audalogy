package com.example.audrey.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Audrey on 3/4/16.
 */
public class RepDetailActivity extends AppCompatActivity {

    public static final String REP_NAME = "rep_name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rep_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //String repName = CongressViewActivity.repName; //get rep name from CongressViewActivity
        Intent myIntent = getIntent();
        //String value = myIntent.getStringExtra("REP_NAME");
    }
}
