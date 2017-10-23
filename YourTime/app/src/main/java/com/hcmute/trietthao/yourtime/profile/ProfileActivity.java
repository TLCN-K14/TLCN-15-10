package com.hcmute.trietthao.yourtime.profile;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;

import butterknife.ButterKnife;


public class ProfileActivity extends TabActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            View v = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
}
