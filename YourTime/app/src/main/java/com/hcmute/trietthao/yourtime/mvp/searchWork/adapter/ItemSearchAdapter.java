package com.hcmute.trietthao.yourtime.mvp.searchWork.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.database.DBWorkServer;
import com.hcmute.trietthao.yourtime.database.PostWorkListener;
import com.hcmute.trietthao.yourtime.model.CongViecModel;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;

import java.util.ArrayList;

import static com.hcmute.trietthao.yourtime.service.utils.DateUtils.getDisplayDate;

/**
 * Created by lxtri on 11/11/2017.
 */



public class ItemSearchAdapter extends BaseExpandableListAdapter implements PostWorkListener{

    Context context;
    ArrayList<NhomCVModel> mListNhomCV;
    DBWorkServer dbWorkServer;

    public ItemSearchAdapter(Context context, ArrayList<NhomCVModel> mListNhomCV){
        this.context = context;
        this.mListNhomCV = mListNhomCV;

    }

    @Override
    public int getGroupCount() {
        return mListNhomCV.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mListNhomCV.get(groupPosition).getCongViecModels().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListNhomCV.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListNhomCV.get(groupPosition).getCongViecModels().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return mListNhomCV.get(groupPosition).getIdNhom();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return mListNhomCV.get(groupPosition).getCongViecModels().get(childPosition).getIdCongViec();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View converView, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        converView = inflater.inflate(R.layout.item_group_header,null);
        Button btnNameGroup = (Button) converView.findViewById(R.id.item_groupwork_name);
        btnNameGroup.setText(mListNhomCV.get(groupPosition).getTenNhom());

        return converView;
    }

    @Override
    public View getChildView(final int groupPosition,final int childPosition, boolean b, View converView, ViewGroup viewGroup) {

        dbWorkServer = new DBWorkServer(this);

        final CongViecModel congViecModel = mListNhomCV.get(groupPosition).getCongViecModels().get(childPosition);
        Log.e("ItemSearchAdapter","getChildView  "+congViecModel.getTenCongViec());
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        converView = inflater.inflate(R.layout.item_work,null);
        CheckBox cbTickWork = (CheckBox) converView.findViewById(R.id.item_work_checkbox);
        TextView tvNameWork = (TextView) converView.findViewById(R.id.item_work_name);
        TextView tvTimeWork = (TextView) converView.findViewById(R.id.item_work_time);
        ImageView ivConversationWork = (ImageView) converView.findViewById(R.id.item_work_conversation);
        ImageView ivRepeatWork = (ImageView) converView.findViewById(R.id.item_work_repeat);
        ImageView ivAssignedWork = (ImageView) converView.findViewById(R.id.item_work_assigned);
        final ImageView ivPriorityWork = (ImageView) converView.findViewById(R.id.item_work_priority);

        tvNameWork.setText(congViecModel.getTenCongViec());
        tvTimeWork.setText(getDisplayDate(congViecModel.getThoiGianBatDau()));

        if(congViecModel.getCoUuTien()==1)
           ivPriorityWork.setImageResource(R.drawable.ic_priority_selected);
        else
            ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);

//        String url_imgAssigned="https://tlcn-yourtime.herokuapp.com/getimg?nameimg=avaupload";
//        Picasso.with(context).load(url_imgAssigned+arrayList.get(position).getImg()+".png")
//                .placeholder(R.drawable.fdi1)
//                .error(R.drawable.fdi1)
//                .into(holder.imgItem);
//
//        if(congViecModel.getIdNguoiDuocGiaoCV()!=null)
//            Picasso.with(context).load(imageResource).fit().centerInside().into(holder.imgItem);
//        else

        ivPriorityWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(congViecModel.getCoUuTien()==1){
                    ivPriorityWork.setImageResource(R.drawable.ic_priority_unselected);

                }
                else{
                    ivPriorityWork.setImageResource(R.drawable.ic_priority_selected);
                }
            }
        });

        cbTickWork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            removeItem(groupPosition,childPosition);
                            //dbWorkServer.updateStatusWork("done",congViecModel.getIdCongViec(),congViecModel.getThoiGianBatDau());

                        }
                    }, 1000);
                }

            }
        });

            ivAssignedWork.setVisibility(View.INVISIBLE);

        if(congViecModel.getIdNhacNho()==null)
            ivRepeatWork.setVisibility(View.INVISIBLE);
        else
            ivRepeatWork.setVisibility(View.VISIBLE);

        ivConversationWork.setVisibility(View.INVISIBLE);

        return converView;

    }

    public void removeItem(int groupPosition, int childPosition){
        mListNhomCV.get(groupPosition).getCongViecModels().remove(childPosition);
        if(mListNhomCV.get(groupPosition).getCongViecModels().size()==0)
            mListNhomCV.remove(groupPosition);
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void getResultPostWork(Boolean isSucess) {

    }
}
