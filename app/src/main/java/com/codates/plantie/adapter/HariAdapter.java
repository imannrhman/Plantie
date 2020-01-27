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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HariAdapter extends RecyclerView.Adapter<HariAdapter.ViewHolder> {
    public HariAdapter(ArrayList<DeskripsiHari> listHari, Hari hari) {
        this.listDeskripsi = listHari;
        this.hari = hari;
    }

    Hari hari;
    private OnItemClickCallback onItemClickCallback;
    ArrayList<DeskripsiHari> listDeskripsi;

    @NonNull
    @Override
    public HariAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_laporan, parent, false);
        return new HariAdapter.ViewHolder(view);
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public interface OnItemClickCallback {
        void onItemClicked(int posisition);
    }

    @Override
    public void onBindViewHolder(@NonNull HariAdapter.ViewHolder holder, final int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        System.out.println(hari.getDate());
        try {
            Date parseDate = simpleDateFormat.parse(hari.getDate());
            int dateDate = Integer.parseInt(dateFormat.format(parseDate));
            int dateToday = Integer.parseInt(dateFormat.format(new Date()));
            System.out.println(dateDate);
            System.out.println(dateToday);
            System.out.println(dateDate <= dateToday);
            System.out.println(hari.getDate().equals(simpleDateFormat.format(new Date())) || dateDate <= dateToday);
            holder.checkBox.setEnabled(hari.getDate().equals(simpleDateFormat.format(new Date())) || dateDate <= dateToday);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.checkBox.setText(listDeskripsi.get(position).deskripsi);
        holder.checkBox.setChecked(listDeskripsi.get(position).selesai);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(position);
            }
        });
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
