package com.darvishiyan.architecture.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.darvishiyan.architecture.data.model.PersonModel;

import java.util.List;

/**
 * Created by Hesam on 2/15/2018.
 */

public class MainViewModel extends ViewModel {

    MutableLiveData<List<PersonModel>> personLiveData;

    public MutableLiveData<List<PersonModel>> getPersonLiveData() {
        if (personLiveData == null) {
            personLiveData = new MutableLiveData<>();
        }
        return personLiveData;
    }
}
