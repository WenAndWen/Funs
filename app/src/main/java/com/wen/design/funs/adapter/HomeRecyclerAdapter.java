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

import java.util.List;

/**
     * Created by hawvu on 2018/2/6.
     */

    public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {

        private Context mContext;
        private List<HomeRecyclerBean> mList;

        public HomeRecyclerAdapter(List<HomeRecyclerBean> list){
            mList = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mContext == null){
                mContext = parent.getContext();
            }

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_item,parent,false);
            HomeRecyclerAdapter.ViewHolder holder = new HomeRecyclerAdapter.ViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            HomeRecyclerBean bean = mList.get(position);

            holder.mMicTitleText.setText(bean.getTitle());
            holder.mMusicAmountText.setText(bean.getAmount());
            holder.mMusicMessageText.setText(bean.getMessage());
            Glide.with(mContext).load(bean.getImage()).thumbnail(Glide.with(mContext).load(R.mipmap.load)).fitCenter().into(holder.mMusicImageView);


        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView mMicTitleText;
            TextView mMusicMessageText;
            TextView mMusicAmountText;
            ImageView mMusicImageView;

            public ViewHolder(View itemView) {
                super(itemView);

                mMicTitleText=(TextView)itemView.findViewById(R.id.musicTitleText);
                mMusicMessageText=(TextView)itemView.findViewById(R.id.musicMessageText);
                mMusicAmountText=(TextView)itemView.findViewById(R.id.musicAmountText);
                mMusicImageView=(ImageView)itemView.findViewById(R.id.musicImageView);
            }
        }
    }

