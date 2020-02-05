package com.codates.plantie.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codates.plantie.R;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.model.Tutorial;
import com.github.florent37.awesomebar.ActionItem;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import id.arieridwan.lib.PageLoader;

public class ListTanaman extends AppCompatActivity {
    private RecyclerView rvTanaman;
    boolean muncul = true;
    private ArrayList<Tanaman> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanaman);
      
        final AwesomeBar bar = findViewById(R.id.bar);
        final PageLoader pageLoader = findViewById(R.id.progress_bar);
        setPageLoader(pageLoader);
        db.collection("tanaman").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            pageLoader.stopProgress();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("MyTag", document.getId() + " => " + document.getData());
                                Tanaman tanaman = new Tanaman();
                                tanaman.setIdTanaman(document.getId());
                                tanaman.setGambar((String) document.getData().get("gambar"));
                                tanaman.setIdTutorial((DocumentReference) document.getData().get("idTutorial"));
                                tanaman.setMinggu((String) document.getData().get("minggu"));
                                tanaman.setNamaTanaman((String) document.getData().get("namaTanaman"));
                                System.out.println(tanaman.getIdTutorial());
                                list.add(tanaman);
                            }
                            try {
                                showRecyclerList(list);
//                                bar.addAction(R.drawable.ic_search_black_24dp, "Cari");
                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Log.d("Main", "Error getting documents: ", task.getException());
                        }
                    }
                }
        );


        final ImageView search = findViewById(R.id.searchbar);

        bar.getSettings().setAnimateMenu(false);
        bar.displayHomeAsUpEnabled(true);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        bar.setActionItemClickListener(new AwesomeBar.ActionItemClickListener() {
//            @Override
//            public void onActionItemClicked(int position, ActionItem actionItem) {
//                if (muncul) {
//                    search.setVisibility(View.VISIBLE);
//                    muncul = false;
//                } else {
//                    search.setVisibility(View.INVISIBLE);
//                    muncul = true;
//                }
//
//            }
//        });

        rvTanaman = findViewById(R.id.recycler_view);
        System.out.println("list kosong" + list.isEmpty());

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
        tanamanAdapter.setOnItemClickCallback(new TanamanAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Tanaman tanaman) {
                Intent intent = new Intent(getApplicationContext(), DetailTanaman.class);
                intent.putExtra(DetailTanaman.EXTRA_TANAMAN, tanaman);
                startActivity(intent);
            }
        });

    }
}
