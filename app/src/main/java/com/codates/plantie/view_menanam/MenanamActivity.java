package com.codates.plantie.view_menanam;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codates.plantie.R;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.model.TanamanUser;
import com.codates.plantie.model.TanamanUserData;
import com.codates.plantie.model.User;
import com.codates.plantie.view_menanam.ui.main.SectionsPagerAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import id.arieridwan.lib.PageLoader;

public class MenanamActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final String EXTRA_POSITION = "extra_position";
    RecyclerView rvTanaman;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menanam);
        final Tanaman tanaman = getIntent().getParcelableExtra(EXTRA_POSITION);
        assert tanaman != null;
        final ViewPager viewPager = findViewById(R.id.view_pager);
        Toast.makeText(getApplicationContext(), tanaman.getNamaTanaman(), Toast.LENGTH_LONG).show();
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        final SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), tanaman);
        viewPager.setAdapter(sectionsPagerAdapter);
        final FloatingActionButton fab = findViewById(R.id.btnchecklistTutorial);
        DocumentReference documentReference = db.collection("tanaman").document(tanaman.getIdTanaman());
        final FirebaseUser account = firebaseAuth.getCurrentUser();
        db.collection("tanaman_user").whereEqualTo("uid", account.getUid()).whereEqualTo("idTanaman", documentReference).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    infoDialog();
                    fab.setVisibility(View.VISIBLE);
                    fab.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            DocumentReference documentReference = db.collection("tanaman").document(tanaman.getIdTanaman());
                            TanamanUser userData = TanamanUserData.setTanamanUser(Integer.parseInt(tanaman.getMinggu()),
                                    documentReference,
                                    account.getUid()
                            );
                            db.collection("tanaman_user").add(userData);
                            finish();
                        }
                    });
                }
            }
        });
    }

    public void infoDialog() {
        Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
        Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);
        final MaterialDialog infoDialog = new MaterialDialog.Builder(this)
                .title("INFO")
                .content("1. Ikuti instruksi mulai dari alat bahan & cara menanam agar menghasilkan tanaman yang berkualitas" +
                        "\r\n\n2. Klik tombol ceklis jika instruksi sudah dilakukan")
                .positiveText("OK")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .icon(getResources().getDrawable(R.mipmap.ic_logo))
                .typeface(faceMed, face)
                .autoDismiss(true)
                .show();

        View submitButton = infoDialog.getActionButton(DialogAction.POSITIVE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
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

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}