package com.codates.plantie.model;

import android.icu.util.LocaleData;

import com.google.firebase.firestore.DocumentReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TanamanUserData {

    public  static Hari getTugasHarian(){
        Hari hari = new Hari();
        ArrayList<DeskripsiHari> deskripsi = new ArrayList<DeskripsiHari>();
        deskripsi.add(new DeskripsiHari(
                "Menyiram Tanaman Pagi",false));
        deskripsi.add(new DeskripsiHari(
                "Menyiram Tanaman Sore",false));
        hari.setDeskripsi(deskripsi);
        return hari;
    }

    public static Hari getTugasHarianPupuk(){
        Hari hari = new Hari();
        ArrayList<DeskripsiHari> deskripsi = new ArrayList<DeskripsiHari>();
        deskripsi.add(new DeskripsiHari(
                "Menyiram Tanaman Pagi",false));
        deskripsi.add(new DeskripsiHari(
                "Memberi Pupuk pada Tanaman",false));
        deskripsi.add(new DeskripsiHari(
                "Menyiram Tanaman Sore",false));
        hari.setDeskripsi(deskripsi);
        return hari;
    }


    public static  Minggu getMinggu(ArrayList<Date> date){
        Minggu minggu = new Minggu();
        ArrayList<Hari> harian = new ArrayList<>();
        for(int i= 0 ; i < 7 ; i++ ){
            harian.add(getTugasHarian());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            harian.get(i).setDate(simpleDateFormat.format(date.get(i)));
            System.out.println(harian.get(i).getDate());
        }
        minggu.setSelesai(false);
        minggu.setHari(harian);
        return minggu;
    }

    public static  Minggu getMingguMemupuk(ArrayList<Date> date){
        Minggu minggu = new Minggu();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Hari> harian = new ArrayList<>();
        for(int i= 0 ; i < 7 ; i++ ){
            if(i==3){
                harian.add(getTugasHarianPupuk());
            }else{
                harian.add(getTugasHarian());
            }
            harian.get(i).setDate(simpleDateFormat.format(date.get(i)));
            System.out.println(harian.get(i).getDate());
        }
        minggu.setSelesai(false);
        minggu.setHari(harian);
        return minggu;
    }

    public static TanamanUser setTanamanUser(int jumlahMinggu, DocumentReference documentReference, String uid){
        TanamanUser tanamanUser = new TanamanUser();
        ArrayList<Minggu> mingguan = new ArrayList<>();
        int tempDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        ArrayList<Date> date = new ArrayList<>();
        for(int i = 0 ; i < jumlahMinggu;i++){
            for(int j = 0 ; j< 7;j++){
                if(date.isEmpty()){
                    calendar.add(Calendar.DAY_OF_MONTH,0);
                }else{
                    calendar.add(Calendar.DAY_OF_MONTH,1);
                }
                if(date.size() <= 7){
                    date.add(j,calendar.getTime());
                }else{
                    date.set(j,calendar.getTime());
                }
            }
           if(i == 0){
               mingguan.add(getMinggu(date));
           }else{
               mingguan.add(getMingguMemupuk(date));
           }
        }
        tanamanUser.setIdTanaman(documentReference);
        tanamanUser.setUid(uid);
        tanamanUser.setSelesai(false);
        tanamanUser.setMinggu(mingguan);
        return tanamanUser;
    }

}
