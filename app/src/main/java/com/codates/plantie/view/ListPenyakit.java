package com.codates.plantie.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codates.plantie.R;
//import com.codates.plantie.adapter.PenyakitAdapter;
import com.codates.plantie.adapter.PenyakitAdapter;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.model.Penyakit;
import com.codates.plantie.model.User;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListPenyakit extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private RecyclerView recyclerViewOne;
    private RecyclerView recyclerViewTwo;
    private ArrayList<Penyakit> listPenyakit = new ArrayList<>();
    private ArrayList<Penyakit> listPenyakit2 = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseFirestore db2 = FirebaseFirestore.getInstance();
    TextView tvName, tvEmail;
    ImageView imgProfile;
    Intent home, myPlant, setting, hama;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    Context context;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penyakit);
        context = this;

        AwesomeBar bar = findViewById(R.id.bar);

        bar.getSettings().setAnimateMenu(false);

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        bar.displayHomeAsUpEnabled(false);
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


        db.collection("penyakit").orderBy("jumlah_view", Query.Direction.DESCENDING).limit(10).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                try{
                                    Log.d("success", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                    Penyakit penyakit = documentSnapshot.toObject(Penyakit.class);
                                    penyakit.setIdPenyakit(documentSnapshot.getId());
                                    System.out.println(penyakit.getTitle());
                                    listPenyakit.add(penyakit);
                                    showRecyclerView(listPenyakit);
                                } catch(Exception ex){
                                    Log.d("error", ex.getMessage());

                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                        else {
                            Log.d("Main", "Error Getting Documents: ", task.getException());
                        }
                    }
                }
        );

        db2.collection("penyakit").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                try {
                                    Log.d("success", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                    Penyakit penyakit2 = documentSnapshot.toObject(Penyakit.class);
                                    penyakit2.setIdPenyakit(documentSnapshot.getId());
                                    System.out.println(penyakit2.getTitle());
                                    listPenyakit2.add(penyakit2);
                                    showRecyclerViewTwo(listPenyakit2);
                                } catch (Exception ex) {
                                    Log.d("error", ex.getMessage());

                                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Log.d("Main", "Error Getting Documents: ", task.getException());
                        }
                    }
                }
        );

        recyclerViewOne = findViewById(R.id.recycler_view_penyakit);
        System.out.println("list kosong " + listPenyakit.isEmpty());
        recyclerViewTwo = findViewById(R.id.recycler_view_penyakit_dua);
        System.out.println("list kosong " + listPenyakit2.isEmpty());
    }

    private void setupNavDrawer(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        home = new Intent(ListPenyakit.this, MainActivity.class);
                        startActivity(home);
                        onBackPressed();
                        break;
                    case R.id.nav_my_plant:
                        myPlant = new Intent(ListPenyakit.this, TanamanKu.class);
                        startActivity(myPlant);
                        onBackPressed();
                        break;
                    case R.id.nav_hama:
                        hama = new Intent(context, ListPenyakit.class);
                        startActivity(hama);
                        onBackPressed();
                        break;
                    case R.id.nav_setting:
                        setting = new Intent(ListPenyakit.this, Setting.class);
                        startActivity(setting);
                        onBackPressed();
                        break;
                    case R.id.nav_contact_us:
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", "codatescompany@gmail.com", null
                        ));
//                        intent.setType("text/plain");
//                        startActivity(Intent.createChooser(intent, "Kritik & Saran"));
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"codatescompany@gmail.com"});
                        intent.putExtra(Intent.EXTRA_CC, new String[] {tvEmail.getText().toString()});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Kritik & Saran");
                        intent.putExtra(Intent.EXTRA_TEXT, "");

                        try {
                            startActivity(Intent.createChooser(intent, "Ingin Mengirim Email ?"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            ex.printStackTrace();
                        }
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
//        drawer.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        if (drawer.isDrawerOpen(Gravity.START)){
            drawer.closeDrawers();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInResult result = User.setOptionalPendingResult(googleApiClient);
        if (result != null) {
            GoogleSignInAccount account = User.handleSignInResult(result);
            if (account != null) {
                tvName.setText(account.getDisplayName());
                tvEmail.setText(account.getEmail());
                if(account.getPhotoUrl() != null){
                    Glide.with(this).load(account.getPhotoUrl()).into(imgProfile);
                }else{
                    imgProfile.setImageResource(R.mipmap.ic_logo);
                }
            } else {
                gotoLoginActivity();
            }
        } else {
            gotoLoginActivity();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    private void gotoLoginActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void showRecyclerView(final ArrayList<Penyakit> penyakit){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        PenyakitAdapter penyakitAdapter = new PenyakitAdapter(penyakit);
        recyclerViewOne.setAdapter(penyakitAdapter);
        recyclerViewOne.setLayoutManager(llm);
        recyclerViewOne.setHasFixedSize(true);
        penyakitAdapter.setOnItemClickCallback(new PenyakitAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(final Penyakit penyakit) {
                int jumlah_view = penyakit.getJumlah_view() + 1;
                DocumentReference documentReference = db.collection("penyakit").document(penyakit.getIdPenyakit());
                documentReference.update("jumlah_view", jumlah_view).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("myTag", "Jumlah Views Bertambah");
                                Intent intent = new Intent(getApplicationContext(), DetailPenyakit.class);
                                intent.putExtra(DetailPenyakit.EXTRA_PENYAKIT, penyakit);
                                startActivity(intent);
                            }
                        }
                ).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("myTag", "ERROR UPDATING DATA");
                                e.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    private void showRecyclerViewTwo(ArrayList<Penyakit> penyakit){

        LinearLayoutManager llm2 = new LinearLayoutManager(this);
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        PenyakitAdapter penyakitAdapter = new PenyakitAdapter(penyakit);
        recyclerViewTwo.setAdapter(penyakitAdapter);
        recyclerViewTwo.setLayoutManager(llm2);

        penyakitAdapter.setOnItemClickCallback(new PenyakitAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(final Penyakit penyakit) {
                int jumlah_view = penyakit.getJumlah_view() + 1;
                DocumentReference documentReference = db.collection("penyakit").document(penyakit.getIdPenyakit());
                documentReference.update("jumlah_view", jumlah_view).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("myTag", "Jumlah Views Bertambah");
                                Intent intent = new Intent(getApplicationContext(), DetailPenyakit.class);
                                intent.putExtra(DetailPenyakit.EXTRA_PENYAKIT, penyakit);
                                startActivity(intent);
                            }
                        }
                ).addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("myTag", "ERROR UPDATING DATA");
                                e.printStackTrace();
                            }
                        }
                );
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
