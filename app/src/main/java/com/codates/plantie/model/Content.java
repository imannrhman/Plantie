package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Content implements Parcelable {

    String deskripsi;
    String solusi;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }

    public Content(){

    }

    protected Content(Parcel in){
        this.deskripsi = in.readString();
        this.solusi = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deskripsi);
        dest.writeString(solusi);
    }

    public static final Parcelable.Creator<Content> CREATOR = new Parcelable.Creator<Content>() {
        @Override
        public Content createFromParcel(Parcel source) {
            return new Content(source);
        }

        @Override
        public Content[] newArray(int size) {
            return new Content[size];
        }
    };

}
