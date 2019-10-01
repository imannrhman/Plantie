package com.codates.plantie;

import android.os.Parcel;
import android.os.Parcelable;

public class Tanaman implements Parcelable {
    String namaTanaman;
    String hari;
    int gambar;

    public String getNamaTanaman() {
        return namaTanaman;
    }

    public void setNamaTanaman(String namaTanaman) {
        this.namaTanaman = namaTanaman;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public int getGambar() {
        return gambar;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaTanaman);
        dest.writeString(this.hari);
        dest.writeInt(this.gambar);
    }

    public Tanaman() {
    }

    protected Tanaman(Parcel in) {
        this.namaTanaman = in.readString();
        this.hari = in.readString();
        this.gambar = in.readInt();
    }

    public static final Parcelable.Creator<Tanaman> CREATOR = new Parcelable.Creator<Tanaman>() {
        @Override
        public Tanaman createFromParcel(Parcel source) {
            return new Tanaman(source);
        }

        @Override
        public Tanaman[] newArray(int size) {
            return new Tanaman[size];
        }
    };
}
