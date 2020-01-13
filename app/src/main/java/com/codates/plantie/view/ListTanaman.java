package com.codates.plantie.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.codates.plantie.R;
import com.codates.plantie.Tanaman;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.TanamanData;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;

import java.util.ArrayList;

public class ListTanaman extends AppCompatActivity {
    private RecyclerView rvTanaman;
    boolean muncul =true;
    private ArrayList<Tanaman> list = new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanaman);
        AwesomeBar bar = findViewById(R.id.bar);
        final ImageView search = findViewById(R.id.searchbar);

        bar.getSettings().setAnimateMenu(false);
        bar.displayHomeAsUpEnabled(true);
        bar.addAction(R.drawable.ic_search_black_24dp,"Cari");

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                
            }
        });
        bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                if(muncul){
                    search.setVisibility(View.VISIBLE);
                    muncul = false;
                }else{
                    search.setVisibility(View.INVISIBLE);
                    muncul = true;
                }

            }
        });

        rvTanaman = findViewById(R.id.recycler_view);
        rvTanaman.setHasFixedSize(true);
        list.addAll(TanamanData.getListData());
        showRecyclerList();
    }

    private void showRecyclerList() {
        TanamanAdapter tanamanAdapter = new TanamanAdapter(list);
        rvTanaman.setAdapter(tanamanAdapter);
        rvTanaman.setLayoutManager(new LinearLayoutManager(this));

        tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tanaman tanaman) {
                Intent intent = new Intent(getApplicationContext(),DetailTanaman.class);
                intent.putExtra(DetailTanaman.EXTRA_TANAMAN,tanaman);
                startActivity(intent);
            }
        });

    }
}
