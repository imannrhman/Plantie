package com.codates.plantie.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.adapter.MingguAdapter;
import com.codates.plantie.model.Tutorial;
import com.codates.plantie.view_menanam.MenanamActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;


public class DetailTanaman extends AppCompatActivity {
    public static final String EXTRA_TANAMAN = "extra_tanaman";
    ImageView gambar;
    public RecyclerView rvMinggu;
    RelativeLayout rlCaraMenanam;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvMinggu;
    Tanaman tanaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);

        toolbar = findViewById(R.id.toolbar);
        tvMinggu = findViewById(R.id.tv_minggu);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_black_24dp);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white)
        );

        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.white)
        );

        gambar = findViewById(R.id.gambar_header);
        rvMinggu = findViewById(R.id.recycler_view_minggu);
        rlCaraMenanam = findViewById(R.id.btnCaraMenanam);
        this.tanaman = getIntent().getParcelableExtra(EXTRA_TANAMAN);
        assert tanaman != null;
        Tutorial documentReference = tanaman.getIdTutorial();
        try {
            Toast.makeText(this, documentReference + "", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        tvMinggu.setText(tanaman.getMinggu() + " minggu");
        collapsingToolbarLayout.setTitle(tanaman.getNamaTanaman());
        Glide.with(this).load(tanaman.getGambar())
                .into(gambar);
        showRecyclerList();

        rlCaraMenanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Sudah di Click", Toast.LENGTH_SHORT).show();
                System.out.println(tanaman.getIdTutorial());
                Intent mulaiMenanam = new Intent(DetailTanaman.this, MenanamActivity.class);
                Log.d("tanamam",tanaman.getIdTutorial() + "");
                mulaiMenanam.putExtra(MenanamActivity.EXTRA_POSITION, tanaman);
                startActivity(mulaiMenanam);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void showRecyclerList() {
        MingguAdapter mingguAdapter = new MingguAdapter();
        rvMinggu.setAdapter(mingguAdapter);
        rvMinggu.setLayoutManager(new GridLayoutManager(this, 3));
        mingguAdapter.setOnItemClickCallback(new MingguAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int posisition) {
                Intent intent = new Intent(getApplicationContext(), Laporan.class);
                intent.putExtra(Laporan.EXTRA_POSITION, posisition);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
