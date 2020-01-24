package com.codates.plantie.view.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.adapter.HariAdapter;
import com.codates.plantie.adapter.MingguAdapter;
import com.codates.plantie.model.DeskripsiHari;
import com.codates.plantie.model.Hari;
import com.codates.plantie.model.Minggu;
import com.codates.plantie.view.Laporan;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView rvHari;

    private PageViewModel pageViewModel;
    private Minggu minggu;

    public PlaceholderFragment(Minggu minggu) {
        this.minggu = minggu;
    }

    public static PlaceholderFragment newInstance(int index, Minggu minggu) {
        PlaceholderFragment fragment = new PlaceholderFragment(minggu);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_laporan, container, false);
        rvHari = root.findViewById(R.id.rv_laporan);
        pageViewModel.getPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {

                showRecyclerList(minggu.getHari().get(position).getDeskripsi());

            }
        });
        return root;
    }
    private void showRecyclerList(final ArrayList<DeskripsiHari> deskripsiHari) {
        HariAdapter hariAdapter = new HariAdapter(deskripsiHari);
        rvHari.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHari.setAdapter(hariAdapter);
        rvHari.setHasFixedSize(true);

    }
}