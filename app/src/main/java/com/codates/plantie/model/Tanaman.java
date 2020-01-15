package com.codates.plantie.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class Tanaman implements Parcelable {
    String namaTanaman;
    String minggu;
    String gambar;
    Tutorial idTutorial;

    public Tutorial getIdTutorial() {
        return idTutorial;
    }

    public void setIdTutorial(DocumentReference tutorial) {
        tutorial.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    idTutorial = task.getResult().toObject(Tutorial.class);
                }
            }
        });
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaTanaman);
        dest.writeString(this.minggu);
        dest.writeString(this.gambar);
        dest.writeParcelable(this.idTutorial, flags);
    }

    public Tanaman() {
    }

    protected Tanaman(Parcel in) {
        this.namaTanaman = in.readString();
        this.minggu = in.readString();
        this.gambar = in.readString();
        this.idTutorial = in.readParcelable(Tutorial.class.getClassLoader());
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
