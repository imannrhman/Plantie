package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DeskripsiHari implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deskripsi);
        dest.writeByte(this.selesai ? (byte) 1 : (byte) 0);
    }

    protected DeskripsiHari(Parcel in) {
        this.deskripsi = in.readString();
        this.selesai = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DeskripsiHari> CREATOR = new Parcelable.Creator<DeskripsiHari>() {
        @Override
        public DeskripsiHari createFromParcel(Parcel source) {
            return new DeskripsiHari(source);
        }

        @Override
        public DeskripsiHari[] newArray(int size) {
            return new DeskripsiHari[size];
        }
    };
}
