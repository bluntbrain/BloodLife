package com.example.learningmaps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UploadPicture extends AppCompatActivity {

    private TextView upload;
    private RoundedImageView profilepic;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int FILE_SELECT_CODE = 0;
    private Uri file;
    private ImageView profilechange;
    private FileOutputStream mFileOutputStream;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_picture);

        isStoragePermissionGranted();
        upload=findViewById(R.id.nextto);
        profilechange=findViewById(R.id.changepic);
        profilepic=findViewById(R.id.profilepic);


        profilechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String path =getRealPathFromURI(UploadPicture.this,file);

               String name =getFileName(file);
                try {
                    insertInPrivateStorage(name, path);
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Nope",Toast.LENGTH_SHORT).show();


                    e.printStackTrace();
                }

            }
        });


    }
    private void insertInPrivateStorage(String name, String path){
        try {
            FileOutputStream fos = openFileOutput(name, MODE_APPEND);

            File file = new File(path);
            int size = (int) file.length();

            byte[] bytes = getBytesFromFile(file, size);

            fos.write(bytes);
            fos.close();

            Toast.makeText(getApplicationContext(), "File saved in :" + getFilesDir() + "/" + name, Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    private byte[] getBytesFromFile(File file, int size){
        byte[] data = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(data, 0, data.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //byte[] data = org.apache.commons.io.FileUtils.readFileToByteArray(file);
        //byte[] data = FileUtils.readFileToByteArray(file);
        return data;

    }

    private String getFileName(Uri uri)
    {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    private String getRealPathFromURI(Context context, Uri uri)
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null,
                null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return null;
    }



    public void isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);


        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_REQUEST_CODE){
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }else
                {

                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode==FILE_SELECT_CODE && resultCode== RESULT_OK && data!=null)
        {
            file = data.getData();

            Transformation transformation = new RoundedTransformationBuilder()
                    .cornerRadiusDp(80)
                    .oval(false)
                    .borderColor(Color.WHITE)
                    .borderWidthDp(3)
                    .build();

            Picasso.get().load(file.toString()).transform(transformation).into(profilepic);

        }else
        {Toast.makeText(UploadPicture.this,"noyolo",Toast.LENGTH_LONG).show();


        }



        }

}

