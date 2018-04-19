package com.darvishiyan.architecture.data.model;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import com.darvishiyan.architecture.data.DataBase;
import com.darvishiyan.architecture.data.holder.PersonaDataStoreHolder;

import java.util.List;

/**
 * Created by Hesam on 2/12/2018.
 */

@Entity(tableName = "person")
public class PersonModel {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public Bitmap avatar;
    @Ignore
    private MutableLiveData<List<PersonaDataStoreHolder>> dataStoreList;

    public MutableLiveData<List<PersonaDataStoreHolder>> getDataStoreList(LifecycleOwner owner, DataBase dataBase) {
        if (dataStoreList == null) {
            dataStoreList = new MutableLiveData<>();
            dataBase.personaDataStoreDao().getPersonData(id).observe(owner, personaDataStoreHolders -> {
                dataStoreList.setValue(personaDataStoreHolders);
            });
        }
        return dataStoreList;
    }

    public PersonModel(String name, Bitmap avatar) {
        this.name = name;
        this.avatar = avatar;
    }
}
