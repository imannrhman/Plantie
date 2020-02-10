package com.codates.plantie.adapter;

import android.graphics.Color;
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
    private ArrayList<Penyakit> listPenyakit;

    public PenyakitAdapter(ArrayList<Penyakit> list) {
        this.listPenyakit = list;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public PenyakitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_penyakit, parent, false);
        return new PenyakitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PenyakitAdapter.ViewHolder holder, int position) {
        Penyakit penyakit = listPenyakit.get(position);
        String jenis = penyakit.getJenis_penyakit();
        holder.txt_title.setText(penyakit.getTitle());
        try {
            holder.txt_jenis_penyakit.setText(jenis);
            if (jenis.equals("bakteri")) {

                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#F4A560"));
                holder.imgJenis.setImageResource(R.drawable.ic_bakteri_penyakit);

            } else if (jenis.equals("hewan/serangga")) {

                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#E14666"));
                holder.imgJenis.setImageResource(R.drawable.ic_hewan_penyakit);

            } else if (jenis.equals("virus")) {

                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#00B3C9"));
                holder.imgJenis.setImageResource(R.drawable.ic_virus_penyakit);

            } else {
                holder.txt_jenis_penyakit.setText("Tidak Terdefinisi");
                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#4A4A4A"));
            }
            holder.txt_level.setText(penyakit.getLevel());
            Glide.with(holder.itemView.getContext()).load(penyakit.getGambar_tanaman()).into(holder.imgPenyakit);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.onItemClicked(listPenyakit.get(holder.getAdapterPosition()));
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listPenyakit.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Penyakit penyakit);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title, txt_jenis_penyakit, txt_level;
        ImageView imgPenyakit, imgJenis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_penyakit);
            txt_jenis_penyakit = itemView.findViewById(R.id.txt_jenis_tanaman);
            txt_level = itemView.findViewById(R.id.txt_level);
            imgPenyakit = itemView.findViewById(R.id.img_penyakit);
            imgJenis = itemView.findViewById(R.id.img_jenis_penyakit);
        }
    }

}
