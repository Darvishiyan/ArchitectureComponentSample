package com.darvishiyan.architecture.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.darvishiyan.architecture.R;
import com.darvishiyan.architecture.adapter.PersonRecyclerViewAdapter;
import com.darvishiyan.architecture.core.BaseActivity;
import com.darvishiyan.architecture.core.BaseApplication;
import com.darvishiyan.architecture.data.DataBase;
import com.darvishiyan.architecture.data.model.PersonModel;
import com.darvishiyan.architecture.utils.BitmapUtil;
import com.darvishiyan.architecture.utils.StringUtil;
import com.darvishiyan.architecture.viewModel.MainViewModel;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Hesam on 2/12/2018.
 */

public class MainActivity extends BaseActivity {

    private static final int LOAD_IMAGE_PERMISSION = 6000;
    private static final int LOAD_IMAGE_FROM_GALLERY = 6001;
    private static final int IMAGE_CROP = 6002;

    @Inject
    protected DataBase dataBase;

    private RecyclerView rvMain;
    private PersonRecyclerViewAdapter personRecyclerViewAdapter;

    private MainViewModel mainViewModel;

    private String loadImageFromImageCropper = null;
    private AlertDialog addPersonDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        BaseApplication.getComponent().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void listener() {

    }

    @Override
    protected void load() {
        dataBase.personDao().getAllPerson().observe(this, personModels -> {
            if (personModels != null) {
                mainViewModel.getPersonLiveData().setValue(personModels);
            }
        });

        mainViewModel.getPersonLiveData().observe(this, personModels -> {
            personRecyclerViewAdapter.setData(personModels);
        });
    }

    @Override
    protected void loadUi() {
    }

    @Override
    protected void init() {
        setContentView(R.layout.main);

        rvMain = findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        personRecyclerViewAdapter = new PersonRecyclerViewAdapter();
        rvMain.setAdapter(personRecyclerViewAdapter);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @SuppressLint("CheckResult")
    public void addPerson(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Person");
        View innerView = LayoutInflater.from(this).inflate(R.layout.person_dialog, null);
        EditText etName = innerView.findViewById(R.id.etName);
        ImageButton ibAvatar = innerView.findViewById(R.id.ibAvatar);

        ibAvatar.setOnClickListener(v -> {
            BitmapUtil.selectFromGallery(MainActivity.this, LOAD_IMAGE_FROM_GALLERY, LOAD_IMAGE_PERMISSION);
        });

        builder.setView(innerView);

        builder.setPositiveButton("Ok", (dialog, which) -> {
            if (StringUtil.validate(etName.getText().toString())) {
                Bitmap bmp = null;
                if (ibAvatar.getTag() != null && ibAvatar.getTag() instanceof Bitmap) {
                    bmp = (Bitmap) ibAvatar.getTag();
                }
                PersonModel personModel = new PersonModel(etName.getText().toString(), bmp);
                Observable.just(dataBase.personDao())
                        .subscribeOn(Schedulers.io())
                        .subscribe(dao -> dao.insert(personModel));
                Toast.makeText(this, etName.getText().toString() + " added.", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            } else {
                Toast.makeText(this, "insert person name", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
        });

        addPersonDialog = builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOAD_IMAGE_PERMISSION) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResult == PackageManager.PERMISSION_GRANTED) {
                    BitmapUtil.selectFromGallery(MainActivity.this, LOAD_IMAGE_FROM_GALLERY, LOAD_IMAGE_PERMISSION);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == LOAD_IMAGE_FROM_GALLERY && data != null) {
                loadImageFromImageCropper = BitmapUtil.handleGalleryImageAndCrop(MainActivity.this, data, IMAGE_CROP);
            } else if (requestCode == IMAGE_CROP) {
                if (addPersonDialog != null && addPersonDialog.isShowing()) {
                    ImageButton ibAvatar = addPersonDialog.findViewById(R.id.ibAvatar);
                    if (ibAvatar != null) {
                        Bitmap bitmap = BitmapUtil.circleClip(BitmapFactory.decodeFile(loadImageFromImageCropper));
                        ibAvatar.setImageBitmap(bitmap);
                        ibAvatar.setTag(bitmap);
                    }
                }
            }
        }
    }
}
