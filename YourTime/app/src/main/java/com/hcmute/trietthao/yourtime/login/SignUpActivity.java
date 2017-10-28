package com.hcmute.trietthao.yourtime.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hcmute.trietthao.yourtime.R;

import butterknife.ButterKnife;

/**
 * Created by admin on 10/2/17.
 */

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

//        mBtnSignUp.setOnClickListener(new OnCLickListener);


    }

}
