package com.codates.plantie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.model.Hari;
import com.codates.plantie.model.Minggu;

import java.util.ArrayList;

public class MingguAdapter extends RecyclerView.Adapter<MingguAdapter.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;

    public MingguAdapter(ArrayList<Minggu> minggu) {
        this.minggu = minggu;
    }

    private ArrayList<Minggu> minggu;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClicked(int posisition);
    }

    @NonNull
    @Override
    public MingguAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tanaman_minggu,parent,false);
        return new MingguAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MingguAdapter.ViewHolder holder, final int position) {
        int nomor = position + 1 ;
        holder.tvMinggu.setText("Minggu "+ nomor );
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return minggu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMinggu ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMinggu = itemView.findViewById(R.id.tv_minggu);
        }
    }
}
