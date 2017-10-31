package com.hcmute.trietthao.yourtime.mvp.signUp.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.signIn.view.UserProfile;

import java.io.InputStream;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SignUpActivity extends AppCompatActivity {
    private ShareDialog shareDialog;

    @Bind(R.id.imgv_sign_up_avatar)
    ImageView mImgvAvatar;
    @Bind(R.id.edit_sign_up_name)
    EditText mEditName;
    @Bind(R.id.edit_sign_up_email)
    EditText mEditEmail;
    @Bind(R.id.edit_sign_up_passw)
    EditText getmEditPass;
    @Bind(R.id.btn_sign_in)
    Button mBtnSignIn;

    public final static int FROM_GG=0;
    public final static int FROM_FB=1;
    public final static String KEY_FROM = "KEY_FROM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_sign_up);
//        mImgvAvatar=(ImageView) findViewById(R.id.imgv_sign_up_avatar);
//        mEditName=(EditText) findViewById(R.id.edit_sign_up_name);

        ButterKnife.bind(this);

        shareDialog = new ShareDialog(this);

        Bundle inBundle = getIntent().getExtras();
        int from = getIntent().getIntExtra(KEY_FROM, FROM_FB);
        switch (from){
            case FROM_FB:
                String name = inBundle.get("name").toString();
                String surname = inBundle.get("surname").toString();
                String imageUrl = inBundle.get("imageUrl").toString();

                if (inBundle != null)
                {
                    mEditName.setText("" + name + " " + surname);
                    new SignUpActivity.DownloadImage((ImageView) findViewById(R.id.imgv_sign_up_avatar)).execute(imageUrl);
                }
                break;
            case FROM_GG:
//                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(getIntent());
//                GoogleSignInAccount acct = result.getSignInAccount();
                String personName = inBundle.get("name").toString();
                String personEmail = inBundle.get("email").toString();
//                String personId = inBundle.get("imageUrl").toString();
//                Uri personPhoto = inBundle.getPhotoUrl();

                mEditName.setText(personName);
                mEditEmail.setText(personEmail);
                //new SignUpActivity.DownloadImage((ImageView) findViewById(R.id.imgv_sign_up_avatar)).execute(personPhoto);

                Log.e("Name::::::::",personName);
                Log.e("Email::::::", personEmail);
                break;
        }


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

}
