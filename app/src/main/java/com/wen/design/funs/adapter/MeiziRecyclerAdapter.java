package com.wen.design.funs.adapter;

/**
 * Created by wen on 2018/2/8.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wen.design.funs.R;
import com.wen.design.funs.bean.HomeRecyclerBean;
import com.wen.design.funs.bean.MeiziRecyclerBean;

import java.util.List;

/**
 * Created by hawvu on 2018/2/6.
 */

public class MeiziRecyclerAdapter extends RecyclerView.Adapter<MeiziRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private List<MeiziRecyclerBean> mList;

    public MeiziRecyclerAdapter(List<MeiziRecyclerBean> list){
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meizi_item,parent,false);
        MeiziRecyclerAdapter.ViewHolder holder = new MeiziRecyclerAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MeiziRecyclerBean bean = mList.get(position);

        Glide.with(mContext).load(bean.getImage()).thumbnail(Glide.with(mContext).load(R.mipmap.load)).fitCenter(). crossFade().into(holder.mMeiziImageView);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mMeiziImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mMeiziImageView=(ImageView)itemView.findViewById(R.id.meiziImageView);
        }
    }
}

