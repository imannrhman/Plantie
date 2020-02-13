package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Hari implements Parcelable {
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
    public ArrayList<Deskripsi> deskripsi;
    public String date;

    public Hari() {
    }

    protected Hari(Parcel in) {
        this.deskripsi = in.createTypedArrayList(Deskripsi.CREATOR);
        this.date = in.readString();
    }

    public ArrayList<Deskripsi> getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(ArrayList<Deskripsi> deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.deskripsi);
        dest.writeString(this.date);
    }
}
