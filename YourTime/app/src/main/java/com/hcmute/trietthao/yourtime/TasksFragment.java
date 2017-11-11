package com.hcmute.trietthao.yourtime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.mvp.signUp.view.SignUpActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class TasksFragment extends Fragment implements View.OnClickListener {

    TextView txtDayCurrent;
    TextView txtInboxCountCompleted,txtInboxCountAll, txtInboxCountOverDue;
    TextView txtAssignedToMeCountAll, txtAssignedToMeCountOverDue;
    TextView txtStarredCountAll, txtStarredCountOverDue;
    TextView txtTodayCountAll, txtTodayCountOverDue;
    TextView txtWeekCountAll, txtWeekCountOverDue;
    TextView txtAllCountAll, txtAllCountOverDue;
    TextView txtNameUser;
    CircleImageView imgUser;

    LinearLayout lnlInbox, lnlAssignedToMe, lnlStarred, lnlToday, lnlWeek, lnlAll, lnlCompleted;

    @Bind(R.id.lnl_create_groupwork)
    LinearLayout lnlCreateGroupWork;

    PreferManager preferManager;




    public static TasksFragment newInstance() {
        TasksFragment fragment = new TasksFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);
        View headerTasksView = view.findViewById(R.id.header_tasks);

        ButterKnife.bind(this,view);

        SignUpActivity.FROM_SIGNUP=false;

        txtDayCurrent = headerTasksView.findViewById(R.id.day_current);
        txtNameUser=view.findViewById(R.id.name_user);
        imgUser=view.findViewById(R.id.image_user);
        Log.e("vào taskfragment:::::","");

        lnlCreateGroupWork.setOnClickListener(this);

        setupHeaderTask(headerTasksView);

        setTxtDayCurrent();

        if(SignUpActivity.FROM_SIGNUP){
            Log.e("from sign up:::::","");
            preferManager = new PreferManager(getActivity().getApplicationContext());

            if(preferManager.checkLogin())
                getActivity().finish();

            // get user data from session
            HashMap<String, String> user = preferManager.getUserDetails();
            // get name
            String userName = user.get(preferManager.KEY_NAME);

            // get email
            String userEmail = user.get(preferManager.KEY_EMAIL);

            txtNameUser.setText(userName);
            Log.e("name user::",userName);
            Log.e("useremail::", userEmail);

        }


        return view;
    }

    public void setTxtDayCurrent(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if(day>10)
            txtDayCurrent.setText(String.valueOf(day));
        else
            txtDayCurrent.setText((" "+String.valueOf(day)));
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(),"resume r ne!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        Intent intent = new Intent(getContext(), DetailGroupWorkMainActivity.class);
        switch (i){
            case R.id.lnl_inbox:
                intent = new Intent(getContext(), DetailGroupWorkActivity.class);
                intent.putExtra("EXTRA_GROUPWORK_ID", "1");
                startActivity(intent);
                break;
            case R.id.lnl_assigned_to_me:
                intent.putExtra("EXTRA_GROUPMAIN_ID", "1");
                startActivity(intent);
                break;
            case R.id.lnl_starred:
                intent.putExtra("EXTRA_GROUPMAIN_ID", "2");
                startActivity(intent);
                break;
            case R.id.lnl_today:
                intent.putExtra("EXTRA_GROUPMAIN_ID", "3");
                startActivity(intent);
                break;
            case R.id.lnl_week:
                intent.putExtra("EXTRA_GROUPMAIN_ID", "4");
                startActivity(intent);
                break;
            case R.id.lnl_all:
                intent.putExtra("EXTRA_GROUPMAIN_ID", "5");
                startActivity(intent);
                break;
            case R.id.lnl_completed:
                intent.putExtra("EXTRA_GROUPMAIN_ID", "6");
                startActivity(intent);
                break;
            case R.id.lnl_create_groupwork:
                intent = new Intent(getContext(), CreateGroupWorkActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void setupHeaderTask(View view){
        lnlInbox = view.findViewById(R.id.lnl_inbox);
        lnlAssignedToMe = view.findViewById(R.id.lnl_assigned_to_me);
        lnlStarred = view.findViewById(R.id.lnl_starred);
        lnlToday = view.findViewById(R.id.lnl_today);
        lnlWeek = view.findViewById(R.id.lnl_week);
        lnlAll = view.findViewById(R.id.lnl_all);
        lnlCompleted = view.findViewById(R.id.lnl_completed);

        txtInboxCountAll = view.findViewById(R.id.txt_inbox_count_all);
        txtInboxCountOverDue = view.findViewById(R.id.txt_inbox_count_overdue);
        txtInboxCountCompleted = view.findViewById(R.id.txt_inbox_count_completed);

        txtInboxCountAll.setVisibility(View.INVISIBLE);
        txtInboxCountOverDue.setVisibility(View.INVISIBLE);
        txtInboxCountCompleted.setVisibility(View.INVISIBLE);

        txtAssignedToMeCountAll = view.findViewById(R.id.txt_assigned_count_all);
        txtAssignedToMeCountOverDue = view.findViewById(R.id.txt_assigned_count_overdue);

        txtAssignedToMeCountAll.setVisibility(View.INVISIBLE);
        txtAssignedToMeCountOverDue.setVisibility(View.INVISIBLE);

        txtStarredCountAll = view.findViewById(R.id.txt_starred_count_all);
        txtStarredCountOverDue = view.findViewById(R.id.txt_starred_count_overdue);

        txtStarredCountAll.setVisibility(View.INVISIBLE);
        txtStarredCountOverDue.setVisibility(View.INVISIBLE);

        txtTodayCountAll = view.findViewById(R.id.txt_today_count_all);
        txtTodayCountOverDue = view.findViewById(R.id.txt_today_count_overdue);

        txtTodayCountAll.setVisibility(View.INVISIBLE);
        txtTodayCountOverDue.setVisibility(View.INVISIBLE);

        txtWeekCountAll = view.findViewById(R.id.txt_week_count_all);
        txtWeekCountOverDue = view.findViewById(R.id.txt_week_count_overdue);

        txtWeekCountAll.setVisibility(View.INVISIBLE);
        txtWeekCountOverDue.setVisibility(View.INVISIBLE);

        txtAllCountAll = view.findViewById(R.id.txt_all_count_all);
        txtAllCountOverDue = view.findViewById(R.id.txt_all_count_overdue);

        txtAllCountAll.setVisibility(View.INVISIBLE);
        txtAllCountOverDue.setVisibility(View.INVISIBLE);

        lnlInbox.setOnClickListener(this);
        lnlAssignedToMe.setOnClickListener(this);
        lnlStarred.setOnClickListener(this);
        lnlToday.setOnClickListener(this);
        lnlWeek.setOnClickListener(this);
        lnlAll.setOnClickListener(this);
        lnlCompleted.setOnClickListener(this);
    }

    public void setupCountTask(){

    }
}