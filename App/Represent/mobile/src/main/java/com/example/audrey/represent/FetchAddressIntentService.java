package com.example.audrey.represent;

/**
 * Created by Audrey on 3/10/16.
 */
public class FetchAddressIntentService { //extends Service

//    public static final String TAG = "FetchAddressIntentService";
//
//    @Override
//    protected void onHandleIntent(Intent intent) {
//        String errorMessage = "";
//
//        // Get the location passed to this service through an extra.
//        Location location = intent.getParcelableExtra(
//                Constants.LOCATION_DATA_EXTRA);
//
//        List<Address> addresses = null;
//
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//        try {
//            addresses = geocoder.getFromLocation(
//                    location.getLatitude(),
//                    location.getLongitude(),
//                    // In this sample, get just a single address.
//                    1);
//        } catch (IOException ioException) {
//            // Catch network or other I/O problems.
//            errorMessage = getString(R.string.service_not_available);
//            Log.e(TAG, errorMessage, ioException);
//        } catch (IllegalArgumentException illegalArgumentException) {
//            // Catch invalid latitude or longitude values.
//            errorMessage = getString(R.string.invalid_lat_long_used);
//            Log.e(TAG, errorMessage + ". " +
//                    "Latitude = " + location.getLatitude() +
//                    ", Longitude = " +
//                    location.getLongitude(), illegalArgumentException);
//        }
//
//        // Handle case where no address was found.
//        if (addresses == null || addresses.size()  == 0) {
//            if (errorMessage.isEmpty()) {
//                errorMessage = getString(R.string.no_address_found);
//                Log.e(TAG, errorMessage);
//            }
//            deliverResultToReceiver(Constants.FAILURE_RESULT, errorMessage);
//        } else {
//            Address address = addresses.get(0);
//            ArrayList<String> addressFragments = new ArrayList<String>();
//
//            // Fetch the address lines using getAddressLine,
//            // join them, and send them to the thread.
//            for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
//                addressFragments.add(address.getAddressLine(i));
//            }
//            Log.i(TAG, getString(R.string.address_found));
//            deliverResultToReceiver(Constants.SUCCESS_RESULT,
//                    TextUtils.join(System.getProperty("line.separator"),
//                            addressFragments));
//        }
//
//    }
}
