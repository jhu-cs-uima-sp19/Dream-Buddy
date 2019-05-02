package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WebAdapter extends RecyclerView.Adapter<WebAdapter.MyViewHolder> {
    private ArrayList<WebSite> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView Tech_ac;
        public TextView Tech_full;

        public MyViewHolder(View v) {
            super(v);
            Tech_ac = v.findViewById(R.id.Tech_ac);
            Tech_full = v.findViewById(R.id.Tech_full);
        }
    }

    public WebAdapter(ArrayList<WebSite> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
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
        holder.Tech_ac.setText(curEntry.getAcronym());
        holder.Tech_full.setText(curEntry.getFull());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewLearn.class);
                intent.putExtra("url", curEntry.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
