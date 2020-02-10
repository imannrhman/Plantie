package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MingguTemp implements Parcelable {
    public static final Parcelable.Creator<MingguTemp> CREATOR = new Parcelable.Creator<MingguTemp>() {
        @Override
        public MingguTemp createFromParcel(Parcel source) {
            return new MingguTemp(source);
        }

        @Override
        public MingguTemp[] newArray(int size) {
            return new MingguTemp[size];
        }
    };
    private ArrayList<Minggu> tempListMinggu;
    private int position;

    public MingguTemp() {
    }

    protected MingguTemp(Parcel in) {
        this.tempListMinggu = in.createTypedArrayList(Minggu.CREATOR);
        this.position = in.readInt();
    }

    public ArrayList<Minggu> getTempListMinggu() {
        return tempListMinggu;
    }

    public void setTempListMinggu(ArrayList<Minggu> tempListMinggu) {
        this.tempListMinggu = tempListMinggu;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.tempListMinggu);
        dest.writeInt(this.position);
    }
}
