package com.darvishiyan.architecture.model;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by Hesam on 2/12/2018.
 */

public enum DataType {
    number("عدد"),
    text("متن"),
    dateTime("تاریخ");

    public String persianName;

    DataType(String persianName) {
        this.persianName = persianName;
    }

    @TypeConverter
    public static int toInt(DataType dataType) {
        return dataType.ordinal();
    }

    @TypeConverter
    public static DataType toDataType(int ordinal) {
        return DataType.values()[ordinal];
    }

}
