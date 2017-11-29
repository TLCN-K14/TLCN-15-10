package com.hcmute.trietthao.yourtime;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hcmute.trietthao.yourtime.database.DBNguoiDungServer;
import com.hcmute.trietthao.yourtime.model.NguoiDungModel;
import com.hcmute.trietthao.yourtime.mvp.login.view.LoginActivity;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.view.TasksFragment;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;



public class MainActivity extends AppCompatActivity implements DBNguoiDungServer.userListener,View.OnClickListener {

    @Bind(R.id.fab_create_work)
    FloatingActionButton mFabCreateWork;
    @Bind(R.id.navigation)
    BottomNavigationView mBottomNavigationView;

    RelativeLayout rlBottomSheetChooseGroup;
    ImageView ivBottomSheetSetReminderStart, ivBottomSheetSetReminderEnd, ivBottomSheetSetPriority, ivBottomSheetImgGroup;
    TextView tvBottomSheetAddWork,tvBottomSheetCancel, tvBottomSheetNameGroup;
    EditText etNameWork;

    ScreenUtils screenUtils;

    View mBottomSheetCreateWorkView;
    PreferManager preferManager;

    DBNguoiDungServer dbNguoiDungServer;
    HashMap<String, String> user;

    BottomSheetDialog bottomSheetDialog;

    AlertDialog.Builder dialogReminder;
    LayoutInflater layoutInflaterRemider;
    View viewRemider;
    AlertDialog alertDialogRemider;
    TextView tvSaveRemider, tvRemoveRemider, tvReminder, tvTitleRemider;
    LinearLayout lnlTimeReminder;

    Calendar timeReminderStart;
    Calendar timeReminderEnd;

    boolean isPriority = false;

