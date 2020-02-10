package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Tutorial implements Parcelable {

    public static final Parcelable.Creator<Tutorial> CREATOR = new Parcelable.Creator<Tutorial>() {
        @Override
        public Tutorial createFromParcel(Parcel source) {
            return new Tutorial(source);
        }

        @Override
        public Tutorial[] newArray(int size) {
            return new Tutorial[size];
        }
    };
    ArrayList<String> bahan;
    ArrayList<String> cara;

    public Tutorial() {
    }

    protected Tutorial(Parcel in) {
        this.bahan = in.createStringArrayList();
        this.cara = in.createStringArrayList();
    }

    public ArrayList<String> getBahan() {
        return bahan;
    }

    public void setBahan(ArrayList<String> bahan) {
        this.bahan = bahan;
    }

    public ArrayList<String> getCara() {
        return cara;
    }

    public void setCara(ArrayList<String> cara) {
        this.cara = cara;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.bahan);
        dest.writeStringList(this.cara);
    }
}
