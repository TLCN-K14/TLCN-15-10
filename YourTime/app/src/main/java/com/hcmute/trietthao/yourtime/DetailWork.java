package com.hcmute.trietthao.yourtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxtri on 24/10/2017.
 */

public class DetailWork extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.img_delete)
    ImageView mImgDelete;
    @Bind(R.id.img_priority)
    ImageView mImgPriority;
    @Bind(R.id.img_show_hide_delete)
    ImageView mImgShowHideDelete;

    boolean isDelete = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);

        ButterKnife.bind(this);

        mImgDelete.setVisibility(View.GONE);
        mImgShowHideDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.img_show_hide_delete:
                if(isDelete){
                    mImgDelete.setVisibility(View.GONE);
                    mImgPriority.setVisibility(View.VISIBLE);
                    isDelete=false;

                }else{
                    mImgDelete.setVisibility(View.VISIBLE);
                    mImgPriority.setVisibility(View.GONE);
                    isDelete=true;
                }
        }
    }
}