    public static NguoiDungModel userCurrent=null;
    private int MAIN_REQ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);

        setupBottomSheetView();
        preferManager = new PreferManager(getApplicationContext());
        dbNguoiDungServer=new DBNguoiDungServer(this);
        Log.e("email:::::::::",preferManager.KEY_EMAIL);
        if(preferManager.isLoggedIn()) {
            HashMap<String, String> user = preferManager.getUserDetails();
            dbNguoiDungServer.getUser(user.get(preferManager.KEY_EMAIL));
        }
        else
        {
            Intent login= new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(login,MAIN_REQ);
        }

        rlBottomSheetChooseGroup.setOnClickListener(this);
        ivBottomSheetSetReminderStart.setOnClickListener(this);
        ivBottomSheetSetReminderEnd.setOnClickListener(this);
        ivBottomSheetSetPriority.setOnClickListener(this);
        tvBottomSheetAddWork.setOnClickListener(this);
        tvBottomSheetCancel.setOnClickListener(this);


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

        bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        bottomSheetDialog.setContentView(mBottomSheetCreateWorkView);
        bottomSheetDialog.setCancelable (true);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) mBottomSheetCreateWorkView.getParent());
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

        screenUtils = new ScreenUtils(getApplicationContext());
        bottomSheetBehavior.setPeekHeight(screenUtils.getHeight()*80/100);

        rlBottomSheetChooseGroup = mBottomSheetCreateWorkView.findViewById(R.id.lnl_bottomsheet_choooselist);
        ivBottomSheetSetReminderStart = mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_reminder_start);
        ivBottomSheetSetReminderEnd =  mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_reminder_end);
        ivBottomSheetImgGroup =  mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_imggroup);
        ivBottomSheetSetPriority = mBottomSheetCreateWorkView.findViewById(R.id.img_bottomsheet_setpriority);
        tvBottomSheetAddWork =  mBottomSheetCreateWorkView.findViewById(R.id.txt_bottomsheet_add);
        tvBottomSheetCancel = mBottomSheetCreateWorkView.findViewById(R.id.txt_bottomsheet_cancel);
        tvBottomSheetNameGroup =  mBottomSheetCreateWorkView.findViewById(R.id.txt_bottomsheet_namegroup);
        etNameWork =  mBottomSheetCreateWorkView.findViewById(R.id.etxt_name_work);

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
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                // Cập nhập lại user với thông tin mới
                dbNguoiDungServer = new DBNguoiDungServer(this);
                preferManager = new PreferManager(getBaseContext());
                user = preferManager.getUserDetails();
                dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));
            }
        }
        if(requestCode==2){
            if(resultCode==RESULT_OK){
                // Cập nhập lại user với thông tin mới
                dbNguoiDungServer = new DBNguoiDungServer(this);
                preferManager = new PreferManager(getBaseContext());
                user = preferManager.getUserDetails();
                dbNguoiDungServer.getUser(user.get(PreferManager.KEY_EMAIL));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void getListUser(ArrayList<NguoiDungModel> listUser) {
        String url = "http://192.168.43.219:8000/getimg?nameimg=";
        String url_imgitem="https://foody-trietv2.herokuapp.com/getimg?nameimg=";
        userCurrent = listUser.get(0);
//        if(userCurrent.getImg()!=null)
//        {
//            Picasso.with(this).load(url_imgitem+userCurrent.getImg()+".png")
//                    .error(R.drawable.fdi1)
//                    .into(avatar_user);
//        }
//        name_user.setText(userCurrent.getUsername());
//        Log.e("","Anh avatar: "+url_imgitem+userCurrent.getImg()+".png");

    }

    @Override
    public void getResultInsert(Boolean isSuccess) {

    }

    @Override
    public void getUser(NguoiDungModel user) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lnl_bottomsheet_choooselist:
            break;
            case R.id.img_bottomsheet_reminder_start:
                setupDialog();
                timeReminderStart = Calendar.getInstance();

                tvTitleRemider.setText("Set Date & Time Start");
                tvReminder.setText("Reminder at "+timeReminderStart.getTime().getHours()+":"
                        +timeReminderStart.getTime().getMinutes());

                tvSaveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // save data
                        alertDialogRemider.dismiss();

                    }
                });

                tvRemoveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // remove time
                        alertDialogRemider.dismiss();

                    }
                });

                lnlTimeReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                timeReminderStart.set(Calendar.HOUR_OF_DAY, selectedHour);
                                timeReminderStart.set(Calendar.MINUTE, selectedMinute);
                                tvReminder.setText("Reminder at "+selectedHour+":"
                                        +selectedMinute);
                            }
                        }, timeReminderStart.getTime().getHours(), timeReminderStart.getTime().getMinutes(), true);

                        mTimePicker.setTitle("Time reminder");
                        mTimePicker.show();
                    }
                });

                alertDialogRemider.show();

                break;

            case R.id.img_bottomsheet_reminder_end:
                setupDialog();
                timeReminderEnd = Calendar.getInstance();

                tvTitleRemider.setText("Set Date & Time End");
                tvReminder.setText("Reminder at "+timeReminderEnd.getTime().getHours()+":"
                        +timeReminderEnd.getTime().getMinutes());

                tvSaveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // save data
                        alertDialogRemider.dismiss();

                    }
                });

                tvRemoveRemider.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // remove time
                        alertDialogRemider.dismiss();

                    }
                });

                lnlTimeReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TimePickerDialog mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                timeReminderEnd.set(Calendar.HOUR_OF_DAY, selectedHour);
                                timeReminderEnd.set(Calendar.MINUTE, selectedMinute);
                                tvReminder.setText("Reminder at "+selectedHour+":"
                                        +selectedMinute);
                            }
                        }, timeReminderEnd.getTime().getHours(), timeReminderEnd.getTime().getMinutes(), true);

                        mTimePicker.setTitle("Time reminder");
                        mTimePicker.show();
                    }
                });

                alertDialogRemider.show();
                break;
            case R.id.img_bottomsheet_setpriority:
                if(isPriority){
                    isPriority = false;
                    ivBottomSheetSetPriority.setImageResource(R.drawable.ic_priority_off);
                }else{
                    isPriority = true;
                    ivBottomSheetSetPriority.setImageResource(R.drawable.ic_priority_on);
                }
            break;
            case R.id.txt_bottomsheet_add:
                bottomSheetDialog.cancel();
                break;
            case R.id.txt_bottomsheet_cancel:
                bottomSheetDialog.cancel();
                break;
        }
    }

    public void setupDialog(){
        dialogReminder = new AlertDialog.Builder(this);
        dialogReminder.setCancelable(true);
        layoutInflaterRemider = LayoutInflater.from((getBaseContext()));
        viewRemider = layoutInflaterRemider.inflate(R.layout.dialog_reminder, null);

        dialogReminder.setView(viewRemider);
        alertDialogRemider = dialogReminder.create();

        tvSaveRemider = viewRemider.findViewById(R.id.tv_save);
        tvRemoveRemider =  viewRemider.findViewById(R.id.tv_remove);
        tvReminder = viewRemider.findViewById(R.id.tv_time_reminder);
        tvTitleRemider = viewRemider.findViewById(R.id.tv_title);
        lnlTimeReminder = viewRemider.findViewById(R.id.lnl_time_reminder);
    }
}
