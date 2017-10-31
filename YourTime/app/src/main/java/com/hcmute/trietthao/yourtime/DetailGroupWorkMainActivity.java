package com.hcmute.trietthao.yourtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailGroupWorkMainActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;

    @Bind(R.id.lnl_groupempty)
    LinearLayout mLnlGroupEmpty;
    @Bind(R.id.img_groupempty)
    ImageView mImgGroupEmpty;
    @Bind(R.id.txt_groupempty)
    TextView mTxtGroupEmpty;

    String EXTRA_GROUPMAIN_ID = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupworkmain_detail);
        ButterKnife.bind(this);

        EXTRA_GROUPMAIN_ID = getIntent().getStringExtra("EXTRA_GROUPMAIN_ID");

        checkEmpty();

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

    }

    public void checkEmpty(){
        switch (EXTRA_GROUPMAIN_ID){
            case "1":
                mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_assigned_white));
                mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_assigned_empty));
                break;
            case "2":
                mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_starred_white));
                mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_starred_empty));
                break;
            case "3":
                mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_today_white));
                mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_today_empty));
                break;
            case "4":
                mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_week_white));
                mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_week_empty));
                break;
            case "5":
                mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_all_white));
                mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_all_empty));
                break;
            case "6":
                mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_completed_white));
                mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_completed_empty));
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
