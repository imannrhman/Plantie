package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;

public class Tanaman implements Parcelable {
    String namaTanaman;
    String minggu;
    String gambar;
    DocumentReference idTutorial;

    public DocumentReference getIdTutorial() {
        return idTutorial;
    }

    public void setIdTutorial(DocumentReference idTutorial) {
        this.idTutorial = idTutorial;
    }


    public String getNamaTanaman() {
        return namaTanaman;
    }

    public void setNamaTanaman(String namaTanaman) {
        this.namaTanaman = namaTanaman;
    }

    public String getMinggu() {
        return minggu;
    }

    public void setMinggu(String minggu) {
        this.minggu = minggu;
    }

    public String getGambar() {
        return gambar;
    }



    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaTanaman);
        dest.writeString(this.minggu);
        dest.writeString(this.gambar);
    }

    public Tanaman() {

    }

    protected Tanaman(Parcel in) {
        this.namaTanaman = in.readString();
        this.minggu = in.readString();
        this.gambar = in.readString();
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
