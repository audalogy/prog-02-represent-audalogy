package com.example.audrey.represent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private CongressViewAdapter mAdapter;
    private EditText zipCodeView;


//    public void currentSearch(View view) {
//        Intent sendIntent = new Intent(MainActivity.this, CongressViewActivity.class); //intent is going to go from MainActivity to CongressViewActivity
//        String zipCode = "94704";
//        sendIntent.putExtra("ZIP_CODE", zipCode);
//        startActivity(sendIntent); //start activity
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchButton = (Button) findViewById(R.id.searchButton);
        zipCodeView = (EditText) findViewById(R.id.zipEditText);

        searchButton.setOnClickListener(new View.OnClickListener() { //set mobile Listener
            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(getBaseContext(), PhoneToWatchService.class); //Listen to the PhoneToWatchService
                String zipCode = zipCodeView.getText().toString();
                serviceIntent.putExtra("ZIP_CODE", zipCode); //hash table with key and value
                startService(serviceIntent);

                Intent sendIntent = new Intent(MainActivity.this, CongressViewActivity.class); //intent is going to go from MainActivity to CongressViewActivity
                sendIntent.putExtra("ZIP_CODE", zipCode);
                startActivity(sendIntent); //start activity
            }
        });

        mAdapter = new CongressViewAdapter(this);

        // set the listener to the adapter
        mAdapter.setOnItemClickListener(onItemClickListener);

    }

    // create an instance of OnItemClickListener below onCreate():
    CongressViewAdapter.OnItemClickListener onItemClickListener = new CongressViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
