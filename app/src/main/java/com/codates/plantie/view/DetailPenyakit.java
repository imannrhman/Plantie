package com.codates.plantie.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.model.Penyakit;
import com.codates.plantie.model.Tutorial;
import com.codates.plantie.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailPenyakit extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String EXTRA_PENYAKIT = "extra_penyakit";
    ImageView imageView;
    TextView txt_judul, txt_deskripsi, txt_solusi;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Penyakit penyakit;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);

        toolbar = findViewById(R.id.toolbar);
        txt_judul = findViewById(R.id.txt_judul);
        txt_solusi = findViewById(R.id.txt_solusi);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);

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

        imageView = findViewById(R.id.gambar_header




        );
        this.penyakit = getIntent().getParcelableExtra(EXTRA_PENYAKIT);
        assert penyakit != null;
        txt_judul.setText(penyakit.getTitle());
        txt_deskripsi.setText(penyakit.getContent().getDeskripsi());
        txt_solusi.setText(penyakit.getContent().getSolusi());
        Glide.with(this).load(penyakit.getGambar_tanaman()).into(imageView);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        final GoogleSignInAccount account = getAccount();
        assert  account != null;
    }

    private GoogleSignInAccount getAccount(){
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
