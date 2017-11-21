package com.hcmute.trietthao.yourtime.mvp.tasksFragment.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBGroupWorkServer;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.createGroupWork.view.CreateGroupWorkActivity;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.DetailGroupWorkActivity;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.view.DetailGroupWorkMainActivity;
import com.hcmute.trietthao.yourtime.mvp.searchWork.view.SearchWorkViewActivity;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.adapter.GroupWorkServerAdapter;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.adapter.IOnItemGroupWorkTasksListener;
import com.hcmute.trietthao.yourtime.mvp.tasksFragment.presenter.TasksPresenter;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.converStringToDateTime;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isDateInCurrentWeek;
import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.isToday;
import static com.hcmute.trietthao.yourtime.service.utils.NetworkUtils.isNetWorkConnected;


public class TasksFragment extends Fragment implements
        View.OnClickListener,ITasksView,IOnItemGroupWorkTasksListener {

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
    LinearLayout lnlCurrentItemLongClick;


    @Bind(R.id.btn_search)
    ImageView ivSearch;

    @Bind(R.id.tv_name_item_selected)
    TextView tvNameItemSelected;

    @Bind(R.id.iv_back_longclick_groupwork)
    ImageView ivBackLongClick;

    @Bind(R.id.iv_rename_groupwork)
    ImageView ivRenameGroupWork;

    @Bind(R.id.iv_delete_groupwork)
    ImageView ivDeleteGroupWork;

    @Bind(R.id.lnl_create_groupwork)
    LinearLayout lnlCreateGroupWork;

    @Bind(R.id.lnlayout_longclick_groupwork)
    LinearLayout lnlLongClickGroupWork;

    @Bind(R.id.lnlayout_topbar)
    LinearLayout lnlTopBar;

    @Bind(R.id.list_groupworks)
    RecyclerView rvListGroupWork;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    PreferManager preferManager;
    TasksPresenter mTasksPresenter;

    GroupWorkServerAdapter mGroupWorkServerAdapter;
    DBGroupWorkServer dbGroupWorkServer;
    NhomCVModel currentGroupWorkLongClick;

    private ArrayList<NhomCVModel> mListNhomCV;
    private ArrayList<CongViecModel> mListCV;

    boolean isLongClicking = false;

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

        txtDayCurrent = headerTasksView.findViewById(R.id.day_current);
        txtNameUser=view.findViewById(R.id.name_user);
        imgUser=view.findViewById(R.id.image_user);

        rvListGroupWork.setLayoutManager(new LinearLayoutManager(getContext()));
        rvListGroupWork.setHasFixedSize(true);

        mTasksPresenter = new TasksPresenter(this);
        initData();

        lnlCreateGroupWork.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivBackLongClick.setOnClickListener(this);
        ivRenameGroupWork.setOnClickListener(this);
        ivDeleteGroupWork.setOnClickListener(this);

        lnlLongClickGroupWork.setVisibility(View.GONE);

        setupHeaderTask(headerTasksView);

        setTxtDayCurrent();

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
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }



    protected void initData() {
        if(isNetWorkConnected(getContext())){
            mTasksPresenter.getAllGroupWorkOnline(1);
            mTasksPresenter.getAllWorkOnline(1);
        }
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
            case  R.id.btn_search:
                intent = new Intent(getContext(), SearchWorkViewActivity.class);
                startActivity(intent);
                break;
            case  R.id.iv_rename_groupwork:
                Toast.makeText(getActivity(), "Rename-----"+currentGroupWorkLongClick.getTenNhom(),
                        Toast.LENGTH_LONG).show();
                break;
            case  R.id.iv_delete_groupwork:
                Toast.makeText(getActivity(), "Delete-----"+currentGroupWorkLongClick.getTenNhom(),
                        Toast.LENGTH_LONG).show();
                break;

            case  R.id.iv_back_longclick_groupwork:
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
                isLongClicking = false;
                lnlCurrentItemLongClick = null;
                currentGroupWorkLongClick = null;
                lnlLongClickGroupWork.setVisibility(View.GONE);
                lnlTopBar.setVisibility(View.VISIBLE);

                tvNameItemSelected.setText("");
//                mGroupWorkServerAdapter = new GroupWorkServerAdapter(getActivity(),mListNhomCV,this,this);
//                rvListGroupWork.setAdapter(mGroupWorkServerAdapter);
//                mGroupWorkServerAdapter.notifyDataSetChanged();
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

        txtAssignedToMeCountAll = view.findViewById(R.id.txt_assigned_count_all);
        txtAssignedToMeCountOverDue = view.findViewById(R.id.txt_assigned_count_overdue);


        txtStarredCountAll = view.findViewById(R.id.txt_starred_count_all);
        txtStarredCountOverDue = view.findViewById(R.id.txt_starred_count_overdue);

        txtTodayCountAll = view.findViewById(R.id.txt_today_count_all);
        txtTodayCountOverDue = view.findViewById(R.id.txt_today_count_overdue);

        txtWeekCountAll = view.findViewById(R.id.txt_week_count_all);
        txtWeekCountOverDue = view.findViewById(R.id.txt_week_count_overdue);


        txtAllCountAll = view.findViewById(R.id.txt_all_count_all);
        txtAllCountOverDue = view.findViewById(R.id.txt_all_count_overdue);


        lnlInbox.setOnClickListener(this);
        lnlAssignedToMe.setOnClickListener(this);
        lnlStarred.setOnClickListener(this);
        lnlToday.setOnClickListener(this);
        lnlWeek.setOnClickListener(this);
        lnlAll.setOnClickListener(this);
        lnlCompleted.setOnClickListener(this);
    }


    @Override
    public void showLoading() {
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void getAllGroupWorkSuccess() {
        mListNhomCV = mTasksPresenter.getListGroupWorkOnline();
        mGroupWorkServerAdapter = new GroupWorkServerAdapter(getActivity(),mListNhomCV,this);
        rvListGroupWork.setAdapter(mGroupWorkServerAdapter);
    }

    @Override
    public void getListAllWorkSucess() {
        mListCV = mTasksPresenter.getListAllWorkOnline();
        setupCountWork();
    }

    @Override
    public void getAllGroupWorkFailure() {
        Toast.makeText(getActivity(), "Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    public void setupCountWork(){
        setupInbox();
        setupAssignedToMe();
        setupToday();
        setupWeek();
        setupStarred();
        setupAll();

    }

    public void setupInbox(){
        int overdue=0,all=0,completed=0;
        if(mListCV.size()>0){
            for(int i=0;i<mListCV.size();i++){
                if(mListCV.get(i).getIdNhom()==0){  // mac dinh nhom co id=0 la nhom inbox
                    all++;
                    if(mListCV.get(i).getTrangThai().equals("overdue"))
                        overdue++;
                    if(mListCV.get(i).getTrangThai().equals("done"))
                        completed++;
                }
            }
        }
        if(all>0)
            txtInboxCountAll.setText(String.valueOf(all));
        else
            txtInboxCountAll.setVisibility(View.INVISIBLE);
        if(overdue>0)
            txtInboxCountOverDue.setText(String.valueOf(overdue));
        else
            txtInboxCountOverDue.setVisibility(View.INVISIBLE);
        if(completed>0)
            txtInboxCountCompleted.setText(String.valueOf(completed));
        else
            txtInboxCountCompleted.setVisibility(View.INVISIBLE);
    }

    public void setupAssignedToMe(){
        int overdue=0,all=0;
        if(mListCV.size()>0){
            for(int i=0;i<mListCV.size();i++){
                if(mListCV.get(i).getIdNguoiDuocGiaoCV()!=null){
                    if(mListCV.get(i).getIdNguoiDuocGiaoCV()==1)
                    {
                        all++;
                        if(mListCV.get(i).getTrangThai().equals("overdue"))
                            overdue++;
                    }
                }
            }
        }
        if(all>0)
            txtAssignedToMeCountAll.setText(String.valueOf(all));
        else
            txtAssignedToMeCountAll.setVisibility(View.INVISIBLE);
        if(overdue>0)
            txtAssignedToMeCountOverDue.setText(String.valueOf(overdue));
        else
            txtAssignedToMeCountOverDue.setVisibility(View.INVISIBLE);
    }

    public void setupToday(){
        int overdue=0,all=0;
        if(mListCV.size()>0){
            for(int i=0;i<mListCV.size();i++){
                try {
                    if(isToday(converStringToDateTime(mListCV.get(i).getThoiGianBatDau()))){
                        all++;
                        if(mListCV.get(i).getTrangThai().equals("overdue"))
                            overdue++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } {

                }
            }
        }
        if(all>0)
            txtTodayCountAll.setText(String.valueOf(all));
        else
            txtTodayCountAll.setVisibility(View.INVISIBLE);
        if(overdue>0)
            txtTodayCountOverDue.setText(String.valueOf(overdue));
        else
            txtTodayCountOverDue.setVisibility(View.INVISIBLE);
    }

    public void setupWeek(){
        int overdue=0,all=0;
        if(mListCV.size()>0){
            for(int i=0;i<mListCV.size();i++){
                try {
                    if(isDateInCurrentWeek(converStringToDateTime(mListCV.get(i).getThoiGianBatDau()))){
                            all++;
                            if(mListCV.get(i).getTrangThai().equals("overdue"))
                                overdue++;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                } {

                }
            }
        }
        if(all>0)
            txtWeekCountAll.setText(String.valueOf(all));
        else
            txtWeekCountAll.setVisibility(View.INVISIBLE);
        if(overdue>0)
            txtWeekCountOverDue.setText(String.valueOf(overdue));
        else
            txtWeekCountOverDue.setVisibility(View.INVISIBLE);
    }

    public void setupStarred(){
        int overdue=0,all=0;
        if(mListCV.size()>0){
            for(int i=0;i<mListCV.size();i++){
                if(mListCV.get(i).getCoUuTien()==1){
                    all++;
                    if(mListCV.get(i).getTrangThai()=="overdue")
                        overdue++;
                }
            }
        }
        if(all>0)
            txtStarredCountAll.setText(String.valueOf(all));
        else
            txtStarredCountAll.setVisibility(View.INVISIBLE);
        if(overdue>0)
            txtStarredCountOverDue.setText(String.valueOf(overdue));
        else
            txtStarredCountOverDue.setVisibility(View.INVISIBLE);
    }

    public void setupAll(){
        int overdue=0,all=0;
        if(mListCV.size()>0){
            for(int i=0;i<mListCV.size();i++){
                    all++;
                    if(mListCV.get(i).getTrangThai().equals("overdue"))
                        overdue++;
            }
        }

        if(all>0)
            txtAllCountAll.setText(String.valueOf(all));
        else
            txtAllCountAll.setVisibility(View.INVISIBLE);
        if(overdue>0)
            txtAllCountOverDue.setText(String.valueOf(overdue));
        else
            txtAllCountOverDue.setVisibility(View.INVISIBLE);
    }


    @Override
    public void onItemClick(NhomCVModel nhomCVModel,LinearLayout view) {
        if(!isLongClicking)
            Toast.makeText(getActivity(), "On Click!-----"+nhomCVModel.getTenNhom(),
                Toast.LENGTH_LONG).show();
        else{
            setupLongClick(nhomCVModel,view);
        }
    }

    @Override
    public void onItemLongClick(NhomCVModel nhomCVModel, LinearLayout view) {
        isLongClicking = true;
        setupLongClick(nhomCVModel,view);
        Toast.makeText(getActivity(), "On Long Click!-----"+nhomCVModel.getTenNhom(),
                Toast.LENGTH_LONG).show();
    }

    public void setupLongClick(NhomCVModel nhomCVModel,LinearLayout view){
        if(currentGroupWorkLongClick!=null && currentGroupWorkLongClick.getIdNhom()==nhomCVModel.getIdNhom()){
            ivBackLongClick.performClick();
        }else{
            if(lnlCurrentItemLongClick!=null){
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
            else{
                lnlTopBar.setVisibility(View.GONE);
                lnlLongClickGroupWork.setVisibility(View.VISIBLE);
            }
            currentGroupWorkLongClick = new NhomCVModel();
            lnlCurrentItemLongClick = view;
            currentGroupWorkLongClick = nhomCVModel;
            tvNameItemSelected.setText(currentGroupWorkLongClick.getTenNhom()+" seleted");
            lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#0099CC"));
        }
    }

}