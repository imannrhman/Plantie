package com.codates.plantie.view_menanam.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public PlaceholderFragment(ArrayList<String> list) {
        this.list = list;
    }

    public static PlaceholderFragment newInstance(int index,ArrayList<String> list) {
        PlaceholderFragment fragment = new PlaceholderFragment(list);
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
        final TextView textView = root.findViewById(R.id.section_label);
        rvTutorial = root.findViewById(R.id.rv_tutorial);
        showRecyclerList(list);
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