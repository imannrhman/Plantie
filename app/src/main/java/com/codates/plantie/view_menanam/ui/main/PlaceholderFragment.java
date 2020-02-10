package com.codates.plantie.view_menanam.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codates.plantie.R;
import com.codates.plantie.adapter.TanamanAdapter;
import com.codates.plantie.adapter.TutorialAdapter;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.view.DetailTanaman;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    int index;
    private PageViewModel pageViewModel;
    RecyclerView rvTutorial;
    ArrayList<String> list;
    String text;
    int gambar;
    public PlaceholderFragment(ArrayList<String> list,int gambar,String text) {
        this.list = list;
        this.gambar = gambar;
        this.text = text;
    }

    public static PlaceholderFragment newInstance(int index,ArrayList<String> list,int gambar ,String text) {
        PlaceholderFragment fragment = new PlaceholderFragment(list,gambar,text);
        Bundle bundle = new Bundle();

        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menanam, container, false);
        rvTutorial = root.findViewById(R.id.rv_tutorial);
        ImageView imageView = root.findViewById(R.id.img_alat);
        TextView textViewAlat = root.findViewById(R.id.textview_alat);
        textViewAlat.setText(text);
        imageView.setImageResource(gambar);

        final TextView tokped = root.findViewById(R.id.link_tokped);
        tokped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.tokopedia.com/p/pertukangan/alat-perkebunan";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        if(list != null){
            showRecyclerList(list);
        }
        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }


    private void showRecyclerList(ArrayList<String> tutorial) {
        TutorialAdapter tutorialAdapter = new TutorialAdapter(tutorial);
        rvTutorial.setAdapter(tutorialAdapter);
        rvTutorial.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTutorial.setHasFixedSize(true);
    }
}