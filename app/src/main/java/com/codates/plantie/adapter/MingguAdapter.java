package com.codates.plantie.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.model.Minggu;

import java.util.ArrayList;

public class MingguAdapter extends RecyclerView.Adapter<MingguAdapter.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;
    private ArrayList<Minggu> minggu;

    public MingguAdapter(ArrayList<Minggu> minggu) {
        this.minggu = minggu;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public MingguAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tanaman_minggu, parent, false);
        return new MingguAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MingguAdapter.ViewHolder holder, final int position) {
        int nomor = position + 1;
        if (minggu.get(position).isSelesai()) {
            holder.tvMinggu.setTextColor(Color.WHITE);
            ((CardView) holder.itemView).setCardBackgroundColor(Color.parseColor("#5fc388"));
        }
        holder.tvMinggu.setText("Minggu " + nomor);
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

    public interface OnItemClickCallback {
        void onItemClicked(int posisition);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMinggu;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_minggu);
            tvMinggu = itemView.findViewById(R.id.tv_minggu);
        }
    }
}
