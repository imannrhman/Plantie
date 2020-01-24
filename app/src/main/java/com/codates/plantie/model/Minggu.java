package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Minggu implements Parcelable {

    public ArrayList<Hari> hari;
    public boolean selesai;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.hari);
        dest.writeByte(this.selesai ? (byte) 1 : (byte) 0);
    }

    public Minggu() {
    }

    protected Minggu(Parcel in) {
        this.hari = in.createTypedArrayList(Hari.CREATOR);
        this.selesai = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Minggu> CREATOR = new Parcelable.Creator<Minggu>() {
        @Override
        public Minggu createFromParcel(Parcel source) {
            return new Minggu(source);
        }

        @Override
        public Minggu[] newArray(int size) {
            return new Minggu[size];
        }
    };
}
