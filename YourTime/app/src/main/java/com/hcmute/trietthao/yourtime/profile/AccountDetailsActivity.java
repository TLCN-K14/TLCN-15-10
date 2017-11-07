package com.hcmute.trietthao.yourtime.profile;



import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hcmute.trietthao.yourtime.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AccountDetailsActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.ln_take_photo)
    LinearLayout mLnTakePhoto;
    @Bind(R.id.imgv_ava_details)
    CircleImageView mImgPhoto;
    @Bind(R.id.ln_change_name)
    LinearLayout mLnChangeName;
    @Bind(R.id.ln_change_pass)
    LinearLayout mLnChangePass;
    @Bind(R.id.ln_change_email)
    LinearLayout mLnChangeEmail;
    @Bind(R.id.imgv_back)
    ImageView mImgvBack;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    final Context c = this;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        ButterKnife.bind(this);
        initListener();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar ab = getSupportActionBar();
//        if (ab != null) {
//            ab.setDisplayHomeAsUpEnabled(true);
//        }

        mImgvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mLnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        mLnChangeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_change_name, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });

        mLnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_change_email, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });


        mLnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.dialog_password, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });
                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {

                }
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountDetailsActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(AccountDetailsActivity.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mImgPhoto.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mImgPhoto.setImageBitmap(bm);
    }
    protected void initListener(){
        mLnTakePhoto.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ln_account_details:
                selectImage();
                break;
        }

    }
}
