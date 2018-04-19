package com.darvishiyan.architecture.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.darvishiyan.architecture.data.model.PersonModel;

import java.util.List;

/**
 * Created by Hesam on 2/12/2018.
 */

@Dao
public interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PersonModel models);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(PersonModel... model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(PersonModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(PersonModel... models);

    @Delete
    void delete(PersonModel model);

    @Delete
    void delete(PersonModel... models);

    @Query("select * from person")
    LiveData<List<PersonModel>> getAllPerson();

    @Query("select * from person where id = :id")
    PersonModel getPerson(long id);
}
