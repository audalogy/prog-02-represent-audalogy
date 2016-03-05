package com.example.audrey.represent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Audrey on 3/2/16.
 */

// 1
public class CongressViewAdapter extends RecyclerView.Adapter<CongressViewAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Rep> mReps;
    OnItemClickListener mItemClickListener;

    public CongressViewAdapter(Context context) {
        this.mContext = context;
        mReps = new RepData().repList();
    }

    // 1 returns the number of items from your data array. In this case, you’re using the size of the PlaceData.placeList().
    @Override
    public int getItemCount() { return mReps.size(); }

    // 2 returns a new instance of your ViewHolder by passing an inflated view of row_reps.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rep, parent, false);
        return new ViewHolder(view);
        }

    // 3 binds the Place object to the UI elements in ViewHolder. You’ll use Picasso to cache the images for the list.
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Rep rep = mReps.get(position);

        holder.repName.setText(rep.name);
        holder.repEmail.setText(rep.email);
        holder.repWebsite.setText(rep.website);
        holder.repTweet.setText(rep.tweet);

        Picasso.with(mContext).load(rep.getImageResourceId(mContext)).into(holder.repImage);
    }

    // 4
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public LinearLayout mainHolder;
        public LinearLayout repNameHolder;
        public TextView repName;
        public TextView repEmail;
        public TextView repWebsite;
        public TextView repTweet;
        public ImageView repImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mainHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            repName = (TextView) itemView.findViewById(R.id.repName);
            repEmail = (TextView) itemView.findViewById(R.id.repEmail);
            repWebsite = (TextView) itemView.findViewById(R.id.repWebsite);
            repTweet = (TextView) itemView.findViewById(R.id.repTweet);
            repNameHolder = (LinearLayout) itemView.findViewById(R.id.repNameHolder);
            repImage = (ImageView) itemView.findViewById(R.id.repImage);
            // hook up the two by adding the following to the bottom of ViewHolder():
            // initiate setOnClickListener for repNameHolder and implement the onClick override method.
            mainHolder.setOnClickListener(this);
        }

        // implement View.OnClickListener by adding the following method stub to the inner ViewHolder class:
        @Override
        public void onClick(View v) {
            final String TAG = "CongressViewAdapter";
            Log.d(TAG, "ClickListener On");
            if (mItemClickListener != null) {
                Log.d(TAG, "ItemClickListener On");
                mItemClickListener.onItemClick(itemView, getPosition());
            }
        }
    }

    //declaring the onClick interface for the RecyclerView:
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // add the setter method of the onClickListener:
    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

}