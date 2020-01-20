package com.codates.plantie.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.codates.plantie.R;
import com.codates.plantie.adapter.PenyakitAdapter;
import com.codates.plantie.model.Penyakit;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ListPenyakit extends AppCompatActivity {

    private RecyclerView recyclerViewOne;
    private ArrayList<Penyakit> listPenyakit = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_penyakit);
        db.collection("penyakit").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Log.d("MyTag", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                                Penyakit penyakit = documentSnapshot.toObject(Penyakit.class);
                                System.out.println(penyakit.getTitle());
                                listPenyakit.add(penyakit);
                            }
                            try{
                                showRecyclerView(listPenyakit);
                            } catch (Exception ex){
                                Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Log.d("Main", "Error Getting Documents: ", task.getException());
                        }
                    }
                }
        );

        recyclerViewOne = findViewById(R.id.recycler_view);
        System.out.println("list kosong" + listPenyakit.isEmpty());
    }

    private void showRecyclerView(ArrayList<Penyakit> penyakit){
        PenyakitAdapter penyakitAdapter = new PenyakitAdapter(penyakit);
//        recyclerViewOne.setAdapter(penyakitAdapter);
        recyclerViewOne.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOne.setHasFixedSize(true);
    }

}
