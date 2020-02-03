package com.codates.plantie.view;

import android.content.Intent;
import android.os.Bundle;

import com.codates.plantie.R;
import com.codates.plantie.model.Hari;
import com.codates.plantie.model.Minggu;
import com.codates.plantie.model.MingguTemp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codates.plantie.view.ui.main.SectionsPagerAdapter;

public class Laporan extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_TEMP = "extra_temp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        Minggu minggu = getIntent().getParcelableExtra(EXTRA_POSITION);
        String tanamanUserId = getIntent().getStringExtra(EXTRA_DATA);
        MingguTemp mingguTemp = getIntent().getParcelableExtra(EXTRA_TEMP);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(),minggu,tanamanUserId,mingguTemp);
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

        FloatingActionButton fab = findViewById(R.id.btnchecklistLaporan);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Anda telah Berhasil ! ", Snackbar.LENGTH_LONG)
                        .show();
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