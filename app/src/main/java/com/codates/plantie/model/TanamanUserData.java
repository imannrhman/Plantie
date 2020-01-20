package com.codates.plantie.model;

import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class TanamanUserData {

    public  static Hari getTugasHarian(){
        Hari hari = new Hari();
        ArrayList<DeskripsiHari> deskripsi = new ArrayList<DeskripsiHari>();
        deskripsi.add(new DeskripsiHari(
                "Menyiram Tanaman Pagi",false));
        deskripsi.add(new DeskripsiHari(
                "Menyiram Tanaman Sore",false));
        hari.setDeskripsi(deskripsi);
        return hari;
    }

    public static  Minggu getMinggu(){
        Minggu minggu = new Minggu();
        ArrayList<Hari> harian = new ArrayList<>();
        for(int i= 0 ; i < 7 ; i++ ){
            harian.add(getTugasHarian());
        }
        minggu.setSelesai(false);
        minggu.setHari(harian);
        return minggu;
    }

    public static TanamanUser setTanamanUser(int jumlahMinggu, DocumentReference documentReference, String uid){
        TanamanUser tanamanUser = new TanamanUser();
        ArrayList<Minggu> mingguan = new ArrayList<>();
        for(int i = 0 ; i < jumlahMinggu;i++){
            mingguan.add(getMinggu());
        }
        tanamanUser.setIdTanaman(documentReference);
        tanamanUser.setUid(uid);
        tanamanUser.setSelesai(false);
        tanamanUser.setMinggu(mingguan);
        return tanamanUser;
    }

}
