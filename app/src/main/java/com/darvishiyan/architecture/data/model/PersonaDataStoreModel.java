package com.darvishiyan.architecture.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Hesam on 2/12/2018.
 */

@Entity(tableName = "personDataStore",
        foreignKeys = {
                @ForeignKey(entity = PersonModel.class, parentColumns = "id", childColumns = "pid", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = DataTypeModel.class, parentColumns = "id", childColumns = "did", onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("pid"), @Index("did")})
public class PersonaDataStoreModel {

    @PrimaryKey(autoGenerate = true)
    public long id;
    public long pid;
    public long did;
    public String data;

    public PersonaDataStoreModel(long pid, long did, String data) {
        this.pid = pid;
        this.did = did;
        this.data = data;
    }
}
