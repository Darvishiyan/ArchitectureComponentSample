package com.darvishiyan.architecture.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.darvishiyan.architecture.model.DataType;


/**
 * Created by Hesam on 2/12/2018.
 */

@Entity(tableName = "dataType")
public class DataTypeModel {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public long lastUse;
    @ColumnInfo(typeAffinity = ColumnInfo.INTEGER)
    public DataType type;

    public DataTypeModel(String name, DataType type) {
        this.name = name;
        this.type = type;
        this.lastUse = System.currentTimeMillis();
    }
}
