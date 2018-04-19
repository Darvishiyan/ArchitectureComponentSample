package com.darvishiyan.architecture.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.darvishiyan.architecture.data.model.DataTypeModel;

import java.util.List;

/**
 * Created by Hesam on 2/12/2018.
 */

@Dao
public interface DataTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(DataTypeModel models);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insert(DataTypeModel... model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(DataTypeModel model);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(DataTypeModel... models);

    @Delete
    void delete(DataTypeModel model);

    @Delete
    void delete(DataTypeModel... models);

    @Query("select * from dataType")
    LiveData<List<DataTypeModel>> getAllDataType();
}
