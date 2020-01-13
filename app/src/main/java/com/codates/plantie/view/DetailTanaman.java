package com.codates.plantie.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.Tanaman;
import com.codates.plantie.adapter.MingguAdapter;
import com.codates.plantie.view_menanam.MenanamActivity;
import com.github.florent37.awesomebar.AwesomeBar;

import javax.annotation.Nullable;

public class DetailTanaman extends AppCompatActivity {
    public static final String EXTRA_TANAMAN = "extra_tanaman";
    ImageView gambar;
    RecyclerView rvMinggu;
    RelativeLayout rlCaraMenanam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);
        gambar = findViewById(R.id.img_header);
        rvMinggu = findViewById(R.id.recycler_view_minggu);
        rlCaraMenanam = findViewById(R.id.btnCaraMenanam);
        Tanaman tanaman = getIntent().getParcelableExtra(EXTRA_TANAMAN);
        gambar.setImageURI(Uri.parse(tanaman.getGambar()));
        showRecyclerList();

        rlCaraMenanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mulaiTanamIntent = new Intent(DetailTanaman.this, MenanamActivity.class);
                startActivity(mulaiTanamIntent);
            }
        });

        getSupportActionBar().hide();
        AwesomeBar bar = findViewById(R.id.bar);
        int position = getIntent().getIntExtra(EXTRA_TANAMAN,0);
        bar.getSettings().setAnimateMenu(false);
        bar.displayHomeAsUpEnabled(true);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void showRecyclerList() {
        MingguAdapter mingguAdapter = new MingguAdapter();
        rvMinggu.setAdapter(mingguAdapter);
        rvMinggu.setLayoutManager(new GridLayoutManager(this, 3));
        mingguAdapter.setOnItemClickCallback(new MingguAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int posisition) {
                    Intent intent = new Intent(getApplicationContext(), Laporan.class);
                    intent.putExtra(Laporan.EXTRA_POSITION,posisition);
                    startActivity(intent);
            }
        });

    }
}
