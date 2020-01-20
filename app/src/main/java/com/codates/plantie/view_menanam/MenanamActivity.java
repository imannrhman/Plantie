package com.codates.plantie.view_menanam;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codates.plantie.R;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.view.DetailTanaman;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codates.plantie.view_menanam.ui.main.SectionsPagerAdapter;

public class MenanamActivity extends AppCompatActivity {
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menanam);
        Tanaman tanaman = getIntent().getParcelableExtra(EXTRA_POSITION);
        assert tanaman != null;
        final ViewPager viewPager = findViewById(R.id.view_pager);
        Toast.makeText(getApplicationContext(),tanaman.getNamaTanaman(),Toast.LENGTH_LONG).show();
        final TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        ImageView Arrowback = findViewById(R.id.ArrowBack);
        Arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager(),tanaman);
        viewPager.setAdapter(sectionsPagerAdapter);
        FloatingActionButton fab = findViewById(R.id.btnchecklistTutorial);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detail = new Intent(MenanamActivity.this, DetailTanaman.class);
                startActivity(detail);
//                Toast.makeText(getApplicationContext(), "ada", Toast.LENGTH_LONG).show();
                // set DB buat visibility
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        infoDialog();
    }

    public void infoDialog(){
        Typeface faceMed = ResourcesCompat.getFont(this, R.font.montserratsemibold);
        Typeface face = ResourcesCompat.getFont(this, R.font.montserrat);
        final MaterialDialog infoDialog = new MaterialDialog.Builder(this)
                .title("INFO")
                .content("1. Ikuti instruksi mulai dari alat bahan & cara menanam agar menghasilkan tanaman yang berkualitas" +
                        "\r\n\n2. Klik tombol ceklis jika instruksi sudah dilakukan" )
                .positiveText("OK")
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .icon(getResources().getDrawable(R.mipmap.ic_logo))
                .typeface(faceMed,face)
                .autoDismiss(true)
                .show();

        View submitButton = infoDialog.getActionButton(DialogAction.POSITIVE);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDialog.dismiss();
            }
        });
    }

}