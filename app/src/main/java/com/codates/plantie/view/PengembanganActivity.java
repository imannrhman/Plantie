package com.codates.plantie.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.codates.plantie.R;
import com.github.florent37.awesomebar.AwesomeBar;

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
