package com.codates.plantie.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codates.plantie.R;
import com.github.florent37.awesomebar.AwesomeBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Setting extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public String wpagi = "";
    public String wsore = "";

    private SharedPreferences sharedPreferences;

    public static final String EXTRA_POSISITION = "extra_posisition";
    protected Spinner spinnerPagi, spinnerSore;
    private static final String PAGI = "pagi";
    private static final String SORE = "sore";

    private static final String[] pagi = {"07.00", "08.00", "09.00"};
    private static final String[] sore = {"15.00", "16.00", "17.00"};
    protected ArrayAdapter<String> adapterPagi;
    protected ArrayAdapter<String> adapterSore;
    String posisiPagi, posisiSore;

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

        posisiPagi = getPreference(PAGI);
        posisiSore = getPreference(SORE);
        Waktupagi.setText(posisiPagi);
        Waktusore.setText(posisiSore);

        int position = getIntent().getIntExtra(EXTRA_POSISITION, 0);
        bar.getSettings().setAnimateMenu(false);
        bar.displayHomeAsUpEnabled(true);

        bar.setOnMenuClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Waktupagi.setText(getPreference(PAGI));
        Waktusore.setText(getPreference(SORE));
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
                        wpagi = "07.00";
                        setPreference(PAGI, wpagi);
                        Waktupagi.setText(getPreference(PAGI));
                        break;
                    case 1:
                        wpagi = "08.00";
                        setPreference(PAGI, wpagi);
                        Waktupagi.setText(getPreference(PAGI));
                        break;
                    case 2:
                        wpagi = "09.00";
                        setPreference(PAGI, wpagi);
                        Waktupagi.setText(getPreference(PAGI));
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

        final Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
        final Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);

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
                .content("Pengingat Menyiram" + "\r\n- Pagi : " + getPreference(PAGI) + "\r\n- Sore : " + getPreference(SORE) )
                .positiveText("OK")
                .icon(getResources().getDrawable(R.mipmap.ic_logo))
                .autoDismiss(true)
                .typeface(faceMed,face)
                .show();

        View positiveBtn = dialog.getActionButton(DialogAction.POSITIVE);
        positiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Setting.this, MainActivity.class);
                startActivity(home);
            }
        });
    }

    public void setPreference(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public String getPreference(String key) {
        return sharedPreferences.getString(key, "");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                wsore = "15.00";
                setPreference(SORE, wsore);
                Waktusore.setText(getPreference(SORE));
                break;
            case 1:
                wsore = "16.00";
                setPreference(SORE, wsore);
                Waktusore.setText(getPreference(SORE));
                break;
            case 2:
                wsore = "17.00";
                setPreference(SORE, wsore);
                Waktusore.setText(getPreference(SORE));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}