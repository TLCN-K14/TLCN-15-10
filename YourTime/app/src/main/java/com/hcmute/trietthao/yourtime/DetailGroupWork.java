package com.hcmute.trietthao.yourtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;



public class DetailGroupWork extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.txt_show_hide_completed_works)
    TextView mTxtShowHideCompletedWorks;

    boolean isShowCompletedWorks = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupwork_detail);
        ButterKnife.bind(this);

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
