package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Deskripsi implements Parcelable {
    public static final Parcelable.Creator<Deskripsi> CREATOR = new Parcelable.Creator<Deskripsi>() {
        @Override
        public Deskripsi createFromParcel(Parcel source) {
            return new Deskripsi(source);
        }

        @Override
        public Deskripsi[] newArray(int size) {
            return new Deskripsi[size];
        }
    };
    public String deskripsi;
    public boolean selesai;

    public Deskripsi(String deskripsi, boolean selesai) {
        this.deskripsi = deskripsi;
        this.selesai = selesai;
    }

    protected Deskripsi(Parcel in) {
        this.deskripsi = in.readString();
        this.selesai = in.readByte() != 0;
    }

    public boolean isSelesai() {
        return selesai;
    }

    public void setSelesai(boolean selesai) {
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
}
