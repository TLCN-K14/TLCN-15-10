package com.hcmute.trietthao.yourtime.mvp.signIn.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hcmute.trietthao.yourtime.R;

import butterknife.ButterKnife;


public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

//        mBtnSignUp.setOnClickListener(new OnCLickListener);


    }

}
