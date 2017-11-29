package com.hcmute.trietthao.yourtime.mvp.detailWork.view;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lxtri on 24/10/2017.
 */

public class DetailWorkActivity extends AppCompatActivity implements View.OnClickListener,PostWorkListener{

    @Bind(R.id.tv_name_work)
    TextView tvNameWork;

    @Bind(R.id.tv_name_assignto)
    TextView tvNameAssignTo;

    @Bind(R.id.tv_time_reminder_start)
    TextView tvTimeReminderStart;

    @Bind(R.id.tv_time_reminder_end)
    TextView tvTimeReminderEnd;

    @Bind(R.id.tv_repeat)
    TextView tvRepeat;

    @Bind(R.id.tv_note)
    TextView tvNote;

    @Bind(R.id.tv_name_img)
    TextView tvNameFile;

    @Bind(R.id.iv_img_assignto)
    ImageView ivImgAssignTo;

    @Bind(R.id.iv_img_time_reminder_start)
    ImageView ivImgTimeReminderStart;

    @Bind(R.id.iv_img_time_reminder_end)
    ImageView ivImgTimeReminderEnd;

    @Bind(R.id.iv_img_repeat)
    ImageView ivImgRepeat;

    @Bind(R.id.iv_img_add_note)
    ImageView ivImgAddNote;

    @Bind(R.id.iv_img_add_picture)
    ImageView ivImgAddFile;

    @Bind(R.id.iv_img_priority)
    ImageView ivImgPriority;

    @Bind(R.id.iv_delete_assignto)
    ImageView ivDeleteAssignTo;

    @Bind(R.id.iv_delete_time_reminder_start)
    ImageView ivDeleteTimeReminderStart;

    @Bind(R.id.iv_delete_time_reminder_end)
    ImageView ivDeleteTimeReminderEnd;

    @Bind(R.id.iv_img_picture)
    ImageView ivImgPicute;

    @Bind(R.id.iv_delete_repeat)
    ImageView ivDeleteRepeat;

    @Bind(R.id.iv_delete_picture)
    ImageView ivDeleteFile;

    @Bind(R.id.iv_img_back)
    ImageView ivBack;

    @Bind(R.id.lnl_add_a_comment)
    LinearLayout lnlAddComment;

    @Bind(R.id.lnl_assignto)
    LinearLayout lnlAssignTo;

    @Bind(R.id.lnl_time_reminder_start)
    LinearLayout lnlTimeReminderStart;

    @Bind(R.id.lnl_time_reminder_end)
    LinearLayout lnlTimeReminderEnd;

    @Bind(R.id.lnl_repeat)
    LinearLayout lnlRepeat;

    @Bind(R.id.lnl_add_picture)
    LinearLayout lnlAddFile;

    @Bind(R.id.lnl_add_note)
    LinearLayout lnlAddNote;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    String EXTRA_WORK_ID = "";
    String EXTRA_GROUPWORK_ID = "";
    String EXTRA_GROUPWORK_NAME = "";

    PreferManager mPreferManager;
    CongViecModel mCongViec;
    DBWorkServer dbWorkServer;

    AlertDialog.Builder dialogReminder;
    LayoutInflater layoutInflaterRemider;
    View viewRemider;
    AlertDialog alertDialogRemider;
    TextView tvSaveRemider, tvRemoveRemider, tvReminder, tvTitleRemider;
    LinearLayout lnlTimeReminder;

    Calendar timeReminderStart;
    Calendar timeReminderEnd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);
        ButterKnife.bind(this);

        mPreferManager = new PreferManager(getApplicationContext());
        dbWorkServer = new DBWorkServer(this);


        EXTRA_WORK_ID = getIntent().getStringExtra("EXTRA_WORK_ID");
        EXTRA_GROUPWORK_ID = getIntent().getStringExtra("EXTRA_GROUPWORK_ID");

        ivDeleteAssignTo.setVisibility(View.INVISIBLE);
        ivDeleteTimeReminderStart.setVisibility(View.INVISIBLE);
        ivDeleteTimeReminderEnd.setVisibility(View.INVISIBLE);
        ivDeleteRepeat.setVisibility(View.INVISIBLE);
        ivDeleteFile.setVisibility(View.INVISIBLE);

        lnlAssignTo.setOnClickListener(this);
        lnlAddComment.setOnClickListener(this);
        lnlTimeReminderStart.setOnClickListener(this);
        lnlTimeReminderEnd.setOnClickListener(this);
        lnlRepeat.setOnClickListener(this);
        lnlAddFile.setOnClickListener(this);
        lnlAddNote.setOnClickListener(this);
        ivImgPriority.setOnClickListener(this);
        ivDeleteAssignTo.setOnClickListener(this);
        ivDeleteTimeReminderStart.setOnClickListener(this);
        ivDeleteTimeReminderEnd.setOnClickListener(this);
        ivDeleteRepeat.setOnClickListener(this);
        ivDeleteFile.setOnClickListener(this);
        ivBack.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.lnl_assignto: break;
            case R.id.lnl_time_reminder_start:
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
                        TimePickerDialog mTimePicker = new TimePickerDialog(DetailWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            case R.id.lnl_time_reminder_end:
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
                        TimePickerDialog mTimePicker = new TimePickerDialog(DetailWorkActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
            case R.id.lnl_repeat: break;
            case R.id.lnl_add_note: break;
            case R.id.lnl_add_picture: break;
            case R.id.lnl_add_a_comment: break;

            case R.id.iv_delete_assignto: break;
            case R.id.iv_delete_time_reminder_start: break;
            case R.id.iv_delete_time_reminder_end: break;
            case R.id.iv_delete_repeat: break;
            case R.id.iv_delete_picture: break;

            case R.id.iv_img_priority:
                if(mCongViec.getCoUuTien()==1){
                    mCongViec.setCoUuTien(0);
                    ivImgPriority.setImageResource(R.drawable.ic_priority_unselected);
                    dbWorkServer.updatePriorityWork(0,mCongViec.getIdCongViec());
                }else{
                    mCongViec.setCoUuTien(1);
                    ivImgPriority.setImageResource(R.drawable.ic_priority_selected);
                    dbWorkServer.updatePriorityWork(1,mCongViec.getIdCongViec());
                }

                break;
            case R.id.iv_img_back:
                Intent data = new Intent();
                data.putExtra("EXTRA_GROUPWORK_ID",EXTRA_GROUPWORK_ID);
                data.putExtra("EXTRA_GROUPWORK_NAME",EXTRA_GROUPWORK_NAME);
                setResult(RESULT_OK,data);
                finish();
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

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }
}
