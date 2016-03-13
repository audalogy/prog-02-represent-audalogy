package com.example.audrey.represent;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audrey on 3/4/16.
 */

public class MyGridPagerAdapter extends FragmentGridPagerAdapter {

    private final Context mContext;
    private List mRows;
    ArrayList<Rep> mReps;

    public MyGridPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
        mReps = new RepData().repList();
    }

    // A simple container for static data in each page
    private static class Page {
        // static resources
        int titleRes;
        int textRes;
        int iconRes;
    }

    // Create a static set of pages in a 2D array
    private final Page[][] PAGES = {  };

    // Override methods in FragmentGridPagerAdapter >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    // Obtain the number of pages (vertical)
    @Override
    public int getRowCount() {
        return mReps.size();
    }

    // Obtain the number of pages (horizontal)
    @Override
    public int getColumnCount(int rowNum) {
        return 1;
    }

    // Obtain the UI fragment at the specified position
    @Override
    public Fragment getFragment(int row, int col) {
        Log.d("GridAdapter", "row " + row + " col " + col);

        Rep rep = mReps.get(row);
        final String title = rep.name;
        String text = "Democrat";
        final WatchFragment fragment = WatchFragment.newInstance(title, text); // WatchFragment.create(title, text, 0 /* TODO: need an icon resource id */);
        return fragment;
    }

    // Obtain the background image for the row
//    @Override
//    public Drawable getBackgroundForRow(int row) {
//        return mContext.getResources().getDrawable(
//                (BG_IMAGES[row % BG_IMAGES.length]), null);
//    }

    // Obtain the background image for the specific page
    @Override
    public Drawable getBackgroundForPage(int row, int column) {
        Rep rep = mReps.get(row);
        return mContext.getResources().getDrawable(rep.getImageResourceId(mContext), null);

//        if( row == 2 && column == 1) {
//            // Place image at specified position
//            return mContext.getResources().getDrawable(R.drawable.bugdroid_large, null);
//        } else {
//            // Default to background image for row
//            return GridPagerAdapter.BACKGROUND_NONE;
//        }
    }


}

