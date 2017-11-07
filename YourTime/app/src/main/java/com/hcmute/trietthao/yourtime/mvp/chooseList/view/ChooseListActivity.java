package com.hcmute.trietthao.yourtime.mvp.chooseList.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.hcmute.trietthao.yourtime.MainActivity;
import com.hcmute.trietthao.yourtime.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChooseListActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_list);

        ButterKnife.bind(this);


        mBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCheckbTravel.isChecked()||mCheckbWork.isChecked()||mCheckbFamily.isChecked()||mCheckbPrivate.isChecked())
                {
                    mBtnContinue.setEnabled(true);
                    mBtnContinue.setBackground(getResources().getDrawable(R.drawable.button_blue_shadow));
                    Intent main= new Intent(ChooseListActivity.this, MainActivity.class);
                    startActivity(main);
                }
            }
        });

    }
}
