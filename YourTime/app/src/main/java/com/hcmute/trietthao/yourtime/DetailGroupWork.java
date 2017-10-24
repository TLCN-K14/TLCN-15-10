package com.hcmute.trietthao.yourtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lxtri on 24/10/2017.
 */

public class DetailGroupWork extends AppCompatActivity implements View.OnClickListener{

    TextView showHideCompletedWorks;

    boolean isShowCompletedWorks = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupwork_detail);

        showHideCompletedWorks =(TextView) findViewById(R.id.show_hide_completed_works);
        showHideCompletedWorks.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.show_hide_completed_works:
                if(isShowCompletedWorks){
                    showHideCompletedWorks.setText(R.string.hide_completed_works);
                    isShowCompletedWorks=false;
                }else{
                    showHideCompletedWorks.setText(R.string.show_completed_works);
                    isShowCompletedWorks=true;
                }

        }
    }
}
