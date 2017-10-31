package com.hcmute.trietthao.yourtime;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


import java.io.InputStream;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;



public class SettingsFragment extends Fragment {


    CircleImageView mImgVAvatar;
    TextView mTxtUserName;

    TabHost mTabHost;
    TabWidget mTabWidget;
    RelativeLayout mRltProfile;
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

        Bundle inBundle = getActivity().getIntent().getExtras();
        String name = inBundle.get("name").toString();
        String surname = inBundle.get("surname").toString();
        String imageUrl = inBundle.get("imageUrl").toString();

        mTxtUserName.setText("" + name + " " + surname);

        new SettingsFragment.DownloadImage((ImageView)container.findViewById(R.id.img_avatar)).execute(imageUrl);

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
        // tabWidget.getChildAt(CurrentTab).setBackgroundResource(R.color.colorWhite);
        isListOpen = false;
        CurrentTab = 3;
        mTabHost.setCurrentTab(CurrentTab);
    }
    // Hàm hiện list new hoặc list address hoặc list type
    private void showList(int tab){
        isListOpen = true;
        CurrentTab = tab;
        // tabWidget.getChildAt(tab).setBackgroundResource(R.color.colorGray);
        mTabHost.setCurrentTab(tab);
    }
    // Hàm chuyển tab
    private void changeTab(int tab) {
        if (CurrentTab == tab) {
            hideList();
            //tabWidget.getChildAt(tab).setBackgroundResource(R.color.colorWhite);
        } else {
            showList(tab);
        }
    }

}