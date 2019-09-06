package com.codates.plantie.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.codates.plantie.R;
import com.codates.plantie.Tanaman;
import com.codates.plantie.TanamanAdapter;
import com.codates.plantie.TanamanData;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvTanaman;
    private ArrayList<Tanaman> list = new ArrayList<>();
    private AppBarConfiguration mAppBarConfiguration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AwesomeBar bar = findViewById(R.id.bar);

        bar.getSettings().setAnimateMenu(false);

        bar.addAction(R.drawable.ic_add_black_24dp,"Add");


        bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
            @Override
            public void onActionItemClicked(int position, ActionItem actionItem) {
                Intent intent = new Intent(MainActivity.this, ListTanaman.class);
                startActivity(intent);
            }
        });
        bar.setOverflowActionItemClickListener(new AwesomeBar.OverflowActionItemClickListener() {
            @Override
            public void onOverflowActionItemClicked(int position, String item) {
                Toast.makeText(getBaseContext(), item+" clicked", Toast.LENGTH_LONG).show();
            }
        });


        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.START);
            }
        });

        bar.displayHomeAsUpEnabled(false);
        rvTanaman = findViewById(R.id.recycler_view);
        rvTanaman.setHasFixedSize(true);
        list.addAll(TanamanData.getListData());
        showRecyclerList();

    }

    private void showRecyclerList() {
        TanamanAdapter tanamanAdapter = new TanamanAdapter(list);
        rvTanaman.setAdapter(tanamanAdapter);
        rvTanaman.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tanaman tanaman) {
                Intent intent = new Intent(getApplicationContext(),DetailTanaman.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
