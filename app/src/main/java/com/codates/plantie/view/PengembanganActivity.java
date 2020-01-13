package com.codates.plantie.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.codates.plantie.R;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.material.appbar.AppBarLayout;

public class PengembanganActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembangan);
        getSupportActionBar().hide();

        AwesomeBar bar = findViewById(R.id.bar);
        bar.displayHomeAsUpEnabled(true);
        bar.getSettings().setAnimateMenu(false);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}
