package com.hcmute.trietthao.yourtime;

import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;


import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity{

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.navigation)
    BottomNavigationView mBottomNavigationView;
    @Bind(R.id.coordinatorlayout)
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "Text", Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.AXIS_CLIP;
        view.setLayoutParams(params);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar
                        .setAction("Action", null).show();
            }
        });


        mBottomNavigationView .setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_tasks:
                                selectedFragment = TasksFragment.newInstance();
                                fab.animate().alpha(1.0f).setDuration(200);
                                fab.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_calendar:
                                selectedFragment = CalendarFragment.newInstance();
                                fab.animate().alpha(1.0f).setDuration(200);
                                fab.setVisibility(View.VISIBLE);
                                break;
                            case R.id.navigation_setting:
                                selectedFragment = SettingsFragment.newInstance();
                                fab.animate().alpha(0.0f).setDuration(200);

                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        fab.setVisibility(View.INVISIBLE);
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

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, TasksFragment.newInstance());
        transaction.commit();
    }

}
