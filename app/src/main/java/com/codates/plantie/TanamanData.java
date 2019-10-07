package com.codates.plantie;

import android.util.Log;

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

    public static ArrayList<Tanaman> getListData(){
        ArrayList<Tanaman> list = new ArrayList<>();
        int[] gambar = {R.drawable.gambar,R.drawable.bawang_daun,R.drawable.buah_tomat,R.drawable.jahe,R.drawable.daun_seledri};
        for (String[] aData : data){
            Tanaman tanaman = new Tanaman();
            tanaman.setNamaTanaman(aData[0]);
            tanaman.setHari(aData[1]);
            tanaman.setGambar(Integer.parseInt(aData[2]));

            list.add(tanaman);
        }

        return list;
    }
}

