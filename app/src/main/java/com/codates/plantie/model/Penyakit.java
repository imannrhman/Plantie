package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class Penyakit implements Parcelable {

    String title;
    String gambar_tanaman;
    String jenis_penyakit;
    String level;
    int jumlah_view;
    Content content;
    ArrayList<DocumentReference> jenis_tanaman;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGambar_tanaman() {
        return gambar_tanaman;
    }

    public void setGambar_tanaman(String gambar_tanaman) {
        this.gambar_tanaman = gambar_tanaman;
    }

    public String getJenis_penyakit() {
        return jenis_penyakit;
    }

    public void setJenis_penyakit(String jenis_penyakit) {
        this.jenis_penyakit = jenis_penyakit;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getJumlah_view() {
        return jumlah_view;
    }

    public void setJumlah_view(int jumlah_view) {
        this.jumlah_view = jumlah_view;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public ArrayList<DocumentReference> getJenis_tanaman() {
        return jenis_tanaman;
    }

    public void setJenis_tanaman(ArrayList<DocumentReference> jenis_tanaman) {
        this.jenis_tanaman = jenis_tanaman;
    }

    protected Penyakit(Parcel in){
        this.title = in.readString();
        this.gambar_tanaman = in.readString();
        this.jenis_penyakit = in.readString();
        this.level = in.readString();
        this.jumlah_view = in.readInt();
        this.content = in.readParcelable(Content.class.getClassLoader());
        this.jenis_tanaman = new ArrayList<DocumentReference>();
        in.readList(this.jenis_tanaman, DocumentReference.class.getClassLoader());

    }

    public Penyakit() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(this.gambar_tanaman);
        dest.writeString(this.jenis_penyakit);
        dest.writeString(this.level);
        dest.writeInt(this.jumlah_view);
        dest.writeParcelable(this.content, flags);
        dest.writeList(this.jenis_tanaman);

    }

    public static final Parcelable.Creator<Penyakit> CREATOR = new Parcelable.Creator<Penyakit>(){
        @Override
        public Penyakit createFromParcel(Parcel source) {
            return new Penyakit(source);
        }

        @Override
        public Penyakit[] newArray(int size) {
            return new Penyakit[size];
        }
    };

}
