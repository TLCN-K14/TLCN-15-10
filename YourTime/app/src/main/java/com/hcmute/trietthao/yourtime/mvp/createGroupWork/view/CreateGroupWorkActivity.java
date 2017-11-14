package com.hcmute.trietthao.yourtime.mvp.createGroupWork.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter.TasksPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxtri on 24/10/2017.
 */

public class CreateGroupWorkActivity extends AppCompatActivity implements View.OnClickListener{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_groupwork);

        ButterKnife.bind(this);

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
//        switch (i){
//
//        }
    }
}
