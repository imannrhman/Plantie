package com.codates.plantie.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.Tanaman;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.TanamanData;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private RecyclerView rvTanaman;
    private ArrayList<Tanaman> list = new ArrayList<>();
    private AppBarConfiguration mAppBarConfiguration;
   TextView tvName,tvEmail;
   Intent home, myPlant, contactUs;
   ImageView imgProfile;
   private GoogleApiClient googleApiClient;
   private GoogleSignInOptions gso;
   Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        AwesomeBar bar = findViewById(R.id.bar);

        bar.getSettings().setAnimateMenu(false);

        bar.addAction(R.drawable.ic_add_black_24dp,"Tambah");

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
        NavigationView navigationView = findViewById(R.id.nav_view);
        setupNavDrawer(navigationView);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
        imgProfile = navigationView.getHeaderView(0).findViewById(R.id.img_profile);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

    }

    private void setupNavDrawer(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                final DrawerLayout drawer = findViewById(R.id.drawer_layout);

                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        home = new Intent(context, MainActivity.class);
                        startActivity(home);
                        onBackPressed();
                        break;
                    case R.id.nav_my_plant:
                        myPlant = new Intent(MainActivity.this, tanamanKu.class);
                        startActivity(myPlant);
                        onBackPressed();
                        break;
                }
                return true;
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        if (drawer.isDrawerOpen(Gravity.START)){
            drawer.closeDrawer(Gravity.START);
//            if (navigationView.isClickable()){
//                navigationView.;
//            }
        }else {
            super.onBackPressed();
        }
    }

    private void showRecyclerList() {
        TanamanAdapter tanamanAdapter = new TanamanAdapter(list);
        rvTanaman.setAdapter(tanamanAdapter);
        rvTanaman.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tanaman tanaman) {
                Intent intent = new Intent(getApplicationContext(),DetailTanaman.class);
                intent.putExtra(DetailTanaman.EXTRA_TANAMAN,tanaman);
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

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result =opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            tvName.setText(account.getDisplayName());
            tvEmail.setText(account.getEmail());
            if(account.getPhotoUrl() != null){
               Glide.with(this).load(account.getPhotoUrl()).into(imgProfile);
                 }else{
                imgProfile.setImageResource(R.mipmap.ic_logo);
            }

        }else{
            gotoLoginActivity();
        }
    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
