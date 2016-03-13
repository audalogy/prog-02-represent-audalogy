package com.example.audrey.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.twitter.sdk.android.core.AppSession;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetView;

import java.util.ArrayList;
import java.util.List;

//source: http://www.raywenderlich.com/103367/material-design

public class CongressViewActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private CongressViewAdapter mAdapter;

    ArrayList<Rep> mReps;

    public static final String TAG = "CongressViewActivity";

    //NOTE: make request here to be more efficient
//    public static final String REP_BASE_URL = "congress.api.sunlightfoundation.com";
//
//    public static String buildURL(String zipCode) {
//        Uri.Builder builder = new Uri.Builder()
//                .scheme("https")
//                .authority(REP_BASE_URL)
//                .appendPath("legislators")
//                .appendPath("locate")
//                .appendQueryParameter("zip", zipCode)
//                .appendQueryParameter("apikey", "4fd48957591a47c2a4d6e91f617b903d");
//        String jsonURL = builder.build().toString();
//        return jsonURL;
//    }
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congress_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.d(TAG, "onCreate CongressViewActivity");

        //Int zipCode = MainActivity.zipCode; //get zip code from MainActivity
        Intent myIntent = getIntent();
        String repData = myIntent.getStringExtra("REP_DATA");
//        Bundle b = getIntent().getExtras();
//        String repData = b.getString("REP_DATA");

        Log.d(TAG, "CongressView data rec'd: " + repData);

        // initialize RecyclerView and apply StaggeredGridLayout to it to create two types of vertically staggered grids
        // start with the first type, passing 1 for the span count and StaggeredGridLayoutManager.VERTICAL for the orientation
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mStaggeredLayoutManager);

        //parse array list of mReps and then query Twitter API
        mReps = Rep.parseSunlightJson(repData);
        twitterQuery();

        // create an instance of the adapter and pass it to the RecyclerView
        mAdapter = new CongressViewAdapter(this, mReps);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(onItemClickListener);
    }


    //https://docs.fabric.io/android/twitter/show-tweets.html
    public void twitterQuery() {
        //http://stackoverflow.com/questions/28541459/getting-403-forbidden-when-using-twitter-fabric-to-get-user-timeline
        TwitterCore.getInstance().logInGuest( new Callback<AppSession>() {

            @Override
            public void success(Result<AppSession> appSessionResult) {
                AppSession session = appSessionResult.data;

                TwitterApiClient twitterApiClient = TwitterCore.getInstance().getApiClient();
                // Can also use Twitter directly: Twitter.getApiClient()
                StatusesService myStatusesService = twitterApiClient.getStatusesService();

                for (int i = 0; i < mReps.size(); i++) {
                    Rep rep = mReps.get(i);
                    String screenName = rep.twitterID;

                    //final LinearLayout tweetLayout = (LinearLayout) findViewById(R.id.myTweetLayout);

                    //https://docs.fabric.io/javadocs/twitter-core/1.6.5/index.html
                    myStatusesService.userTimeline((Long) null, screenName, Integer.valueOf(1), null, null, null, null, null, null, new Callback<List<Tweet>>() {
                        @Override
                        public void success(Result<List<Tweet>> result) { //generic programming (aka templates) for Result< >
                            List<Tweet> tweets = result.data;
                            Tweet tweet = null;
                            if (tweets.size() > 0) {
                                tweet = tweets.get(0);
                            }
                            Log.d(TAG, "TWEEEEEEEET: " + tweet);
                            TweetView tweetView = new TweetView(CongressViewActivity.this, tweet);
                            //tweetLayout.addView(tweetView);
                        }

                        @Override
                        public void failure(TwitterException e) {
                            Log.d("TwitterKit", "Load Tweet failure", e);
                        }
                    });
                }
            }
            @Override
            public void failure(TwitterException e) {
                Log.d("TwitterKit", "Guest Session exception", e);
                e.printStackTrace();
            }
        });  // loginGuest


            // final ViewGroup parentView = (ViewGroup) getWindow().getDecorView().getRootView();
            // Base this Tweet ID on some data from elsewhere in your app

//            TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
//                @Override
//                public void success(Result<Tweet> result) {
//                    TweetView tweetView = new TweetView(CongressViewActivity.this, result.data);
//                    tweetLayout.addView(tweetView);
//                }
//
//                @Override
//                public void failure(TwitterException exception) {
//                    Log.d("TwitterKit", "Load Tweet failure", exception);
//                }
//            });
    }

    CongressViewAdapter.OnItemClickListener onItemClickListener = new CongressViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Intent nextIntent = new Intent(CongressViewActivity.this, RepDetailActivity.class);
            nextIntent.putExtra(RepDetailActivity.REP_NAME, position);
//            ImageView repImage = (ImageView) v.findViewById(R.id.repImage);
//            LinearLayout repNameHolder = (LinearLayout) v.findViewById(R.id.repNameHolder);
            startActivity(nextIntent);
        }
    };
}
