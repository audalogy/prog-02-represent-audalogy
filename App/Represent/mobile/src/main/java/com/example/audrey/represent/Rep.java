package com.example.audrey.represent;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Audrey on 3/2/16.
 */
public class Rep {

    //CongressViewActivity
    public String name;
    public String first_name;
    public String last_name;
    public String party;
    public String email; //oc_email from API
    public String website;

    //TWITTER
    public String twitterID; //twitter_ID from API; screen_name in Twitter
    public String imageName; //use twitter API
    public String tweet; //use twitter API

    //RepDetailActivity
    public String id; //bioguide_id from API
    public String committees;
    public String bills;
    public String endTerm; //term_end from API

    public static ArrayList<Rep> parseSunlightJson(String mSunlightReps) {
        ArrayList<Rep> reps = new ArrayList<>();

        JSONObject repsObject = null; //declaring variable and initializing to null
        try {
            if (mSunlightReps == null) {
                Log.e("Rep", "WTF");
            }
            repsObject = new JSONObject(mSunlightReps); //instantiating to JSONObject of sunlightReps. Try/catch because JSON methods throw exceptions
            JSONArray repsResults = repsObject.getJSONArray("results"); //JSONarray of just the array of results
            for (int i=0; i < repsResults.length(); i++) {
                try {
                    JSONObject oneObject = repsResults.getJSONObject(i); //get JSONObject from JSONArray for each representative
                    // Pulling items from the array
                    Rep oneRep = new Rep(); //create new rep object
                    oneRep.first_name = oneObject.getString("first_name");
                    oneRep.last_name = oneObject.getString("last_name");
                    oneRep.party = oneObject.getString("party");
                    oneRep.email = oneObject.getString("oc_email");
                    oneRep.website = oneObject.getString("website");
                    oneRep.twitterID = oneObject.getString("twitter_id");
                    oneRep.id = oneObject.getString("bioguide_id");
                    oneRep.endTerm = oneObject.getString("term_end");
                    oneRep.imageName = oneObject.getString("last_name").replaceAll("\\s+", "").toLowerCase();
                    oneRep.tweet = "@NancyPelosi: We have a responsibility to ensure children & residents of #FlintWaterCrisis ... ";
                    reps.add(oneRep);
                } catch (JSONException e) {
                    // Oops
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            // oops
            e.printStackTrace();
        }

        Log.d("Rep.java", reps.toString());
        return reps;

    }

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }

}
