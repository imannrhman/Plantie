package com.codates.plantie.view_menanam.ui.main;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.codates.plantie.R;
import com.codates.plantie.model.Tanaman;
import com.codates.plantie.model.Tutorial;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private final Tanaman tanaman;
    Tutorial tutorials;

    public SectionsPagerAdapter(Context context, FragmentManager fm, Tanaman tanaman) {
        super(fm);
        mContext = context;
        this.tanaman = tanaman;
    }

    @Override
    public Fragment getItem(final int position) {
        Tutorial tutorial = tanaman.getIdTutorial();
        if (position == 0) {
            int gambar = R.drawable.ic_wheelbarrow;
            String text = "Alat & Bahan";
            return PlaceholderFragment.newInstance(position, tutorial.getBahan(), gambar, text);
        } else {
            String text = "Cara Menanam";
            int gambar = R.drawable.ic_flower_pot;
            return PlaceholderFragment.newInstance(position, tutorial.getCara(), gambar, text);
        }
    }

    private void getDataTutorial(final DocumentReference tutorial) {
        tutorial.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    try {
                        tutorials = task.getResult().toObject(Tutorial.class);
                    } catch (Exception e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}