package com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.DetailWorkActivity;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.mvp.IOnItemWorkListener;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.adapter.ItemWorkServerAdapter;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.presenter.DetailGroupWorkPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.NetworkUtils.isNetWorkConnected;


public class DetailGroupWorkActivity extends AppCompatActivity implements View.OnClickListener,IDetailGroupWorkView,IOnItemWorkListener {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.txt_show_hide_completed_works)
    TextView mTxtShowHideCompletedWorks;

    @Bind(R.id.tv_name_item_work_selected)
    TextView tvNameItemWork;

    @Bind(R.id.iv_back_longclick_itemwork)
    ImageView ivBackFromLongClick;

    @Bind(R.id.iv_delete_item_work)
    ImageView ivDeleteItemWork;

    @Bind(R.id.lnlayout_longclick_work)
    LinearLayout lnlLongClickMenu;

    @Bind(R.id.list_works)
    RecyclerView rvListWork;

    @Bind(R.id.list_works_completed)
    RecyclerView rvListWorkCompleted;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    String EXTRA_GROUPWORK_ID = "";

    DetailGroupWorkPresenter mDetailGroupWorkPresenter;

    CongViecModel currentItemWork;
    LinearLayout lnlCurrentItemLongClick;
    ArrayList<CongViecModel> mListWork, mListWorkCompleted;

    ItemWorkServerAdapter itemWorkServerAdapter, itemWorkServerAdapterCompleted;

    boolean isLongClicking = false;

    boolean isShowCompletedWorks = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupwork_detail);
        ButterKnife.bind(this);

        rvListWork.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvListWork.setHasFixedSize(true);
        rvListWorkCompleted.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvListWorkCompleted.setHasFixedSize(true);

        EXTRA_GROUPWORK_ID = getIntent().getStringExtra("EXTRA_GROUPWORK_ID");
        Toast.makeText(getApplication(), "GROUPWORKID! "+EXTRA_GROUPWORK_ID,
                Toast.LENGTH_LONG).show();
        //mDetailGroupWorkPresenter = new DetailGroupWorkPresenter(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        mTxtShowHideCompletedWorks.setOnClickListener(this);
    }

    public void initData(){
        if(isNetWorkConnected(getApplication())){
            mDetailGroupWorkPresenter.getWorkByIdGroup(Integer.parseInt(EXTRA_GROUPWORK_ID));
        }
    }

    @Override
    protected void onResume() {
//        rvListWork.setAdapter((RecyclerView.Adapter)null);
//        rvListWorkCompleted.setAdapter((RecyclerView.Adapter) null);
//        initData();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.txt_show_hide_completed_works:
                if(isShowCompletedWorks){
                    mTxtShowHideCompletedWorks.setText(R.string.hide_completed_works);
                    isShowCompletedWorks=false;
                }else{
                    mTxtShowHideCompletedWorks.setText(R.string.show_completed_works);
                    isShowCompletedWorks=true;
                }
                break;
            case R.id.iv_back_longclick_itemwork:
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
                lnlCurrentItemLongClick = null;
                currentItemWork = null;
                isLongClicking = false;
                lnlLongClickMenu.setVisibility(View.GONE);
                mToolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_delete_item_work:
                Toast.makeText(getApplication(), "Delete-----"+currentItemWork.getTenCongViec(),
                        Toast.LENGTH_LONG).show();
                break;


        }
    }

    @Override
    public void getWorkByIDGroupSuccess() {
        mListWork = mDetailGroupWorkPresenter.getListWorkNormal();
        mListWorkCompleted = mDetailGroupWorkPresenter.getListWorkCompleted();

        itemWorkServerAdapter = new ItemWorkServerAdapter(getParent(),mListWork,
                mListWorkCompleted,1,this);
        itemWorkServerAdapterCompleted = new ItemWorkServerAdapter(getParent(),
                mListWork,mListWorkCompleted,2,this);
        rvListWork.setAdapter(itemWorkServerAdapter);
        rvListWorkCompleted.setAdapter(itemWorkServerAdapterCompleted);
    }

    @Override
    public void getWorkByIDGroupFail() {
        Toast.makeText(getApplication(), "getWorkByIDGroupFail! Check your connection!",
                Toast.LENGTH_LONG).show();
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
    public void insertWorkSuccess() {

    }

    @Override
    public void insertWorkFail() {
        Toast.makeText(getApplication(), "insertWorkFail! Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(CongViecModel congViecModel, LinearLayout view) {
        if(!isLongClicking){
            Intent intent = new Intent(getApplicationContext(), DetailWorkActivity.class);
            intent.putExtra("EXTRA_WORK_ID", congViecModel.getIdCongViec().toString());
            startActivity(intent);
        }
        else{
            setupLongClick(congViecModel,view);
        }
    }

    @Override
    public void onItemLongClick(CongViecModel congViecModel, LinearLayout view) {
        isLongClicking = true;
        setupLongClick(congViecModel,view);
    }

    public void setupLongClick(CongViecModel congViecModel,LinearLayout view){
        if(currentItemWork!=null && currentItemWork.getIdCongViec()==congViecModel.getIdCongViec()){
            ivBackFromLongClick.performClick();
        }else{
            if(lnlCurrentItemLongClick!=null){
                lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#FAFAFA"));
            }
            else{
                mToolbar.setVisibility(View.GONE);
                lnlLongClickMenu.setVisibility(View.VISIBLE);
            }
            currentItemWork = new CongViecModel();
            lnlCurrentItemLongClick = view;
            currentItemWork = congViecModel;
            tvNameItemWork.setText(currentItemWork.getTenCongViec()+" seleted");
            lnlCurrentItemLongClick.setBackgroundColor(Color.parseColor("#0099CC"));
        }
    }
}
