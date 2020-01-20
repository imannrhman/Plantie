package com.codates.plantie.model;

import java.util.ArrayList;

public class DeskripsiHari {
    public String deskripsi;

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
        this.selesai = selesai;
    }

    public boolean selesai;

    public DeskripsiHari(String deskripsi,boolean selesai) {
        this.deskripsi = deskripsi;
        this.selesai = selesai;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }


}
