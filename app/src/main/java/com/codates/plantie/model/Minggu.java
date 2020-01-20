package com.codates.plantie.model;

import java.util.ArrayList;

public class Minggu {

    ArrayList<Hari> hari;
    boolean selesai;

    public ArrayList<Hari> getHari() {
        return hari;
    }

    public void setHari(ArrayList<Hari> hari) {
        this.hari = hari;
    }

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }
}
