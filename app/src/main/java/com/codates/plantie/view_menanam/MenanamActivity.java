package com.codates.plantie.view_menanam;

import android.content.Intent;
import android.os.Bundle;

import com.codates.plantie.R;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.model.Tutorial;
import com.codates.plantie.view.DetailTanaman;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.codates.plantie.view_menanam.ui.main.SectionsPagerAdapter;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class MenanamActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";
    RecyclerView rvTanaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menanam);
        Tanaman tanaman = getIntent().getParcelableExtra(EXTRA_POSITION);
        assert tanaman != null;
        final ViewPager viewPager = findViewById(R.id.view_pager);
        Toast.makeText(getApplicationContext(),tanaman.getNamaTanaman(),Toast.LENGTH_LONG).show();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager(),tanaman);
        viewPager.setAdapter(sectionsPagerAdapter);
        FloatingActionButton fab = findViewById(R.id.btnchecklistTutorial);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}