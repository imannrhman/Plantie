package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

public class Hari implements Parcelable {
    public ArrayList<DeskripsiHari> deskripsi;

    public ArrayList<DeskripsiHari> getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(ArrayList<DeskripsiHari> deskripsi) {
        this.deskripsi = deskripsi;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.deskripsi);
    }

    public Hari() {
    }

    protected Hari(Parcel in) {
        this.deskripsi = new ArrayList<DeskripsiHari>();
        in.readList(this.deskripsi, DeskripsiHari.class.getClassLoader());
    }

    public static final Parcelable.Creator<Hari> CREATOR = new Parcelable.Creator<Hari>() {
        @Override
        public Hari createFromParcel(Parcel source) {
            return new Hari(source);
        }

        @Override
        public Hari[] newArray(int size) {
            return new Hari[size];
        }
    };
}
