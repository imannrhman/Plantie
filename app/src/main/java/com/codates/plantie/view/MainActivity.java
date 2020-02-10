package com.codates.plantie.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.model.Tanaman;
import com.facebook.login.LoginManager;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private RecyclerView rvTanaman;
    private ArrayList<Tanaman> list = new ArrayList<>();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView tvName, tvEmail, tvJumlahTanaman;
    private Intent home, myPlant, setting, hama;
    private ImageView imgProfile;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser account = firebaseAuth.getCurrentUser();
        if (account == null) {
            gotoLoginActivity();
            finish();
        }
        setContentView(R.layout.activity_main);
        context = this;

        AwesomeBar bar = findViewById(R.id.bar);
        tvJumlahTanaman = findViewById(R.id.tv_jumlah_tanamanku);
        bar.getSettings().setAnimateMenu(false);

        bar.addAction(R.drawable.ic_add_black_24dp, "Tambah");

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
                Toast.makeText(getBaseContext(), item + " clicked", Toast.LENGTH_LONG).show();
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
        showRecyclerList(list);
        NavigationView navigationView = findViewById(R.id.nav_view);
        setupNavDrawer(navigationView);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tv_name);
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tv_email);
        imgProfile = navigationView.getHeaderView(0).findViewById(R.id.img_profile);


        showJumlahTanaman(account);
        // list tanaman
        showListTanaman();
        System.out.println("list kosong" + list.isEmpty());

    }

    private void showListTanaman() {
        db.collection("tanaman").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("MyTag", document.getId() + " => " + document.getData());
                                Tanaman tanaman = document.toObject(Tanaman.class);
                                System.out.println(tanaman.getIdTutorial());
                                tanaman.setIdTanaman(document.getId());
                                list.add(tanaman);
                            }
                            try {
                                showRecyclerList(list);
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("Main", "Error getting documents: ", task.getException());
                        }
                    }
                }
        );
    }

    private void showJumlahTanaman(FirebaseUser account) {
        db.collection("tanaman_user").whereEqualTo("uid", account.getUid()).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int jumlah = task.getResult().getDocuments().size();
                            setJumlahTanaman(String.valueOf(jumlah));
                        }
                    }
                }
        );
    }

    private void setJumlahTanaman(String jumlah) {
        tvJumlahTanaman.setText(jumlah);
    }

    private void setupNavDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        home = new Intent(context, MainActivity.class);
                        startActivity(home);
                        finish();
                        break;
                    case R.id.nav_my_plant:
                        myPlant = new Intent(MainActivity.this, TanamanKu.class);
                        startActivity(myPlant);
                        onBackPressed();
                        break;
                    case R.id.nav_hama:
                        hama = new Intent(MainActivity.this, ListPenyakit.class);
                        startActivity(hama);
                        onBackPressed();
                        break;
                    case R.id.nav_setting:
                        setting = new Intent(MainActivity.this, Setting.class);
                        startActivity(setting);
                        onBackPressed();
                        break;
                    case R.id.nav_contact_us:
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "codatescompany@gmail.com", null
                        ));
//                        intent.setType("text/plain");
//                        startActivity(Intent.createChooser(intent, "Kritik & Saran"));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"codatescompany@gmail.com"});
                        intent.putExtra(Intent.EXTRA_CC, new String[]{tvEmail.getText().toString()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Kritik & Saran");
                        intent.putExtra(Intent.EXTRA_TEXT, "");

                        try {
                            startActivity(Intent.createChooser(intent, "Ingin Mengirim Email ?"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case R.id.nav_logout:
                        firebaseAuth.signOut();
                        GoogleSignInOptions gso = new GoogleSignInOptions.
                                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                build();
                        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context, gso);
                        googleSignInClient.signOut();
                        LoginManager.getInstance().logOut();
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser account = firebaseAuth.getCurrentUser();
        showJumlahTanaman(account);
      }


    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseUser account = firebaseAuth.getCurrentUser();
        showJumlahTanaman(account);
       }


    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

//    private void showRecyclerList() {
//        TanamanAdapter tanamanAdapter = new TanamanAdapter(list);
//        rvTanaman.setAdapter(tanamanAdapter);
//        rvTanaman.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
//
//
//        tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
//            @Override
//            public void onItemClicked(Tanaman tanaman) {
//                Intent intent = new Intent(getApplicationContext(),DetailTanaman.class);
//                intent.putExtra(DetailTanaman.EXTRA_TANAMAN,tanaman);
//                startActivity(intent);
//            }
//        });
//    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser account = firebaseAuth.getCurrentUser();
        if (account != null) {
            tvName.setText(account.getDisplayName());
            tvEmail.setText(account.getEmail());
            if (account.getPhotoUrl() != null) {
                Glide.with(this).load(account.getPhotoUrl()).into(imgProfile);
            } else {
                imgProfile.setImageResource(R.mipmap.ic_logo);
            }
        } else {
            gotoLoginActivity();
        }

    }

    private void gotoLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void showRecyclerList(ArrayList<Tanaman> tanaman) {
        TanamanAdapter tanamanAdapter = new TanamanAdapter(tanaman);
        rvTanaman.setAdapter(tanamanAdapter);
        rvTanaman.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tanaman tanaman) {
                Intent intent = new Intent(getApplicationContext(), DetailTanaman.class);
                intent.putExtra(DetailTanaman.EXTRA_TANAMAN, tanaman);
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
