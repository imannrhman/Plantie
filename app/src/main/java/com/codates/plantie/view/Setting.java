package com.codates.plantie.view;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codates.plantie.R;
import com.codates.plantie.adapter.AlarmAdapter;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class Setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public int wpagi, wsore ;

    private SharedPreferences sharedPreferences;

    public static final String EXTRA_POSISITION = "extra_posisition";
    protected Spinner spinnerPagi, spinnerSore;
    private static final String PAGI = "pagi";
    private static final String SORE = "sore";

    private static final String[] pagi = {"07.00", "08.00", "09.00"};
    private static final String[] sore = {"15.00", "16.00", "17.00"};
    protected ArrayAdapter<String> adapterPagi;
    protected ArrayAdapter<String> adapterSore;
    String posisiPagi, posisiSore, menit, awalPagi, getPagi, getSore;

    TextView Waktupagi, Waktusore;
    FloatingActionButton btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("KEY", Context.MODE_PRIVATE);

        AwesomeBar bar = findViewById(R.id.bar);
        Waktupagi = findViewById(R.id.tv_waktu_2);
        Waktusore = findViewById(R.id.tv_waktu_1);
        menit = ".00"; awalPagi = "0";

        getPagi = String.valueOf(getPreference(PAGI));
        getSore = String.valueOf(getPreference(SORE));

        posisiPagi = awalPagi+getPreference(PAGI)+menit;
        posisiSore = getPreference(SORE)+menit;
        Waktupagi.setText(posisiPagi);
        Waktusore.setText(posisiSore);

        Waktupagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerPagi.performClick();
            }
        });

        Waktusore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerSore.performClick();
            }
        });

//        int position = getIntent().getIntExtra(EXTRA_POSISITION, 0);
        bar.getSettings().setAnimateMenu(false);
        bar.displayHomeAsUpEnabled(true);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        Waktupagi.setText(posisiPagi);
//        Waktusore.setText(posisiSore);
        spinnerPagi = findViewById(R.id.spinnerWaktuPagi);
        adapterPagi = new ArrayAdapter<>(Setting.this, android.R.layout.simple_spinner_item, pagi);
        adapterPagi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPagi.setAdapter(adapterPagi);
        spinnerPagi.setSelection(adapterPagi.getPosition(posisiPagi));
        spinnerPagi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        wpagi = 7;
                        setPreference(PAGI, wpagi);
                        String p = String.valueOf(getPreference(PAGI));
                        String p1 = awalPagi+p+menit;
                        Waktupagi.setText(p1);
                        break;
                    case 1:
                        wpagi = 8;
                        setPreference(PAGI, wpagi);
                        String pp = String.valueOf(getPreference(PAGI));
                        String p2 = awalPagi+pp+menit;
                        Waktupagi.setText(p2);
                        break;
                    case 2:
                        wpagi = 9;
                        setPreference(PAGI, wpagi);
                        String ppp = String.valueOf(getPreference(PAGI));
                        String p3 = awalPagi+ppp+menit;
                        Waktupagi.setText(p3);
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
//                Waktupagi.setText(getPreference(PAGI));
            }


        });

        // defaultnya posisi item pertama
        spinnerSore = findViewById(R.id.spinnerWaktuSore);
        adapterSore = new ArrayAdapter(Setting.this, android.R.layout.simple_spinner_item, sore);
        adapterSore.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSore.setAdapter(adapterSore);
        spinnerSore.setSelection(adapterSore.getPosition(posisiSore));
        spinnerSore.setOnItemSelectedListener(this);

