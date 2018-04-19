package com.darvishiyan.architecture.data.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.darvishiyan.architecture.data.DataBase;
import com.darvishiyan.architecture.data.model.PersonModel;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PersonDaoTest {

    private DataBase dataBase;

    private PersonDao personDao;

    private PersonModel personModel = new PersonModel("tester", null);

    @Before
    public void initDb() {
        dataBase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), DataBase.class).build();
        personDao = dataBase.personDao();
    }

    @Test
    public void personCanBeRetrievedAfterInsert() {
        long id = personDao.insert(personModel);
        PersonModel tmp = personDao.getPerson(id);
        Assert.assertEquals(personModel.name, tmp.name);
    }

    @After
    public void closeDb() {
        dataBase.close();
    }

}
