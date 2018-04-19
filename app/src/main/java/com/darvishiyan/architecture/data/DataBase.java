package com.darvishiyan.architecture.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.darvishiyan.architecture.data.dao.DataTypeDao;
import com.darvishiyan.architecture.data.dao.PersonDao;
import com.darvishiyan.architecture.data.dao.PersonaDataStoreDao;
import com.darvishiyan.architecture.data.model.DataTypeModel;
import com.darvishiyan.architecture.data.model.PersonModel;
import com.darvishiyan.architecture.data.model.PersonaDataStoreModel;
import com.darvishiyan.architecture.model.DataType;
import com.darvishiyan.architecture.utils.BitmapUtil;

/**
 * Created by Hesam on 2/12/2018.
 */

@Database(entities =
        {
                PersonModel.class,
                DataTypeModel.class,
                PersonaDataStoreModel.class
        }, version = 1)
@TypeConverters({BitmapUtil.class, DataType.class})
public abstract class DataBase extends RoomDatabase {

    private static final String DB_NAME = "db";

    private static volatile DataBase instance;

    public abstract PersonDao personDao();

    public abstract DataTypeDao dataTypeDao();

    public abstract PersonaDataStoreDao personaDataStoreDao();

    public static DataBase getInstance(Context context) {
        synchronized (DataBase.class) {
            if (instance == null) {
                instance = Room.databaseBuilder(context, DataBase.class, DB_NAME).build();
            }
            return instance;
        }
    }
}
