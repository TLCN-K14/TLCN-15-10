package com.hcmute.trietthao.yourtime;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.signIn.presenter.SignInPresenter;
import com.hcmute.trietthao.yourtime.mvp.signUp.presenter.SignUpPresenter;
import com.hcmute.trietthao.yourtime.profile.AccountDetailsActivity;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;



public class SettingsFragment extends Fragment {


    CircleImageView mImgVAvatar;
    TextView mTxtUserName;

    TabHost mTabHost;
    TabWidget mTabWidget;
    RelativeLayout mRltProfile;
    LinearLayout mLnSignOut, mLnDetails;
    int CurrentTab = -1;
    public static boolean isListOpen = false;



    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mTabHost = (TabHost) view.findViewById(R.id.tabhost);
        mRltProfile=(RelativeLayout) view.findViewById(R.id.rlt_profile);

        mImgVAvatar= (CircleImageView) view.findViewById(R.id.img_avatar);
        mTxtUserName=(TextView) view.findViewById(R.id.txt_user_name);

        mLnDetails=(LinearLayout) view.findViewById(R.id.ln_account_details);
        mLnSignOut=(LinearLayout) view.findViewById(R.id.ln_sign_out);


//        if(signInPresenter.checkLogin())
//            getActivity().finish();
//
//        // get user data from session
//        HashMap<String, String> user = signInPresenter.getUserDetails();
//        // get name
//        String userPassw = user.get(SignInPresenter.KEY_PASSW);
//
//        // get email
//        String userEmail = user.get(SignInPresenter.KEY_EMAIL);

//        mTxtUserName.setText(userEmail);

        LoginActivity.FROM_FB=false;

        tabHostSetup();      // Khởi tạo tabhost chính

        mTabWidget.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(0);
            }
        });
        mTabWidget.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(1);
            }
        });
        mTabWidget.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTab(2);
            }
        });
        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        if(LoginActivity.FROM_FB){
            Bundle inBundle = getActivity().getIntent().getExtras();
            String name = inBundle.get("name").toString();
            String surname = inBundle.get("surname").toString();
            String imageUrl = inBundle.get("imageUrl").toString();

            mTxtUserName.setText("" + name + " " + surname);

            new SettingsFragment.DownloadImage((ImageView)container.findViewById(R.id.img_avatar)).execute(imageUrl);
        }

        mLnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent details= new Intent(getActivity(), AccountDetailsActivity.class);
                startActivity(details);

            }
        });
        mLnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Sign Out");
                alertDialogBuilder.setMessage("Are you sure you want to sign out?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
//                                signInPresenter.logoutUser();
                            }
                        });

                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                arg0.dismiss();

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            }
        });

        return view;
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
    public void tabHostSetup() {
        mTabHost.setup();
        TabHost.TabSpec tabAccount = mTabHost.newTabSpec("Account");
        tabAccount.setContent(R.id.ln_tab_account);
        tabAccount.setIndicator("Account");
        mTabHost.addTab(tabAccount);

        TabHost.TabSpec tabGeneral = mTabHost.newTabSpec("General");
        tabGeneral.setIndicator("General");
        tabGeneral.setContent(R.id.ln_tab_general);
        mTabHost.addTab(tabGeneral);

        TabHost.TabSpec tabAddress = mTabHost.newTabSpec("Extras");
        tabAddress.setIndicator("Extras");
        tabAddress.setContent(R.id.ln_tab_extras);
        mTabHost.addTab(tabAddress);

        mTabWidget = mTabHost.getTabWidget();

        mTabHost.setCurrentTab(3);
    }

    private void hideList(){
        isListOpen = false;
        CurrentTab = 3;
        mTabHost.setCurrentTab(CurrentTab);
    }
    private void showList(int tab){
        isListOpen = true;
        CurrentTab = tab;
        mTabHost.setCurrentTab(tab);
    }
    private void changeTab(int tab) {
        if (CurrentTab == tab) {
            hideList();
        } else {
            showList(tab);
        }
    }

}