//        final Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
//        final Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);

        btnSave = findViewById(R.id.btnSetting);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });

    }

    public void click(){
        final Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
        final Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);

        setPreference(PAGI, wpagi);
        setPreference(SORE, wsore);

        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .title("PENGATURAN")
                .content("Pengingat Menyiram" + "\r\n- Pagi : " +awalPagi+ getPreference(PAGI) + ".00\r\n- Sore : " + getPreference(SORE) + menit )
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .positiveText("OK")
                .icon(getResources().getDrawable(R.mipmap.ic_logo))
                .autoDismiss(true)
                .typeface(faceMed,face)
                .show();

        View positiveBtn = dialog.getActionButton(DialogAction.POSITIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent home = new Intent(Setting.this, MainActivity.class);
//                startActivity(home);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pengingatSore(calendarSore().getTimeInMillis(), calendarSore());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pengingatPagi(calendarPagi().getTimeInMillis(), calendarPagi());
                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void pengingatSore(long timeInMillis, Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Setting.this, AlarmAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Setting.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        assert alarmManager != null;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

//       ini percobaan
//        int hour = c.get(Calendar.HOUR);
//        int date = c.get(Calendar.DATE);
//        int mth = c.get(Calendar.MONTH);
//        int yer = c.get(Calendar.YEAR);
//        int min = c.get(Calendar.MINUTE);
//
//        Calendar cal = Calendar.getInstance();
//        int jam = cal.get(Calendar.HOUR);
//        int tgl = cal.get(Calendar.DATE);
//        int bln = cal.get(Calendar.MONTH);
//        int thn = cal.get(Calendar.YEAR);
//        int mnt = cal.get(Calendar.MINUTE);
//
//
//        final Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
//        final Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);
//        MaterialDialog dialog = new MaterialDialog.Builder(this)
//                .title("PENGATURAN Sore")
//                .content("c : " +hour+"."+min+" "+date+" "+mth+" "+yer+
//                        "\ncal : "+jam+"."+mnt+" "+tgl+" "+bln+" "+thn)
//                .positiveText("OK")
//                .icon(getResources().getDrawable(R.mipmap.ic_logo))
//                .autoDismiss(true)
//                .typeface(faceMed,face)
//                .show();
//
//        View positiveBtn = dialog.getActionButton(DialogAction.POSITIVE);
//        positiveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent home = new Intent(Setting.this, MainActivity.class);
//                startActivity(home);
//            }
//        });
//        sini-sini
//        Toast.makeText(this, String.valueOf(timeInMillis), Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void pengingatPagi(long timeInMillis, Calendar c){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Setting.this, AlarmAdapter.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Setting.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        assert alarmManager != null;
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

//        Toast.makeText(this, "JAM "+hour+" ."+mnt+" "+tgl+" "+bln+" "+thn, Toast.LENGTH_LONG).show();
    }

    public Calendar calendarSore(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, wsore);
        calendar.set(Calendar.MINUTE, 0);

        int hour = calendar.get(Calendar.HOUR);
        int date = calendar.get(Calendar.DATE);
        int mth = calendar.get(Calendar.MONTH);
        int yer = calendar.get(Calendar.YEAR);
        int min = calendar.get(Calendar.MINUTE);

        Calendar cal = Calendar.getInstance();
        int jam = cal.get(Calendar.HOUR);
        int tgl = cal.get(Calendar.DATE);
        int bln = cal.get(Calendar.MONTH);
        int thn = cal.get(Calendar.YEAR);
        int mnt = cal.get(Calendar.MINUTE);

        int tgl1 = tgl + 1;

        if (hour < jam || hour == jam && date == tgl && mth == bln && yer == thn && min < mnt || min == mnt){
            calendar.set(Calendar.DATE, tgl1);
        }

        return calendar;
    }

    public Calendar calendarPagi(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, wpagi);
        calendar.set(Calendar.MINUTE, 0);

        int hour = calendar.get(Calendar.HOUR);
        int date = calendar.get(Calendar.DATE);
        int mth = calendar.get(Calendar.MONTH);
        int yer = calendar.get(Calendar.YEAR);
        int min = calendar.get(Calendar.MINUTE);

        Calendar cal = Calendar.getInstance();
        int jam = cal.get(Calendar.HOUR);
        int tgl = cal.get(Calendar.DATE);
        int bln = cal.get(Calendar.MONTH);
        int thn = cal.get(Calendar.YEAR);
        int mnt = cal.get(Calendar.MINUTE);

        int tgl1 = tgl + 1;

        if (hour < jam || hour == jam && date == tgl && mth == bln && yer == thn && min < mnt || min == mnt){
            calendar.set(Calendar.DATE, tgl1);
        }

        return calendar;
    }

    public void setPreference(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
        editor.commit();
    }

    public int getPreference(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                wsore = 15;
                setPreference(SORE, wsore);
                String s = String.valueOf(getPreference(SORE));
                String s1 = s+menit;
                Waktusore.setText(s1);
                break;
            case 1:
                wsore = 16;
                setPreference(SORE, wsore);
                String ss = String.valueOf(getPreference(SORE));
                String s2 = ss+menit;
                Waktusore.setText(s2);
                break;
            case 2:
                wsore = 17;
                setPreference(SORE, wsore);
                String sss = String.valueOf(getPreference(SORE));
                String s3 = sss+menit;
                Waktusore.setText(s3);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}