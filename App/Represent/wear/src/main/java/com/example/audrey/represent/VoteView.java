package com.example.audrey.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Audrey on 3/4/16.
 */
public class VoteView extends Activity {

    private Button toWatchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_view);

        //toWatchBtn = (Button) findViewById(R.id.toWatchBtn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String zipCode = extras.getString("ZIP_CODE");
            //toWatchBtn.setText("Find " + zipCode);
        }
    }
}
