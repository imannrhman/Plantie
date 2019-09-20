package com.codates.plantie.view;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.codates.plantie.R;
import com.manuelpeinado.fadingactionbar.FadingActionBarHelper;
public class DetailTanaman extends AppCompatActivity {
    public static final String GAMBAR= "gambar";
    public static final String HARI = "hari";
    public static final String NAMA_TANAMAN = "namatanaman";
    ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tanaman);
        gambar = findViewById(R.id.img_header);

         }
}
