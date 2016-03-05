package com.example.audrey.represent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.GridViewPager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView mTextView;
    private Button toPhoneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toPhoneBtn = (Button) findViewById(R.id.toPhoneBtn);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null) {
            String zipCode = extras.getString("ZIP_CODE");
//            toPhoneBtn.setText("Find " + zipCode);
        }

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


//        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
//        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
//            @Override
//            public void onLayoutInflated(WatchViewStub stub) {
//                mTextView = (TextView) stub.findViewById(R.id.text);
//            }
//        });
    }
}
