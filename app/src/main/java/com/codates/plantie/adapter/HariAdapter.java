package com.codates.plantie.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.model.DeskripsiHari;
import com.codates.plantie.model.Hari;

import java.util.ArrayList;

public class HariAdapter extends RecyclerView.Adapter<HariAdapter.ViewHolder> {
    public HariAdapter(ArrayList<DeskripsiHari> listHari) {
        this.listDeskripsi = listHari;
    }

    ArrayList<DeskripsiHari> listDeskripsi;

    @NonNull
    @Override
    public HariAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new HariAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HariAdapter.ViewHolder holder, int position) {
        holder.checkBox.setText(listDeskripsi.get(position).deskripsi);
        holder.checkBox.setChecked(listDeskripsi.get(position).selesai);

    }

    @Override
    public int getItemCount() {
        return listDeskripsi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public ViewHolder(View view) {
            super(view);
            checkBox = view.findViewById(R.id.checkbox_laporan);
        }
    }
}
