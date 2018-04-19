package com.darvishiyan.architecture.binder;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.darvishiyan.architecture.R;
import com.darvishiyan.architecture.data.model.PersonModel;

/**
 * Created by Hesam on 2/15/2018.
 */

public class PersonBinder extends BaseObservable {

    private Context context;
    private PersonModel personModel;

    public PersonBinder(Context context, PersonModel personModel) {
        this.context = context;
        this.personModel = personModel;
    }

    @Bindable
    public String getName() {
        return personModel.name;
    }

    @Bindable
    public Drawable getAvatar() {
        if (personModel.avatar != null) {
            return new BitmapDrawable(context.getResources(), personModel.avatar);
        }
        return context.getResources().getDrawable(R.drawable.ic_person_black_24dp);
    }
}
