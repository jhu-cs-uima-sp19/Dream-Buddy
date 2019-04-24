package com.example.dreambuddy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.MyViewHolder> {
    private ArrayList<AudioFile> mDataset;
    private Context context;
    private MediaPlayer mp;
    private boolean playing;
    private int trackPlaying;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView audio;
        public ImageButton play;

        public MyViewHolder(View v) {
            super(v);
            audio = v.findViewById(R.id.audio);
            play = v.findViewById(R.id.play);
        }
    }

    public AudioAdapter(ArrayList<AudioFile> myDataset, Context context) {
        this.mDataset = myDataset;
        this.context = context;
        this.playing = false;
        this.trackPlaying = 0;
    }

    @Override
    public AudioAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.waves_item, parent, false);

        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final AudioFile curEntry = mDataset.get(position);
        holder.audio.setText(curEntry.getTitle());
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = curEntry.getTitle();
                if (!playing) {
                    if (temp.equals("Track1")) {
                        mp = MediaPlayer.create(context, R.raw.track1);
                        mp.start();
                        holder.play.setImageResource(R.drawable.ic_pause_black_24dp);
                        playing = true;
                        trackPlaying = 1;
                    } else if (temp.equals("Track2")) {
                        mp = MediaPlayer.create(context, R.raw.track2);
                        mp.start();
                        holder.play.setImageResource(R.drawable.ic_pause_black_24dp);
                        playing = true;
                        trackPlaying = 2;
                    } else if (temp.equals("Track3")) {
                        mp = MediaPlayer.create(context, R.raw.track3);
                        mp.start();
                        holder.play.setImageResource(R.drawable.ic_pause_black_24dp);
                        playing = true;
                        trackPlaying = 3;
                    } else if (temp.equals("Track4")) {
                        mp = MediaPlayer.create(context, R.raw.track4);
                        mp.start();
                        holder.play.setImageResource(R.drawable.ic_pause_black_24dp);
                        playing = true;
                        trackPlaying = 4;
                    }
                } else if (playing) {
                    if (temp.equals("Track1") && trackPlaying == 1) {
                        mp.release();
                        mp = null;
                        holder.play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        playing = false;
                        trackPlaying = 0;
                    } else if (temp.equals("Track2") && trackPlaying == 2) {
                        mp.release();
                        mp = null;
                        holder.play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        playing = false;
                        trackPlaying = 0;
                    } else if (temp.equals("Track3") && trackPlaying == 3) {
                        mp.release();
                        mp = null;
                        holder.play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        playing = false;
                        trackPlaying = 0;
                    } else if (temp.equals("Track4") && trackPlaying == 4) {
                        mp.release();
                        mp = null;
                        holder.play.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                        playing = false;
                        trackPlaying = 0;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
