package com.hcmute.trietthao.yourtime.mvp.createGroupWork.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.createGroupWork.presenter.CreateGroupWorkPresenter;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.DetailGroupWorkActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;


public class CreateGroupWorkActivity extends AppCompatActivity implements View.OnClickListener,
        ICreateGroupWorkView{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.edit_group_name)
    EditText mEditGroupName;
    @Bind(R.id.imgv_save)
    ImageView mImgvSave;

    int idNhom,laNhomCaNhan;
    String tenNhom;
    PreferManager mPreferManager;

    CreateGroupWorkPresenter mCreateGroupWorkPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_groupwork);

        ButterKnife.bind(this);
        mImgvSave.setOnClickListener(this);
        mCreateGroupWorkPresenter=new CreateGroupWorkPresenter(this);
        mPreferManager=new PreferManager(getBaseContext());

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_save:
                tenNhom=mEditGroupName.getText().toString();
                idNhom=getIntCurrentDateTime();
                mCreateGroupWorkPresenter.insertGroupWork(idNhom,tenNhom,1);
                mCreateGroupWorkPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"Owner");
                mCreateGroupWorkPresenter.getGroupById(idNhom);
                Intent detailWG= new Intent(CreateGroupWorkActivity.this, DetailGroupWorkActivity.class);
                detailWG.putExtra("EXTRA_GROUPWORK_ID", String.valueOf(idNhom));
                detailWG.putExtra("EXTRA_GROUPWORK_NAME", tenNhom);
                startActivity(detailWG);
                break;
        }
    }

    @Override
    public void createGroupWorkSuccess() {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void createGroupWorkFail() {
        Toast.makeText(this,"Tạo nhóm công việc thất bại!", Toast.LENGTH_LONG).show();
    }

}
