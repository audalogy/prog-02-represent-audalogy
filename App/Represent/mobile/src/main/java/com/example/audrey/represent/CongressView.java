package com.example.audrey.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

//source: http://www.raywenderlich.com/103367/material-design

public class CongressView extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private CongressViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congress_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       //Int zipCode = MainActivity.zipCode; //get zip code from MainActivity
        Intent myIntent = getIntent();
        String value = myIntent.getStringExtra("ZIP_CODE");

        // initialize RecyclerView and apply StaggeredGridLayout to it, which you’ll use to create two types of vertically staggered grids
        // start with the first type, passing 1 for the span count and StaggeredGridLayoutManager.VERTICAL for the orientation
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        // create an instance of the adapter and pass it to the RecyclerView
        mAdapter = new CongressViewAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
