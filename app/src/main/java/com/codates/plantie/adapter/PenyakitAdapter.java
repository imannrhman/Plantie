package com.codates.plantie.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        String jenis = penyakit.getJenis_penyakit();
        String level = penyakit.getLevel();

        try{
            holder.txt_title.setText(penyakit.getTitle());
            holder.txt_jenis_penyakit.setText(jenis);
            if (jenis.equals("bakteri")){

                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#F4A560"));
                holder.imgJenis.setImageResource(R.drawable.ic_bakteri_penyakit);

            } else if (jenis.equals("hewan/serangga")){

                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#E14666"));
                holder.imgJenis.setImageResource(R.drawable.ic_hewan_penyakit);

            } else if (jenis.equals("virus")){

                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#00B3C9"));
                holder.imgJenis.setImageResource(R.drawable.ic_virus_penyakit);

            } else{
                holder.txt_jenis_penyakit.setText("Tidak Terdefinisi");
                holder.txt_jenis_penyakit.setTextColor(Color.parseColor("#4A4A4A"));
            }


            if (level.equals("1")){
                holder.txt_level.setText("Rendah");
                holder.txt_level.setTextColor(Color.parseColor("#6FCF97"));
                holder.rl_level.setBackgroundColor(Color.parseColor("#D4EDDA"));
            } else if (level.equals("2")){
                holder.txt_level.setText("Sedang");
                holder.txt_level.setTextColor(Color.parseColor("#00B3C9"));
                holder.rl_level.setBackgroundColor(Color.parseColor("#CCE5FF"));
            } else if (level.equals("3")){
                holder.txt_level.setText("Serius");
                holder.txt_level.setTextColor(Color.parseColor("#F2994A"));
                holder.rl_level.setBackgroundColor(Color.parseColor("#FCE5D2"));
            } else if(level.equals("4")){
                holder.txt_level.setText("Bahaya");
                holder.txt_level.setTextColor(Color.parseColor("#EB5757"));
                holder.rl_level.setBackgroundColor(Color.parseColor("#F8D7DA"));
            } else{
                holder.txt_level.setText(level);
            }

            Glide.with(holder.itemView.getContext()).load(penyakit.getGambar_tanaman()).into(holder.imgPenyakit);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.onItemClicked(listPenyakit.get(holder.getAdapterPosition()));
                }
            });
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return listPenyakit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt_title, txt_jenis_penyakit, txt_level;
        ImageView imgPenyakit, imgJenis;
        RelativeLayout rl_level;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_penyakit);
            txt_jenis_penyakit = itemView.findViewById(R.id.txt_jenis_tanaman);
            txt_level = itemView.findViewById(R.id.txt_level);
            imgPenyakit = itemView.findViewById(R.id.img_penyakit);
            imgJenis = itemView.findViewById(R.id.img_jenis_penyakit);
            rl_level = itemView.findViewById(R.id.relative_layout_level);
        }
    }

}
