package com.hcmute.trietthao.yourtime.mvp.signUp.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.chooseList.view.ChooseListActivity;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.signUp.presenter.SignUpPresenter;
import com.hcmute.trietthao.yourtime.profile.Utility;
import com.hcmute.trietthao.yourtime.service.utils.Base64Utils;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;
import com.hcmute.trietthao.yourtime.service.utils.DateUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;


public class SignUpActivity extends AppCompatActivity implements ISignUpView, DBNguoiDungServer.userListener {
    private ShareDialog shareDialog;

    @Bind(R.id.imgv_sign_up_avatar)
    ImageView mImgvAvatar;
    @Bind(R.id.edit_sign_up_name)
    EditText mEditName;
    @Bind(R.id.edit_sign_up_email)
    EditText mEditEmail;
    @Bind(R.id.edit_sign_up_passw)
    EditText mEditPass;
    @Bind(R.id.btn_sign_up)
    Button mBtnSignUp;

    String encodedString="";


    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    final Context c = this;

    PreferManager preferManager;
    String  pass="",email="",name="";
    int id;
    DBNguoiDungServer dbNguoiDungServer;
    SignUpPresenter signUpPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        dbNguoiDungServer=new DBNguoiDungServer(this);

        preferManager = new PreferManager(getApplicationContext());
        signUpPresenter=new SignUpPresenter(this);

        shareDialog = new ShareDialog(this);
//        LoginActivity.FROM_FB=false;
//        LoginActivity.FROM_GG=false;

        if(LoginActivity.FROM_FB){
            Bundle inBundle = getIntent().getExtras();
            String namefb = inBundle.get("name").toString();
            String surnamefb = inBundle.get("surname").toString();
            String emailfb =inBundle.get("email").toString();
            String imageUrl = inBundle.get("imageUrl").toString();
            mEditName.setText("" + namefb + " " + surnamefb);
            mEditEmail.setText(emailfb);
            new SignUpActivity.DownloadImage((ImageView) findViewById(R.id.imgv_sign_up_avatar)).execute(imageUrl);
        }
        if(LoginActivity.FROM_GG){
            Bundle inBundle = getIntent().getExtras();
            String personName = inBundle.get("gg_name").toString();
            String personEmail = inBundle.get("gg_email").toString();
            String personId = inBundle.get("gg_url").toString();

            mEditName.setText(personName);
            mEditEmail.setText(personEmail);
            new SignUpActivity.DownloadImage((ImageView) findViewById(R.id.imgv_sign_up_avatar)).execute(personId);

            Log.e("Name::::::::",personName);
            Log.e("Email::::::", personEmail);
        }


        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email=mEditEmail.getText().toString();
                pass=mEditPass.getText().toString();
                name= mEditName.getText().toString();
                id= getIntCurrentDateTime();

                if(pass.trim().length() > 0 && email.trim().length() > 0 && name.trim().length() > 0&& isEmailValid(email)){
                    signUpPresenter.insertUser(id,name,encodedString,email,pass);

                }
                else
                    Toast.makeText(getApplication(),"Nhập đầy đủ thông tin!!!", Toast.LENGTH_LONG).show();
            }
        });

        mImgvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this,"vào on activity result:::",Toast.LENGTH_LONG).show();
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
        //Từ gg vào
    }

    @Override
    public void signUpSuccess() {
        dbNguoiDungServer.getUser(email);

    }

    @Override
    public void signUpFail(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void getIntentData() {

        Intent data = new Intent();
        data.putExtra("email", email);
        data.putExtra("name", name);
        data.putExtra("id",id);
        data.putExtra("pass", pass);
        setResult(RESULT_OK, data);

    }


    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {
        Toast.makeText(this,"Đăng ký thành công \n đang chuyển hướng !\n", Toast.LENGTH_LONG).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Log.e("email prefer::::",email);

                preferManager.createUserSignInSession(id,name,email);

                Intent chooseList= new Intent(SignUpActivity.this, ChooseListActivity.class);
                startActivity(chooseList);
                finish();

            }
        }, 1000);

    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImage(ImageView bmImage){
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls){
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try{
                InputStream in = new URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            }catch (Exception e){
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result){
            bmImage.setImageBitmap(result);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            LoginManager.getInstance().logOut();
            Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(login);
            finish();

        }

        return super.onKeyDown(keyCode, event);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(SignUpActivity.this);

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

        encodedString = Base64Utils.encodeTobase64(thumbnail);

        mImgvAvatar.setImageBitmap(thumbnail);
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

        encodedString = Base64Utils.encodeTobase64(bm);

        mImgvAvatar.setImageBitmap(bm);
    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
