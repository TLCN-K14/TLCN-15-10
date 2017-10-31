package com.hcmute.trietthao.yourtime.mvp.chooseList.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hcmute.trietthao.yourtime.R;

import butterknife.ButterKnife;

public class ChooseListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);

        ButterKnife.bind(this);



    }
}
