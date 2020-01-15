package com.codates.plantie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.model.Tanaman;

import java.util.ArrayList;

public class TanamanAdapter extends RecyclerView.Adapter<TanamanAdapter.ViewHolder> {
    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClicked(Tanaman tanaman);
    }
    private ArrayList<Tanaman> listTanaman;

    public TanamanAdapter(ArrayList<Tanaman> list){this.listTanaman = list;}
    @NonNull
    @Override
    public TanamanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tanaman,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TanamanAdapter.ViewHolder holder, int position) {
        Tanaman tanaman = listTanaman.get(position);
        holder.tvName.setText(tanaman.getNamaTanaman());
        holder.tvHari.setText(tanaman.getMinggu() + " minggu ");
        Glide.with(holder.itemView.getContext()).load(tanaman.getGambar())
                .into(holder.imgGambar);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listTanaman.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTanaman.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGambar;
        TextView tvName,tvHari;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGambar = itemView.findViewById(R.id.img_gambar);
            tvName = itemView.findViewById(R.id.tv_tanaman);
            tvHari = itemView.findViewById(R.id.tv_waktu);
        }
    }
}
