package com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWorkMain.presenter.DetailGroupWorkMainPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class DetailGroupWorkMainActivity extends AppCompatActivity implements View.OnClickListener,IDetailGroupWorkMainView{

    Toolbar mToolbar;

    @Bind(R.id.lnl_groupempty)
    LinearLayout mLnlGroupEmpty;

    @Bind(R.id.img_groupempty)
    ImageView mImgGroupEmpty;

    @Bind(R.id.iv_back_longclick_itemwork)
    ImageView ivBackFromLongClick;

    @Bind(R.id.txt_groupempty)
    TextView mTxtGroupEmpty;

    @Bind(R.id.lnlayout_longclick_work)
    LinearLayout lnlLongClickMenu;

    @Bind(R.id.list_groupworks_works)
    ExpandableListView elListGroupWorkMain;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    ArrayList<NhomCVModel> mListNhomCV;
    String EXTRA_GROUPMAIN_ID = "";

    DetailGroupWorkMainPresenter mdetailGroupWorkMainPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupworkmain_detail);
        ButterKnife.bind(this);

        EXTRA_GROUPMAIN_ID = getIntent().getStringExtra("EXTRA_GROUPMAIN_ID");
        mdetailGroupWorkMainPresenter = new DetailGroupWorkMainPresenter(this);
        mdetailGroupWorkMainPresenter.getAllWorkOnline(1,EXTRA_GROUPMAIN_ID);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void getDetailGroupWorkSuccess() {
        mListNhomCV = formatList(mdetailGroupWorkMainPresenter.getListNhomCV());
        if(mListNhomCV.size()!=0){
            int dem=0;
            for(int i=0;i<mListNhomCV.size();i++){
                    dem+=mListNhomCV.get(i).getCongViecModels().size();
            }
            Toast.makeText(getApplication(), "Number of work: "+dem,
                    Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(getApplication(), "Number of work: 0",
                    Toast.LENGTH_LONG).show();
        switch (EXTRA_GROUPMAIN_ID) {
            case "1":
                Log.e("Size:","****************** "+mListNhomCV.size());
                if(mListNhomCV.size()==0){
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_assigned_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_assigned_empty));
                }

                break;
            case "2":
                Log.e("Size:","****************** "+mListNhomCV.size());
                if(mListNhomCV.size()==0){
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_starred_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_starred_empty));
                }

                break;
            case "3":
                if(mListNhomCV.size()==0){
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_today_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_today_empty));
                }

                break;
            case "4":
                if(mListNhomCV.size()==0){
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_week_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_week_empty));
                }

                break;
            case "5":
                if(mListNhomCV.size()==0){
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_all_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_all_empty));
                }

                break;
            case "6":
                if(mListNhomCV.size()==0){
                    mImgGroupEmpty.setBackground(getResources().getDrawable(R.drawable.ic_completed_white));
                    mTxtGroupEmpty.setText(getResources().getString(R.string.groupwork_completed_empty));
                }

                break;
        }
    }

    public ArrayList<NhomCVModel> formatList(ArrayList<NhomCVModel> nhomCVModelArrayList){
        ArrayList<NhomCVModel> temp = new ArrayList<NhomCVModel>();
        for(int i=0;i<nhomCVModelArrayList.size();i++){
            if(nhomCVModelArrayList.get(i).getCongViecModels().size()!=0)
                temp.add(nhomCVModelArrayList.get(i));
        }
        return temp;
    }

    @Override
    public void getDetailGroupWorkFail() {
        Toast.makeText(getApplication(), "Check your connection!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        mLnlGroupEmpty.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mLnlGroupEmpty.setVisibility(View.VISIBLE);
        pbLoading.setVisibility(View.GONE);
    }
}
