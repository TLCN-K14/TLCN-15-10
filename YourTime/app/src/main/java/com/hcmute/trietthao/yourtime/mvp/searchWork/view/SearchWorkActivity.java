package com.hcmute.trietthao.yourtime.mvp.searchWork.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;
import com.hcmute.trietthao.yourtime.mvp.searchWork.presenter.SearchWorkPresenter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.hcmute.trietthao.yourtime.service.utils.NetworkUtils.isNetWorkConnected;


public class SearchWorkActivity extends AppCompatActivity implements View.OnClickListener,ISearchWork{

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Bind(R.id.etxt_search)
    EditText etSearch;

    @Bind(R.id.iv_clear_search)
    ImageView ivClearSearch;

    @Bind(R.id.progressBar_Loadmore)
    ProgressBar pbLoading;

    SearchWorkPresenter mSearchWorkPresenter;
    ArrayList<NhomCVModel> mListNhomCv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchwork);
        ButterKnife.bind(this);

        mSearchWorkPresenter = new SearchWorkPresenter(this);
        mListNhomCv = new ArrayList<>();

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
        {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        ivClearSearch.setOnClickListener(this);
        ivClearSearch.setVisibility(View.INVISIBLE);
        pbLoading.setVisibility(View.GONE);

        etSearch.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0)
                { //do your work here }
                    ivClearSearch.setVisibility(View.VISIBLE);

                    if(isNetWorkConnected(getApplicationContext())){
                        mSearchWorkPresenter.getAllWorkSearchOnline(1,etSearch.getText().toString());  // ID USER IN PREFERENCE
                    }

                }else
                    ivClearSearch.setVisibility(View.INVISIBLE);

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.iv_clear_search:
                ivClearSearch.setVisibility(View.INVISIBLE);
                etSearch.setText("");
                break;
        }
    }

    @Override
    public void getListGroupWorkSucess() {
        mListNhomCv = formatList(mSearchWorkPresenter.getListSearchOnline());
        Toast.makeText(getApplication(), ""+mListNhomCv.size(),
                Toast.LENGTH_LONG).show();
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
    public void getListGroupWorkEmpty() {
        Toast.makeText(getApplication(), "No result found!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void getListGroupWorkFail() {
        Toast.makeText(getApplication(), "Check your connection!",
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
}
