package com.codates.plantie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.model.Penyakit;
import com.codates.plantie.model.Tanaman;

import java.util.ArrayList;

public class JenisTanamanAdapter extends RecyclerView.Adapter<JenisTanamanAdapter.ViewHolder> {

    private OnItemClickCallback onItemClickCallback;
    private ArrayList<Tanaman> listTanaman;

    public JenisTanamanAdapter(ArrayList<Tanaman> list) {
        this.listTanaman = list;
    }

    public void setOnItemClickCallback(JenisTanamanAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public JenisTanamanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_jenis_tanaman, parent, false);
        return new JenisTanamanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final JenisTanamanAdapter.ViewHolder holder, int position) {
        Tanaman tanaman = listTanaman.get(position);
        holder.txt_jenis_tanaman.setText(tanaman.getNamaTanaman());
    }

    @Override
    public int getItemCount() {
        return listTanaman.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Penyakit penyakit);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_jenis_tanaman;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_jenis_tanaman = itemView.findViewById(R.id.txt_jenis_tanaman);
        }
    }

}
