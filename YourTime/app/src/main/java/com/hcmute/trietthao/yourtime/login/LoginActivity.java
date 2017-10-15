package com.hcmute.trietthao.yourtime.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hcmute.trietthao.yourtime.R;

import java.util.Timer;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by admin on 9/29/17.
 */

public class LoginActivity extends AppCompatActivity {

    ViewPager mViewPager;
    CustomPagerAdapter mCustomPagerAdapter;
    Timer timer;
    private CallbackManager mCallbackManager;

    @InjectView(R.id.btnSignUp)
    Button mBtnSignUp;
    @InjectView(R.id.btn_login_facebook)
    LoginButton mBtnLoginFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        mCallbackManager = CallbackManager.Factory.create();


        ButterKnife.inject(this);

        mBtnLoginFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                mTvInfo.setText("User ID: " + loginResult.getAccessToken().getUserId() + "\n" +
//                        "Auth Token: " + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
//                mTvInfo.setText("Login canceled.");
            }

            @Override
            public void onError(FacebookException e) {
//                mTvInfo.setText("Login failed.");
            }
        });

//        mBtnSignUp.setOnClickListener(new OnCLickListener);

//        final int[] mResources = {
//                R.drawable.collaborate_login,
//                R.drawable.login2
//
//        };
//        mCustomPagerAdapter = new CustomPagerAdapter(this, mResources);
//        mViewPager = (ViewPager) this.findViewById(R.id.pagerF);
//        mViewPager.setAdapter(mCustomPagerAdapter);
//        //Xác định thời gian chạy slide ảnh
//        TimerTask timerTask = new TimerTask() {
//            @Override
//            public void run() {
//                mViewPager.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        mViewPager.setCurrentItem((mViewPager.getCurrentItem() + 1) % mResources.length);
//                    }
//                });
//            }
//        };
//        timer = new Timer();
//        timer.schedule(timerTask, 3000, 3000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
