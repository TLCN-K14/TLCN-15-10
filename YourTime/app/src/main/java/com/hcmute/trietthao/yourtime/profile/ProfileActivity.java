package com.hcmute.trietthao.yourtime.profile;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.share.widget.ShareDialog;
import com.hcmute.trietthao.yourtime.R;

import java.io.InputStream;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileActivity extends TabActivity {

    private ShareDialog shareDialog;
    @InjectView(R.id.img_avatar)
    CircleImageView mImgVAvatar;
    @InjectView(R.id.txt_user_name)
    TextView mTxtUserName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec accountspec = tabHost.newTabSpec("Account");
        accountspec.setIndicator("Account");
        Intent accountIntent = new Intent(this, AccountActivity.class);
        accountspec.setContent(accountIntent);

        TabHost.TabSpec generalspec = tabHost.newTabSpec("General");
        generalspec.setIndicator("General");
        Intent generalIntent = new Intent(this, GeneralActivity.class);
        generalspec.setContent(generalIntent);

        TabHost.TabSpec extrasspec = tabHost.newTabSpec("Extras");
        extrasspec.setIndicator("Extras");
        Intent extrasIntent = new Intent(this, ExtrasActivity.class);
        extrasspec.setContent(extrasIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(accountspec);
        tabHost.addTab(generalspec);
        tabHost.addTab(extrasspec);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }

//        Profile profile = Profile.getCurrentProfile();
//
//
//        String name = profile.getFirstName();
//        String surname = profile.getLastName();
//        String imageUrl = profile.getProfilePictureUri(200,200).toString();
        ShareDialog shareDialog = new ShareDialog(this);

        Bundle inBundle = getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        mTxtUserName.setText("" + name + " " + surname);

        new ProfileActivity.DownloadImage((ImageView)findViewById(R.id.img_avatar)).execute(imageUrl);
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
