package com.example.audrey.represent;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Audrey on 3/2/16.
 */

// 1
public class CongressViewAdapter extends RecyclerView.Adapter<CongressViewAdapter.ViewHolder> {

    Context mContext;

    // 2
    public CongressViewAdapter(Context context) {
        this.mContext = context;
    }

    // 3
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout mainHolder;
        public LinearLayout repNameHolder;
        public TextView repName;
        public ImageView repImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mainHolder = (LinearLayout) itemView.findViewById(R.id.mainHolder);
            repName = (TextView) itemView.findViewById(R.id.repName);
            repNameHolder = (LinearLayout) itemView.findViewById(R.id.repNameHolder);
            repImage = (ImageView) itemView.findViewById(R.id.repImage);
        }
    }

    // 1 returns the number of items from your data array. In this case, you’re using the size of the PlaceData.placeList().
    @Override
    public int getItemCount() {
        return new RepData().repList().size();
    }

    // 2 returns a new instance of your ViewHolder by passing an inflated view of row_reps.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_rep, parent, false);
        return new ViewHolder(view);
    }

    // 3 binds the Place object to the UI elements in ViewHolder. You’ll use Picasso to cache the images for the list.
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Rep rep = new RepData().repList().get(position);
        holder.repName.setText(rep.name);
        Picasso.with(mContext).load(rep.getImageResourceId(mContext)).into(holder.repImage);
    }
}