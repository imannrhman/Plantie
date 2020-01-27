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
import com.codates.plantie.model.Penyakit;

import java.util.ArrayList;

public class PenyakitAdapter extends RecyclerView.Adapter<PenyakitAdapter.ViewHolder> {

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback{
        void onItemClicked(Penyakit penyakit);
    }

    private ArrayList<Penyakit> listPenyakit;

    public PenyakitAdapter(ArrayList<Penyakit> list){
        this.listPenyakit = list;
    }

    @NonNull
    @Override
    public PenyakitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penyakit,parent,false);
        return new PenyakitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PenyakitAdapter.ViewHolder holder, int position){
        Penyakit penyakit = listPenyakit.get(position);
        holder.txt_title.setText(penyakit.getTitle());
        holder.txt_jenis_penyakit.setText(penyakit.getJenis_penyakit());
        holder.txt_level.setText(penyakit.getLevel());
        Glide.with(holder.itemView.getContext()).load(penyakit.getGambar_tanaman()).into(holder.imgPenyakit);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(listPenyakit.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPenyakit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_title, txt_jenis_penyakit, txt_level;
        ImageView imgPenyakit;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_penyakit);
            txt_jenis_penyakit = itemView.findViewById(R.id.txt_jenis_tanaman);
            txt_level = itemView.findViewById(R.id.txt_level);
            imgPenyakit = itemView.findViewById(R.id.img_penyakit);
        }
    }

}
