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
import com.codates.plantie.model.Hari;
import com.codates.plantie.model.Minggu;
import com.codates.plantie.model.MingguTemp;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.adapter.MingguAdapter;
import com.codates.plantie.model.Tutorial;
import com.codates.plantie.model.User;
import com.codates.plantie.view_menanam.MenanamActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.util.JsonMapper;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class DetailTanaman extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final String EXTRA_TANAMAN = "extra_tanaman";
    ImageView gambar;
    public RecyclerView rvMinggu;
    RelativeLayout rlCaraMenanam;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvMinggu;
    Tanaman tanaman;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


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
        final Tutorial documentReference = tanaman.getIdTutorial();
        try {
            Toast.makeText(this, documentReference + "", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        tvMinggu.setText(tanaman.getMinggu() + " minggu");
        collapsingToolbarLayout.setTitle(tanaman.getNamaTanaman());
        Glide.with(this).load(tanaman.getGambar())
                .into(gambar);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        showTanamanUser();
        rlCaraMenanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Sudah di Click", Toast.LENGTH_SHORT).show();
                System.out.println(tanaman.getIdTutorial());
                Intent mulaiMenanam = new Intent(DetailTanaman.this, MenanamActivity.class);
                Log.d("tanamam", tanaman.getIdTutorial() + "");
                mulaiMenanam.putExtra(MenanamActivity.EXTRA_POSITION, tanaman);
                startActivity(mulaiMenanam);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        showTanamanUser();
    }

    private void showTanamanUser(){
        final GoogleSignInAccount account = getAccount();
        assert account != null;
        DocumentReference reference = db.collection("tanaman").document(tanaman.getIdTanaman());
        db.collection("tanaman_user").whereEqualTo("uid", account.getId()).whereEqualTo("idTanaman", reference).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {

                } else {
                    ArrayList<Minggu> listMinggu = new ArrayList<>();
                    String tanamanUserId = "";
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        try {

                            listMinggu = (ArrayList<Minggu>) documentSnapshot.get("minggu");
                            tanamanUserId = documentSnapshot.getId();
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    for (int i = 0; i < listMinggu.size(); i++) {
                        Gson gson = new Gson();
                        JsonElement jsonElement = gson.toJsonTree(listMinggu.get(i));
                        Minggu pojo = gson.fromJson(jsonElement, Minggu.class);
                        listMinggu.set(i, pojo);
                    }
                    showRecyclerList(listMinggu,tanamanUserId);
                }
            }
        });
    }
    private GoogleSignInAccount getAccount() {
        GoogleSignInResult result = User.setOptionalPendingResult(googleApiClient);
        if (result != null) {
            GoogleSignInAccount account = User.handleSignInResult(result);
            if (account != null) {
                return account;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        showTanamanUser();
    }

    @Override
    protected void onPause() {
        super.onPause();
        showTanamanUser();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showTanamanUser();
    }

    private void showRecyclerList(final ArrayList<Minggu> listMinggu, final String tanamanUserId) {
        rvMinggu.setVisibility(View.VISIBLE);
        MingguAdapter mingguAdapter = new MingguAdapter(listMinggu);
        rvMinggu.setAdapter(mingguAdapter);
        rvMinggu.setLayoutManager(new GridLayoutManager(this, 3));
        mingguAdapter.setOnItemClickCallback(new MingguAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int position) {
               Intent intent = new Intent(getApplicationContext(),Laporan.class);
                MingguTemp mingguTemp = new MingguTemp();
                mingguTemp.setTempListMinggu(listMinggu);
                mingguTemp.setPosition(position);
               intent.putExtra(Laporan.EXTRA_POSITION,listMinggu.get(position));
               intent.putExtra(Laporan.EXTRA_DATA,tanamanUserId);
               intent.putExtra(Laporan.EXTRA_TEMP,mingguTemp);
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


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
