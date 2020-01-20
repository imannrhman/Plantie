package com.codates.plantie.model;

import com.google.firebase.firestore.DocumentReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class TanamanUser {
    String uid;
    DocumentReference idTanaman;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public DocumentReference getIdTanaman() {
        return idTanaman;
    }

    public void setIdTanaman(DocumentReference idTanaman) {
        this.idTanaman = idTanaman;
    }

    public ArrayList<Minggu> getMinggu() {
        return minggu;
    }

    public void setMinggu(ArrayList<Minggu> minggu) {
        this.minggu = minggu;
    }

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }

    ArrayList<Minggu> minggu;
    boolean selesai;



}
