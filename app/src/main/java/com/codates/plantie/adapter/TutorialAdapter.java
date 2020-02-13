package com.codates.plantie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;

import java.util.ArrayList;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.ViewHolder> {
    ArrayList<String> tutorial;

    public TutorialAdapter(ArrayList<String> tutorial) {
        this.tutorial = tutorial;
    }

    @NonNull
    @Override
    public TutorialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tutorial, parent, false);
        return new TutorialAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorialAdapter.ViewHolder holder, int position) {
        holder.tvTutorial.setText(tutorial.get(position));
    }

    @Override
    public int getItemCount() {
        return tutorial.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTutorial;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTutorial = itemView.findViewById(R.id.text_detail_alat);
        }
    }
}
