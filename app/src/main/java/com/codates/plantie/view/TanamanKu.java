package com.codates.plantie.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.model.User;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import id.arieridwan.lib.PageLoader;

public class TanamanKu extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView rvTanaman;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private ImageView imgEmpty;
    private TextView txtEmpty;
    private PageLoader pageLoader;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanaman_ku);
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
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        final FirebaseUser account = firebaseAuth.getCurrentUser();
        assert account != null;
        rvTanaman = findViewById(R.id.recycler_view);
        imgEmpty = findViewById(R.id.img_empty);
        txtEmpty = findViewById(R.id.tv_empty);
        pageLoader = findViewById(R.id.progress_bar);
        setPageLoader(pageLoader);
        db.collection("tanaman_user").whereEqualTo("uid", account.getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                final ArrayList<Tanaman> listTanaman = new ArrayList<>();
                if (queryDocumentSnapshots.isEmpty()) {
                    pageLoader.stopProgress();
                    imgEmpty.setVisibility(View.VISIBLE);
                    txtEmpty.setVisibility(View.VISIBLE);

                } else {
                    for (final DocumentSnapshot document : queryDocumentSnapshots) {
                        DocumentReference tanamanReference = (DocumentReference) document.get("idTanaman");
                        assert tanamanReference != null;
                        tanamanReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                try {
                                    Tanaman tanaman = documentSnapshot.toObject(Tanaman.class);
                                    tanaman.setIdTanaman(documentSnapshot.getId());
                                    listTanaman.add(tanaman);
                                    pageLoader.stopProgress();
                                    showRecyclerList(listTanaman);
                                    Toast.makeText(getApplicationContext(), document.getId() + "", Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }

            }
        });
    }

    private void setPageLoader(PageLoader pageLoader) {
        pageLoader.setImageLoading(R.drawable.logo);
        pageLoader.setLoadingAnimationMode("flip");
        pageLoader.setTextLoading("Tunggu");
        pageLoader.setTextSize(0);
        pageLoader.setLoadingImageWidth(250);
        pageLoader.setLoadingImageHeight(250);
        pageLoader.setTextColor(ColorStateList.valueOf(0));
        pageLoader.startProgress();
    }

    private void showRecyclerList(ArrayList<Tanaman> tanaman) {
        TanamanAdapter tanamanAdapter = new TanamanAdapter(tanaman);
        rvTanaman.setAdapter(tanamanAdapter);
        rvTanaman.setLayoutManager(new LinearLayoutManager(this));
        rvTanaman.setHasFixedSize(true);
        try {
            tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
                @Override
                public void onItemClicked(Tanaman tanaman) {
                    Intent intent = new Intent(getApplicationContext(), DetailTanaman.class);
                    intent.putExtra(DetailTanaman.EXTRA_TANAMAN, tanaman);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
