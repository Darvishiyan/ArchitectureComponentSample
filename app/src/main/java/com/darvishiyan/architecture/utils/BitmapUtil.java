package com.darvishiyan.architecture.utils;

import android.Manifest;
import android.app.Activity;
import android.arch.persistence.room.TypeConverter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Created by Hesam on 2/12/2018.
 */

public class BitmapUtil {

    @TypeConverter
    public static Bitmap toBitmap(byte[] bytes) {
        if (bytes != null) {
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return null;
    }

    @TypeConverter
    public static byte[] toByteArray(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }
        return null;
    }

    public static void selectFromGallery(Activity activity, int requestCode, int permissionRequestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, permissionRequestCode);
            } else {
                innerSelectFromGallery(activity, requestCode);
            }
        } else {
            innerSelectFromGallery(activity, requestCode);
        }
    }

    private static void innerSelectFromGallery(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    public static String handleGalleryImageAndCrop(Activity activity, @NonNull Intent data, int requestCode) {
        try {
            if (data.getData() != null) {
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = activity.getContentResolver().query(data.getData(), filePath, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));
                    cursor.close();
                    String path = MediaStore.Images.Media.insertImage(activity.getContentResolver(), imagePath, "Title", null);
                    String loadImageFromImageCropper = getTmpImageName(activity);
                    Uri output = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(loadImageFromImageCropper));
                    activity.startActivityForResult(cropByIntent(activity, Uri.parse(path), output), requestCode);
                    return loadImageFromImageCropper;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(activity, "خطا در بارگذاری تصویر", Toast.LENGTH_SHORT).show();
        return null;
    }

    @NonNull
    private static String getTmpImageName(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/" + context.getPackageName() + "." + UUID.randomUUID().toString().replace("-", "") + ".jpg";
    }

    private static Intent cropByIntent(Context context, Uri input, Uri output) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(input, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 512);
        intent.putExtra("outputY", 512);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, output);

        List<ResolveInfo> resolvedIntentActivities = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, output, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        return intent;
    }

    public static Bitmap circleClip(Bitmap bmp) {
        Bitmap output = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(bmp.getWidth() / 2, bmp.getHeight() / 2, bmp.getHeight() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, rect, rect, paint);
        return output;
    }

}
