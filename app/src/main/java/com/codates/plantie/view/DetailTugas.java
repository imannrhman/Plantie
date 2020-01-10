package com.codates.plantie.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codates.plantie.R;
import com.github.florent37.awesomebar.AwesomeBar;

public class DetailTugas extends AppCompatActivity {
    public static  final String EXTRA_POSISITION = "extra_posisition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);
        getSupportActionBar().hide();
        AwesomeBar bar = findViewById(R.id.bar);
        int position = getIntent().getIntExtra(EXTRA_POSISITION,0);
        bar.getSettings().setAnimateMenu(false);
        bar.displayHomeAsUpEnabled(true);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
