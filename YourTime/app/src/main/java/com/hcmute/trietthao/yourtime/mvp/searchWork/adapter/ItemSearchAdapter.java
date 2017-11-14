package com.hcmute.trietthao.yourtime.mvp.searchWork.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcmute.trietthao.yourtime.R;
import com.hcmute.trietthao.yourtime.model.NhomCVModel;

import java.util.ArrayList;

/**
 * Created by lxtri on 11/11/2017.
 */



public class ItemSearchAdapter extends RecyclerView.Adapter<ItemSearchAdapter.GroupWorkServerViewHolder> {

    private ArrayList<NhomCVModel> nhomCVModelList;
    private Activity activity;
    /**Contructor*/
    public ItemSearchAdapter(Activity activity, ArrayList<NhomCVModel> nhomCVModelList) {
        this.activity = activity;
        this.nhomCVModelList = nhomCVModelList;
    }
    /** Create ViewHolder*/
    public class GroupWorkServerViewHolder extends  RecyclerView.ViewHolder {
        private ImageView ivGroupWork;
        private TextView tvGroupWork;
        private TextView tvGroupWorkAll;
        private TextView tvGroupWorkOverDue;

        public GroupWorkServerViewHolder(View itemView) {
            super(itemView);
            ivGroupWork = (ImageView) itemView.findViewById(R.id.item_groupwork_image);
            tvGroupWork = (TextView) itemView.findViewById(R.id.item_groupwork_namegroup);
            tvGroupWorkAll = (TextView) itemView.findViewById(R.id.item_groupwork_all);
            tvGroupWorkOverDue = (TextView) itemView.findViewById(R.id.item_groupwork_overdue);
        }
    }
    @Override
    public GroupWorkServerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /** Get layout */
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_groupwork,parent,false);
        return new GroupWorkServerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupWorkServerViewHolder holder, int position) {
        /** Set Value*/
        NhomCVModel nhomCVModel= nhomCVModelList.get(position);
        if(nhomCVModel.getLaNhomCaNhan()==1)
            holder.ivGroupWork.setImageResource(R.drawable.ic_groupnormal);
        else
            holder.ivGroupWork.setImageResource(R.drawable.ic_groupassigned);
        holder.tvGroupWork.setText(nhomCVModel.getTenNhom());
        if(nhomCVModel.getSoCV()>0)
            holder.tvGroupWorkAll.setText(nhomCVModel.getSoCV().toString());
        else
            holder.tvGroupWorkAll.setVisibility(View.INVISIBLE);
        if(nhomCVModel.getSoCVQuaHan()>0)
            holder.tvGroupWorkOverDue.setText(nhomCVModel.getSoCVQuaHan().toString());
        else
            holder.tvGroupWorkOverDue.setVisibility(View.INVISIBLE);
   /*Sự kiện click vào item*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return nhomCVModelList.size();
    }
}

