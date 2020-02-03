package com.codates.plantie.view.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.codates.plantie.model.MingguTemp;
import com.codates.plantie.view.Laporan;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    RecyclerView rvHari;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private PageViewModel pageViewModel;
    private Minggu minggu;
    private String tanamanUserId;
    private  MingguTemp mingguTemp;
    public PlaceholderFragment(Minggu minggu,String tanamanUserId,MingguTemp mingguTemp) {
        this.minggu = minggu;
        this.tanamanUserId = tanamanUserId;
        this.mingguTemp = mingguTemp;
    }

    public static PlaceholderFragment newInstance(int index, Minggu minggu, String tanamanUserId, MingguTemp mingguTemp) {
        PlaceholderFragment fragment = new PlaceholderFragment(minggu,tanamanUserId,mingguTemp);
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
    public void onResume() {
        super.onResume();
        pageViewModel.getPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer position) {
                showRecyclerList(minggu.getHari().get(position).getDeskripsi(),minggu.getHari(),position);
            }
        });
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
                    showRecyclerList(minggu.getHari().get(position).getDeskripsi(),minggu.getHari(),position);
            }
        });
        return root;
    }
    private void showRecyclerList(final ArrayList<DeskripsiHari> deskripsiHari, final ArrayList<Hari> hari, final int dayPosition) {
        System.out.println(hari.get(dayPosition));
        HariAdapter hariAdapter = new HariAdapter(deskripsiHari,hari.get(dayPosition));
        rvHari.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHari.setAdapter(hariAdapter);
        rvHari.setHasFixedSize(true);
        hariAdapter.setOnItemClickCallback(new HariAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int position) {
                deskripsiHari.get(position).setSelesai(!deskripsiHari.get(position).isSelesai());
                hari.get(dayPosition).setDeskripsi(deskripsiHari);
                 int mingguPosition = mingguTemp.getPosition();
                 ArrayList<Minggu> mingguList = mingguTemp.getTempListMinggu();
                 mingguList.get(mingguPosition).setHari(hari);
                 db.collection("tanaman_user").document(tanamanUserId).update("minggu",mingguList);

            }
        });

    }
}