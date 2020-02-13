package com.codates.plantie.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.codates.plantie.R;
import com.codates.plantie.model.Hari;
import com.codates.plantie.model.Minggu;
import com.codates.plantie.model.MingguTemp;
import com.codates.plantie.model.TanamanUser;
import com.codates.plantie.view.ui.main.SectionsPagerAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class Laporan extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_TEMP = "extra_temp";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CoordinatorLayout layout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        progressBar = findViewById(R.id.progress_bar);
        layout = findViewById(R.id.layout);
        Minggu minggu = getIntent().getParcelableExtra(EXTRA_POSITION);
        final String tanamanUserId = getIntent().getStringExtra(EXTRA_DATA);
        final MingguTemp mingguTemp = getIntent().getParcelableExtra(EXTRA_TEMP);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), minggu, tanamanUserId, mingguTemp);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        ImageView Arrowback = findViewById(R.id.ArrowBack);
        Arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();
            }
        });

        final FloatingActionButton fab = findViewById(R.id.btnchecklistLaporan);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("tanaman_user").document(tanamanUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        layout.setEnabled(false);
                        progressBar.setVisibility(View.VISIBLE);
                        ArrayList<Minggu> listMinggu = (ArrayList<Minggu>) documentSnapshot.get("minggu");
                        Gson gson = new Gson();
                        JsonElement jsonElement = gson.toJsonTree(listMinggu.get(mingguTemp.getPosition()));
                        Minggu minggu = gson.fromJson(jsonElement, Minggu.class);
                        int taksSelesai = 0;
                        int jumlahTask =0 ;
                        int hariSize = minggu.getHari().size();
                        for (int i = 0; i < hariSize; i++) {
                            Hari hari = minggu.getHari().get(i);
                            int deskripsiSize = hari.getDeskripsi().size();
                            for (int j = 0; j < deskripsiSize; j++) {
                                jumlahTask += 1;
                                if(hari.getDeskripsi().get(j).isSelesai()){
                                    taksSelesai += 1;
                                }
                            }
                        }
                        if(taksSelesai == jumlahTask){
                            ArrayList<Minggu> addMinggu =mingguTemp.getTempListMinggu();
                            addMinggu.get(mingguTemp.getPosition()).setSelesai(true);
                            db.collection("tanaman_user").document(tanamanUserId).update("minggu",addMinggu);
                            finish();
                        }else{
                            ArrayList<Minggu> addMinggu =mingguTemp.getTempListMinggu();
                            addMinggu.get(mingguTemp.getPosition()).setSelesai(false);
                            db.collection("tanaman_user").document(tanamanUserId).update("minggu",addMinggu);
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}