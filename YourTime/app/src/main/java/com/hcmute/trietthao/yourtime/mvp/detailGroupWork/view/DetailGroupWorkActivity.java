package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;

import butterknife.Bind;
import butterknife.ButterKnife;



public class DetailGroupWorkActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.txt_show_hide_completed_works)
    TextView mTxtShowHideCompletedWorks;

    boolean isShowCompletedWorks = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupwork_detail);
        ButterKnife.bind(this);

        String extraGroupworkId = getIntent().getStringExtra("EXTRA_GROUPWORK_ID");

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }

//        showHideCompletedWorks =(TextView) findViewById(R.id.show_hide_completed_works);
        mTxtShowHideCompletedWorks.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.txt_show_hide_completed_works:
                if(isShowCompletedWorks){
                    mTxtShowHideCompletedWorks.setText(R.string.hide_completed_works);
                    isShowCompletedWorks=false;
                }else{
                    mTxtShowHideCompletedWorks.setText(R.string.show_completed_works);
                    isShowCompletedWorks=true;
                }

        }
    }
}
