package com.codates.plantie.view.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();


    private LiveData<Integer> positon = Transformations.map(mIndex, new Function<Integer, Integer>() {

        @Override
        public Integer apply(Integer input) {
            return input;
        }
    });


    public void setIndex(int index) {
        mIndex.setValue(index);
    }


    public LiveData<Integer> getPosition() {
        return positon;
    }

}