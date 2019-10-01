package com.codates.plantie.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.Tanaman;
import com.codates.plantie.adapter.MingguAdapter;

public class DetailTanaman extends AppCompatActivity {
    public static final String EXTRA_TANAMAN = "extra_tanaman";
    ImageView gambar;
    RecyclerView rvMinggu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);
        gambar = findViewById(R.id.img_header);
        rvMinggu = findViewById(R.id.recycler_view_minggu);
        Tanaman tanaman = getIntent().getParcelableExtra(EXTRA_TANAMAN);
        gambar.setImageResource(tanaman.getGambar());
        showRecyclerList();

    }

    private void showRecyclerList() {
        MingguAdapter mingguAdapter = new MingguAdapter();
        rvMinggu.setAdapter(mingguAdapter);
        rvMinggu.setLayoutManager(new GridLayoutManager(this, 3));
        mingguAdapter.setOnItemClickCallback(new MingguAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int posisition) {
                    Intent intent = new Intent(getApplicationContext(),DetailTugas.class);
                    intent.putExtra(DetailTugas.EXTRA_POSISITION,posisition);
                    startActivity(intent);
            }
        });

    }
}
