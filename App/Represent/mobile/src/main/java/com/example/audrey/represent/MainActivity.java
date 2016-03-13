package com.example.audrey.represent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String TAG = "MainActivity";

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "OKlEgF9D9ZHhUdUHSh9tJ3hSv";
    private static final String TWITTER_SECRET = "vBwSnYZ2IvCAaHnaebrLqqESOAVNy4GpdZczBZVuOlxIaBa4dm";

    private GoogleApiClient mGoogleApiClient;

    private Button searchButton;
    private EditText zipCodeView;

    public static final String REP_BASE_URL = "congress.api.sunlightfoundation.com";

    public static String buildURL(String zipCode) {
        Uri.Builder builder = new Uri.Builder()
                .scheme("https")
                .authority(REP_BASE_URL)
                .appendPath("legislators")
                .appendPath("locate")
                .appendQueryParameter("zip", zipCode)
                .appendQueryParameter("apikey", "4fd48957591a47c2a4d6e91f617b903d");
        String jsonURL = builder.build().toString();
        return jsonURL;
    }


//    public void currentSearch(View view) {
//        Intent sendIntent = new Intent(MainActivity.this, CongressViewActivity.class); //intent is going to go from MainActivity to CongressViewActivity
//        String zipCode = "94704";
//        sendIntent.putExtra("ZIP_CODE", zipCode);
//        startActivity(sendIntent); //start activity
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchButton = (Button) findViewById(R.id.searchButton);
        zipCodeView = (EditText) findViewById(R.id.zipEditText);

        searchButton.setOnClickListener(new View.OnClickListener() { //set mobile Listener
            @Override
            public void onClick(View v) {
                // get current location zip code
                String zipCode = getZipFromMyLocation();

                // query sunlight API with zip code for legislator first name, last name, twitter id, bioguide_id, contact_form, website in JSON Object
                sendRequest(zipCode);

                // pass JSON object to second screen and watch
                // parse JSON in second screen and watch
                // display JSON array strings on second screen
                // third screen: use bioguide_id to search for committees and bills by legislator
            }
        });

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addApi(Wearable.API)  // used for data layer API
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Log.e(TAG, "GOOOOOOOOGLE API" + mGoogleApiClient.toString());
    }

    public void startActivity(String repData) {

        Intent serviceIntent = new Intent(getBaseContext(), PhoneToWatchService.class); //Listen to the PhoneToWatchService
        //String zipCode = zipCodeView.getText().toString();
        serviceIntent.putExtra("REP_DATA", repData); //hash table with key and value
        startService(serviceIntent);

        Intent sendIntent = new Intent(MainActivity.this, CongressViewActivity.class); //intent is going to go from MainActivity to CongressViewActivity
//        Bundle b = new Bundle();
//        b.putString("REP_DATA", repData);
//        sendIntent.putExtras(b);
        sendIntent.putExtra("REP_DATA", repData);
        startActivity(sendIntent); //start activity
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    // sendRequest() will execute after pressing the button Search by current location.
    private void sendRequest(String zipCode) {
        String url = buildURL(zipCode);
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    startActivity(response);
                    Log.i(TAG, response);
                    //showJSON(response);
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, error.toString());
                    // TODO Auto-generated method stub

                }
            });
        // Access the RequestQueue through your singleton class.
        //MySingleton.getInstance(this).addToRequestQueue(jsObjRequest);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
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

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d(TAG, "onConnected postal code: " + getZipFromMyLocation());
    }

    public String getZipFromMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "permissions not granted");
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        } else {
            Log.d(TAG, "permissions granted");
        }

            List<Address> addresses = null;

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            Location location = null;

            while (location == null) {
                location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                Log.d(TAG, "Got location coordinates: " + location);
            }

            try {
                addresses = geocoder.getFromLocation(
                        location.getLatitude(),
                        location.getLongitude(),
                        // In this sample, get just a single address.
                        1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addresses.get(0);
            return address.getPostalCode();
    }
    // The getLastLocation() method returns a Location object from which you can
    // retrieve the latitude and longitude coordinates of a geographic location.
    // The location object returned may be null in rare cases when the location is not available.



    @Override
    public void onConnectionSuspended(int i) {
        Log.e(TAG, "connectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(TAG, "connectionFailed" + connectionResult.getErrorMessage());
    }
}
