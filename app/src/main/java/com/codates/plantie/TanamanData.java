package com.codates.plantie;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TanamanData {
    public static String[][] data = new String[][]{
            {"Tanaman Cabe Rawit",
                    "30hari",
                    String.valueOf(R.drawable.gambar),
            },
            {"Tanaman Bawang Daun",
                    "20hari",
                    String.valueOf(R.drawable.bawang_daun),
            },
            {"Tanaman Tomat",
                    "10hari",
                    String.valueOf(R.drawable.buah_tomat),
            },
            {"Tanaman Jahe",
                    "40hari",
                    String.valueOf(R.drawable.jahe),
            },
            {"Tanaman Sledri",
                    "20hari",
                    String.valueOf(R.drawable.daun_seledri)
            }


    };

    public static ArrayList<Tanaman> getListData() {
        final ArrayList<Tanaman> list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> querySnapshot = db.collection("tanaman").get();
        querySnapshot.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

            }
        });
        return list;
    }
}

