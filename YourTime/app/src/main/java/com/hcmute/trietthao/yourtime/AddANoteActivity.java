package com.hcmute.trietthao.yourtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by lxtri on 24/10/2017.
 */

public class AddANoteActivity extends AppCompatActivity implements View.OnClickListener{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_note);

        ButterKnife.bind(this);

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
//        switch (i){
//
//        }
    }
}
