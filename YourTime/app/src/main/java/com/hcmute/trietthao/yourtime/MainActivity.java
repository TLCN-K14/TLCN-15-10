package com.hcmute.trietthao.yourtime;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.TasksFragment;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;


public class MainActivity extends AppCompatActivity{

    @Bind(R.id.fab_create_work)
    FloatingActionButton mFabCreateWork;
    @Bind(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    ScreenUtils screenUtils;

    View mBottomSheetCreateWorkView;
    PreferManager preferManager;

    private int PROFILE_REQ =1;
    DBNguoiDungServer dbNguoiDungServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ButterKnife.bind(this);

        setupBottomSheetView();
        preferManager = new PreferManager(getApplicationContext());
        Toast.makeText(getApplicationContext(),
                "User Login Status: " + preferManager.isLoggedIn(),
                Toast.LENGTH_LONG).show();
        Log.e("email:::::::::",preferManager.KEY_EMAIL);
        if(preferManager.checkLogin())
            finish();


        mBottomNavigationView .setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_tasks:
                                selectedFragment = TasksFragment.newInstance();
                                mFabCreateWork.animate().alpha(1.0f).setDuration(200);
                                mFabCreateWork.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_calendar:
                                selectedFragment = CalendarFragment.newInstance();
                                mFabCreateWork.animate().alpha(1.0f).setDuration(200);
                                mFabCreateWork.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_setting:
                                selectedFragment = SettingsFragment.newInstance();
                                mFabCreateWork.animate().alpha(0.0f).setDuration(200);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mFabCreateWork.setVisibility(View.INVISIBLE);
                                    }
                                }, 300);

                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.content, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, TasksFragment.newInstance());
        transaction.commit();
    }

    public void setupBottomSheetView(){

        mBottomSheetCreateWorkView = getLayoutInflater().inflate(R.layout.bottom_sheet_create_work, null);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(mBottomSheetCreateWorkView);
        bottomSheetDialog.setCancelable (true);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetCreateWorkView.getParent());
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        screenUtils = new ScreenUtils(getApplicationContext());
        bottomSheetBehavior.setPeekHeight(screenUtils.getHeight()*80/100);

        mFabCreateWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                bottomSheetDialog.show();
            }
        });
    }

    BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String email ="";
        String name = "";
        String id="";
        if(resultCode==RESULT_OK) {
            if(requestCode==PROFILE_REQ) {
                // Sau khi đăng nhập xong thì chuyển tới
                email = data.getStringExtra("email");
                name = data.getStringExtra("name");
                id=data.getStringExtra("id");
                preferManager.createUserSignInSession(Integer.parseInt(id),name,email);
                dbNguoiDungServer.getUser(email);
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
    public void CheckLogin()
    {
        if(preferManager.isLoggedIn())
        {  // nếu có đăng nhập hiển thị thanh logout và get lại user theo session email
            HashMap<String, String> user = preferManager.getUserDetails();
            dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));
        }
        else
        {
            Intent login=new Intent(this, LoginActivity.class);
            startActivity(login);
        }

    }

}
