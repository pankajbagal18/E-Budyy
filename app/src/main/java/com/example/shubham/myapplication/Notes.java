package com.example.shubham.myapplication;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import static android.app.PendingIntent.getActivity;
import static android.media.MediaRecorder.VideoSource.CAMERA;

public class Notes extends AppCompatActivity{

    private EditText editText,input;
    private Button save;
    private Button clear;
    private String m_Text;
    private int GALLERY = 1, CAMERA = 2;
    private static final String IMAGE_DIRECTORY = "/appphoto";
    private com.github.clans.fab.FloatingActionButton pdfNotes;
    private com.github.clans.fab.FloatingActionButton videoNotes;
    private com.github.clans.fab.FloatingActionButton textNotes;
    int count=0;
    String filename="example.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        editText = (EditText)findViewById(R.id.editNotes);
        save = (Button)findViewById(R.id.save);
        clear = (Button)findViewById(R.id.clear);
        pdfNotes = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.pdfNotes);
        textNotes = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.textNotes);
        videoNotes = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.videoNotes);
        pdfNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            FilenameDialog filenameDialog = new FilenameDialog();
//            filenameDialog.show(getSupportFragmentManager(),"example dialog");
                save_file();
            }
        });
        textNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewList = new Intent(getApplicationContext(),Notes2.class);
                startActivity(viewList);
            }
        });
        videoNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent,7);
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.getText().clear();
            }
        });
    }

    public void save_file() {
        String text = editText.getText().toString();
        count++;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("filename"+count+".txt",MODE_PRIVATE);
            Toast.makeText(getApplicationContext(),"Saved to "+getFilesDir()+"/"+filename+".txt",Toast.LENGTH_LONG).show();
            editText.getText().clear();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }
    public void takePhotoFromCamera(){
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(Notes.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //imageview.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Notes.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            //imageview.setImageBitmap(thumbnail);
            String str = saveImage(thumbnail);

            Toast.makeText(Notes.this, "Image Saved!"+str, Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==7) {
            if (resultCode == RESULT_OK) {
                String PathHolder = data.getData().getPath();
                DBHelper dbHelper = new DBHelper(this);
                Uri uri = data.getData();
                String displayname = getRealPathFromURI(uri);
                //path = path.substring(path.indexOf("/"),path.lastIndexOf(path));
                Toast.makeText(getApplicationContext(),displayname,Toast.LENGTH_LONG);
                if(dbHelper.insertPath(PathHolder,displayname))
                {
                    Toast.makeText(getApplicationContext(),"inserted :- "+displayname,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"not inserted",Toast.LENGTH_LONG).show();
                }
            }
        }

    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            String s = Calendar.getInstance()
                    .getTimeInMillis() + ".jpg";
            File f = new File(wallpaperDirectory, s);
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            DBHelper dbHelper = new DBHelper(this);
            dbHelper.insertPath(f.getAbsolutePath(),getRealPathFromURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY+"/"+s))));
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "something";
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            int idx = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}

