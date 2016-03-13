package com.example.audrey.represent;

import android.content.Context;

/**
 * Created by Audrey on 3/4/16.
 */
public class Rep {

    public String name;
    //TWITTER: public String imageName;
    public String first_name;
    public String last_name;
    public String party;

//    public boolean isFav;

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }
}

