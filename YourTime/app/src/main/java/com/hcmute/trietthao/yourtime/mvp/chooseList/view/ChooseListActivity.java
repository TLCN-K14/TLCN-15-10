package com.hcmute.trietthao.yourtime.mvp.chooseList.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hcmute.trietthao.yourtime.MainActivity;
import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.mvp.chooseList.presenter.ChooseListPresenter;
import com.hcmute.trietthao.yourtime.mvp.detailGroupWork.view.DetailGroupWorkActivity;
import com.hcmute.trietthao.yourtime.prefer.PreferManager;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getIntCurrentDateTime;

public class ChooseListActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        View.OnClickListener, IChooseListView {

    @Bind(R.id.btn_choose_list_continue)
    Button mBtnContinue;
    @Bind(R.id.checkb_travel)
    CheckBox mCheckbTravel;
    @Bind(R.id.checkb_work)
    CheckBox mCheckbWork;
    @Bind(R.id.checkb_family)
    CheckBox mCheckbFamily;
    @Bind(R.id.checkb_private)
    CheckBox mCheckbPrivate;
    int dem=0;

    int idNhom;
    PreferManager mPreferManager;

    ChooseListPresenter mChooseListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);

        mChooseListPresenter= new ChooseListPresenter(this);
        mPreferManager= new PreferManager(getBaseContext());

        ButterKnife.bind(this);
        mCheckbTravel.setOnCheckedChangeListener(this);
        mCheckbWork.setOnCheckedChangeListener(this);
        mCheckbFamily.setOnCheckedChangeListener(this);
        mCheckbPrivate.setOnCheckedChangeListener(this);

        mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_low_blue_shadown));



        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(mCheckbTravel.isChecked()||mCheckbWork.isChecked()||mCheckbFamily.isChecked()||mCheckbPrivate.isChecked())
//                {
//                    Intent main= new Intent(ChooseListActivity.this, MainActivity.class);
//                    startActivity(main);
//                }
                if(mCheckbTravel.isChecked()){
                    idNhom=getIntCurrentDateTime();
                    mChooseListPresenter.insertGroupWork(idNhom,"Travel",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"Owner");
                    Intent detailWG= new Intent(ChooseListActivity.this, DetailGroupWorkActivity.class);
                    detailWG.putExtra("EXTRA_GROUPWORK_ID", String.valueOf(idNhom));
                    detailWG.putExtra("EXTRA_GROUPWORK_NAME", "Travel");
                    startActivity(detailWG);
                }
                if(mCheckbWork.isChecked()){
                    idNhom=getIntCurrentDateTime();
                    mChooseListPresenter.insertGroupWork(idNhom,"Work",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"Owner");
                    Intent detailWG= new Intent(ChooseListActivity.this, DetailGroupWorkActivity.class);
                    detailWG.putExtra("EXTRA_GROUPWORK_ID", String.valueOf(idNhom));
                    detailWG.putExtra("EXTRA_GROUPWORK_NAME", "Work");
                    startActivity(detailWG);
                }
                if(mCheckbFamily.isChecked()){
                    idNhom=getIntCurrentDateTime();
                    mChooseListPresenter.insertGroupWork(idNhom,"Family",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"Owner");
                    Intent detailWG= new Intent(ChooseListActivity.this, DetailGroupWorkActivity.class);
                    detailWG.putExtra("EXTRA_GROUPWORK_ID", String.valueOf(idNhom));
                    detailWG.putExtra("EXTRA_GROUPWORK_NAME", "Family");
                    startActivity(detailWG);
                }
                if(mCheckbPrivate.isChecked()){
                    idNhom=getIntCurrentDateTime();
                    mChooseListPresenter.insertGroupWork(idNhom,"Private",1);
                    mChooseListPresenter.insertGroupWorkUser(idNhom,mPreferManager.getID(),"Owner");
                    Intent detailWG= new Intent(ChooseListActivity.this, DetailGroupWorkActivity.class);
                    detailWG.putExtra("EXTRA_GROUPWORK_ID", String.valueOf(idNhom));
                    detailWG.putExtra("EXTRA_GROUPWORK_NAME", "Private");
                    startActivity(detailWG);
                }

            }
        });

    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();
        switch (id){
            case R.id.checkb_travel:
                if(b){
                    dem++;
                }
                else
                    dem--;
                break;
            case R.id.checkb_work:
                if(b)
                    dem++;
                else
                    dem--;
                break;
            case R.id.checkb_family:
                if(b)
                    dem++;
                else
                    dem--;
                break;
            case R.id.checkb_private:
                if(b)
                    dem++;
                else
                    dem--;
                break;

        }
        if(dem==0){
            mBtnContinue.setEnabled(false);
            mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_low_blue_shadown));
        }else {
            mBtnContinue.setEnabled(true);
            mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_blue_shadow));
        }
    }

    @Override
    public void onClick(View view) {

    }

}
