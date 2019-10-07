package com.codates.plantie.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codates.plantie.R;

public class DetailTugas extends AppCompatActivity {
    public static  final String EXTRA_POSISITION = "extra_posisition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);
        getSupportActionBar().hide();
        int position = getIntent().getIntExtra(EXTRA_POSISITION,0);


    }
}
