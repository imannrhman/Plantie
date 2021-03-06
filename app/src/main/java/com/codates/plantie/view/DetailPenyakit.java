package com.codates.plantie.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.adapter.JenisTanamanAdapter;
import com.codates.plantie.adapter.PenyakitAdapter;
import com.codates.plantie.model.Penyakit;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.model.Tutorial;
import com.codates.plantie.model.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;

public class DetailPenyakit extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final String EXTRA_PENYAKIT = "extra_penyakit";
    private RecyclerView recyclerViewJenisTanaman;
    ImageView imageView, imgJenis, img_btn_ig, img_btn_tele, img_btn_fb, img_btn_email;
    TextView txt_judul, txt_deskripsi, txt_solusi, txt_jenis_penyakit, txt_level, txt_contact;
    Toolbar toolbar;
    RelativeLayout rl_level;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Penyakit penyakit;
    LinearLayout ll_contact;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    boolean opened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penyakit);

        toolbar = findViewById(R.id.toolbar);
        txt_judul = findViewById(R.id.txt_judul);
        txt_solusi = findViewById(R.id.txt_solusi);
        txt_deskripsi = findViewById(R.id.txt_deskripsi);
        txt_jenis_penyakit = findViewById(R.id.txt_jenis_penyakit);
        txt_level = findViewById(R.id.txt_level);
        rl_level = findViewById(R.id.relative_layout_level);
        imgJenis = findViewById(R.id.img_jenis_penyakit);
        txt_contact = findViewById(R.id.txt_contact_button);
        ll_contact = findViewById(R.id.ll_contact);

        img_btn_ig = findViewById(R.id.btn_contact_instagram);
        img_btn_tele = findViewById(R.id.btn_contact_telegram);
        img_btn_fb = findViewById(R.id.btn_contact_facebook);
        img_btn_email = findViewById(R.id.btn_contact_email);

        img_btn_ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/rashifmi");
                Intent followme = new Intent(Intent.ACTION_VIEW, uri);

                followme.setPackage("com.instagram.android");

                try{
                    startActivity(followme);
                } catch (ActivityNotFoundException ex){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/_u/rashifmi")));
                    ex.printStackTrace();
                }
            }
        });

        img_btn_tele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.telegram.me/rashifmi");
                Intent chatme = new Intent(Intent.ACTION_VIEW, uri);

                chatme.setPackage("org.telegram.messenger");

                try {
                    startActivity(chatme);
                } catch (ActivityNotFoundException ex){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.telegram.me/rashifmi")));
                    ex.printStackTrace();
                }
            }
        });

        img_btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/iman.nurrohman.7");

                Intent chatme = new Intent(Intent.ACTION_VIEW, uri);

                chatme.setPackage("com.facebook.katana");

                try{
                    startActivity(chatme);
                } catch (ActivityNotFoundException ex){
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/iman.nurrohman.7")));
                    ex.printStackTrace();
                }
            }
        });

        img_btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "codatescompany@gmail.com", null
                ));
                FirebaseUser account = firebaseAuth.getCurrentUser();
//                        intent.setType("text/plain");
//                        startActivity(Intent.createChooser(intent, "Kritik & Saran"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"codatescompany@gmail.com"});
                intent.putExtra(Intent.EXTRA_CC, new String[]{account.getEmail().toString()});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Kritik & Saran");
                intent.putExtra(Intent.EXTRA_TEXT, "");

                try {
                    startActivity(Intent.createChooser(intent, "Ingin Mengirim Email ?"));
                } catch (android.content.ActivityNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        txt_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ll_contact.setVisibility(View.VISIBLE);
                if (!opened){
                    ll_contact.setVisibility(View.VISIBLE);
                    TranslateAnimation animation = new TranslateAnimation(0, 0, ll_contact.getHeight(),0);
                    animation.setDuration(500);
                    animation.setFillAfter(true);
                    ll_contact.startAnimation(animation);
                }else {
                    ll_contact.setVisibility(View.INVISIBLE);
                    TranslateAnimation animation = new TranslateAnimation(0,0,0,ll_contact.getHeight());
                    animation.setDuration(500);
                    animation.setFillAfter(true);
                    ll_contact.startAnimation(animation);
                }
                opened = !opened;

            }
        });

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

        imageView = findViewById(R.id.gambar_header);
        this.penyakit = getIntent().getParcelableExtra(EXTRA_PENYAKIT);
        assert penyakit != null;

        txt_judul.setText(penyakit.getTitle());
        txt_deskripsi.setText(penyakit.getContent().getDeskripsi());
        txt_solusi.setText(penyakit.getContent().getSolusi());

        String jenis = penyakit.getJenis_penyakit();
        txt_jenis_penyakit.setText(jenis);
        if (jenis.equals("bakteri")){

            txt_jenis_penyakit.setTextColor(Color.parseColor("#6FCF97"));
            imgJenis.setImageResource(R.drawable.ic_bakteri_penyakit);

        } else if (jenis.equals("hewan/serangga")){

            txt_jenis_penyakit.setTextColor(Color.parseColor("#EB5757"));
            imgJenis.setImageResource(R.drawable.ic_hewan_penyakit);

        } else if (jenis.equals("virus")){

            txt_jenis_penyakit.setTextColor(Color.parseColor("#2F80ED"));
            imgJenis.setImageResource(R.drawable.ic_virus_penyakit);

        } else if (jenis.equals("jamur")){

            txt_jenis_penyakit.setTextColor(Color.parseColor("#F2994A"));
            imgJenis.setImageResource(R.drawable.ic_jamur_penyakit);

        }else{

            txt_jenis_penyakit.setText("Tidak Terdefinisi");
            txt_jenis_penyakit.setTextColor(Color.parseColor("#4A4A4A"));

        }


        String level = penyakit.getLevel();

        if (level.equals("1")){
            txt_level.setText("Rendah");
            txt_level.setTextColor(Color.parseColor("#6FCF97"));
            rl_level.setBackgroundColor(Color.parseColor("#D4EDDA"));
        } else if (level.equals("2")){
            txt_level.setText("Sedang");
            txt_level.setTextColor(Color.parseColor("#00B3C9"));
            rl_level.setBackgroundColor(Color.parseColor("#CCE5FF"));
        } else if (level.equals("3")){
            txt_level.setText("Serius");
            txt_level.setTextColor(Color.parseColor("#F2994A"));
            rl_level.setBackgroundColor(Color.parseColor("#FCE5D2"));
        } else if(level.equals("4")){
            txt_level.setText("Bahaya");
            txt_level.setTextColor(Color.parseColor("#EB5757"));
            rl_level.setBackgroundColor(Color.parseColor("#F8D7DA"));
        } else{
            txt_level.setText(level);
        }

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



        recyclerViewJenisTanaman = findViewById(R.id.recycler_view_jenis_tanaman);
        showRecyclerView(penyakit.getJenis_tanaman());

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

    private void showRecyclerView(final ArrayList<Tanaman> tanaman){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        JenisTanamanAdapter jenisTanamanAdapter = new JenisTanamanAdapter(tanaman);
        recyclerViewJenisTanaman.setAdapter(jenisTanamanAdapter);
        recyclerViewJenisTanaman.setLayoutManager(llm);
        recyclerViewJenisTanaman.setHasFixedSize(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
