package com.example.dreambuddy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.MyViewHolder> {
    private ArrayList<WebSite> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView Tech_ac;
        public TextView Tech_full;

        public MyViewHolder(View v) {
            super(v);
            Tech_ac = v.findViewById(R.id.postTitle);
            Tech_full = v.findViewById(R.id.likesCount);
        }
    }

    public WebAdapter(ArrayList<WebSite> myDataset) {
        this.mDataset = myDataset;
    }

    @Override
    public WebAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.learn_item, parent, false);

        WebAdapter.MyViewHolder vh = new WebAdapter.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(WebAdapter.MyViewHolder holder, int position) {
        final WebSite curEntry = mDataset.get(position);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
