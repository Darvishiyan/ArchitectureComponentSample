package com.darvishiyan.architecture.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.darvishiyan.architecture.data.holder.PersonaDataStoreHolder;
import com.darvishiyan.architecture.data.model.PersonaDataStoreModel;

import java.util.List;

/**
 * Created by Hesam on 2/12/2018.
 */

@Dao
public interface PersonaDataStoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PersonaDataStoreModel models);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(PersonaDataStoreModel model);

    @Delete
    void delete(PersonaDataStoreModel model);

    @Query("select" +
            " dataType.id as dataTypeId," +
            " dataType.name as dataTypeName," +
            " personDataStore.id as dataStoreId," +
            " personDataStore.data" +
            " from personDataStore" +
            " inner join person on person.id = personDataStore.pid" +
            " inner join dataType on dataType.id = personDataStore.did where person.id = :id")
    LiveData<List<PersonaDataStoreHolder>> getPersonData(long id);
}
