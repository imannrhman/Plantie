package com.codates.plantie.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.codates.plantie.R;
import com.codates.plantie.adapter.MingguAdapter;
import com.codates.plantie.model.DeskripsiHari;
import com.codates.plantie.model.Minggu;
import com.codates.plantie.model.MingguTemp;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.model.Tutorial;
import com.codates.plantie.view_menanam.MenanamActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class DetailTanaman extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static final String EXTRA_TANAMAN = "extra_tanaman";
    ImageView gambar;
    public RecyclerView rvMinggu;
    RelativeLayout rlCaraMenanam;
    Toolbar toolbar;
    boolean lean;
    CollapsingToolbarLayout collapsingToolbarLayout;
    TextView tvPengerjaan, tvTugasHarian;
    Tanaman tanaman;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);
        tvPengerjaan = findViewById(R.id.tv_pengerjaan);
        toolbar = findViewById(R.id.toolbar);

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
        tvTugasHarian = findViewById(R.id.tv_tugas_harian);
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

        collapsingToolbarLayout.setTitle(tanaman.getNamaTanaman());
        Glide.with(this).load(tanaman.getGambar())
                .into(gambar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_delete).setVisible(false);
        if (lean == true) {
            menu.findItem(R.id.action_delete).setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showTanamanUser();
    }

    private void showTanamanUser() {
        DocumentReference reference = db.collection("tanaman").document(tanaman.getIdTanaman());
        FirebaseUser account = firebaseAuth.getCurrentUser();
        assert account != null;
        db.collection("tanaman_user").whereEqualTo("uid", account.getUid()).whereEqualTo("idTanaman", reference).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                    int jumlahSemuaCeklis = 0;
                    int jumlahCeklis = 0;
                    for (int i = 0; i < listMinggu.size(); i++) {
                        for (int j = 0; j < listMinggu.get(i).getHari().size(); j++) {
                            for (int k = 0; k < listMinggu.get(i).getHari().get(j).getDeskripsi().size(); k++) {
                                DeskripsiHari deskripsiHari = listMinggu.get(i).getHari().get(j).getDeskripsi().get(k);
                                jumlahSemuaCeklis += 1;
                                if (deskripsiHari.isSelesai()) {
                                    jumlahCeklis += 1;
                                }
                            }
                        }
                    }
                    double task = Double.valueOf(jumlahCeklis);
                    double alltask = Double.valueOf(jumlahSemuaCeklis);
                    double presentase = task / alltask * 100;
                    DecimalFormat decimalFormat = new DecimalFormat("#.##");
                    String progress = decimalFormat.format(presentase) + " %";
                    String ceklis = jumlahCeklis + "/" + jumlahSemuaCeklis;
                    tvPengerjaan.setText(progress);
                    tvTugasHarian.setText(ceklis);
                    showRecyclerList(listMinggu, tanamanUserId);
                }
            }
        });
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
        lean = true;
        invalidateOptionsMenu();
        MingguAdapter mingguAdapter = new MingguAdapter(listMinggu);
        rvMinggu.setAdapter(mingguAdapter);
        rvMinggu.setLayoutManager(new GridLayoutManager(this, 3));
        mingguAdapter.setOnItemClickCallback(new MingguAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int position) {
                Intent intent = new Intent(getApplicationContext(), Laporan.class);
                MingguTemp mingguTemp = new MingguTemp();
                mingguTemp.setTempListMinggu(listMinggu);
                mingguTemp.setPosition(position);
                intent.putExtra(Laporan.EXTRA_POSITION, listMinggu.get(position));
                intent.putExtra(Laporan.EXTRA_DATA, tanamanUserId);
                intent.putExtra(Laporan.EXTRA_TEMP, mingguTemp);
                startActivity(intent);
            }
        });

    }

    public void hapus() {
        DocumentReference reference = db.collection("tanaman").document(tanaman.getIdTanaman());
        FirebaseUser account = firebaseAuth.getCurrentUser();
        assert account != null;
        db.collection("tanaman_user").whereEqualTo("uid", account.getUid()).whereEqualTo("idTanaman", reference).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onSuccess(QuerySnapshot query) {
                if (!query.isEmpty()) {
                    db.collection("tanaman_user").document(query.getDocuments().get(0).getId()).delete();
                    Toast.makeText(getApplicationContext(), "terhapus", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Tdk Terhapus", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_delete:
                showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDialog(){
        final Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
        final Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("INFO")
                .content("Anda Ingin Mengakhiri Penanaman "+tanaman.getNamaTanaman()+" ?")
                .cancelable(true)
                .canceledOnTouchOutside(true)
                .positiveText("YA")
                .negativeText("TIDAK")
                .icon(getResources().getDrawable(R.mipmap.ic_logo))
                .autoDismiss(true)
                .typeface(faceMed,face)
                .show();

        View positiveBtn = dialog.getActionButton(DialogAction.POSITIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapus();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
