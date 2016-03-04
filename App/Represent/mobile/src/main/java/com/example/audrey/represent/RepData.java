package com.example.audrey.represent;

import java.util.ArrayList;

/**
 * Created by Audrey on 3/2/16.
 */
public class RepData {

    public static String[] repNameArray = {"Bora Bora", "Canada", "Dubai", "Hong Kong", "Iceland", "India", "Kenya", "London", "Switzerland", "Sydney"};

    public static ArrayList<Rep> repList() {
        ArrayList<Rep> list = new ArrayList<>();
        for (int i = 0; i < repNameArray.length; i++) {
            Rep rep = new Rep();
            rep.name = repNameArray[i];
            rep.imageName = repNameArray[i].replaceAll("\\s+", "").toLowerCase();
            if (i == 2 || i == 5) {
                rep.isFav = true;
            }
            list.add(rep);
        }
        return (list);
    }
}
