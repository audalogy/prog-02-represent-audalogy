package com.example.audrey.represent;

import java.util.ArrayList;

/**
 * Created by Audrey on 3/4/16.
 */
public class RepData {

    public static String[] repNameArray = {"Boxer", "Feinstein", "Pelosi"};

    public static ArrayList<Rep> repList() {
        ArrayList<Rep> list = new ArrayList<>();
        for (int i = 0; i < repNameArray.length; i++) {
            Rep rep = new Rep();
            rep.name = repNameArray[i];
            rep.imageName = repNameArray[i].replaceAll("\\s+", "").toLowerCase();
//            if (i == 2 || i == 5) {
//                rep.isFav = true;
//            }
            list.add(rep);
        }
        return (list);
    }
}
