package com.wen.design.funs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wen.design.funs.R;

import java.util.List;

/**
 * Created by wen on 2018/2/8.
 */

public class GalleryAdapter extends
        RecyclerView.Adapter<GalleryAdapter.ViewHolder>
{
    private LayoutInflater mInflater;
    private List<Integer> mDatas;

    public GalleryAdapter(Context context, List<Integer> datats)
    {
        mInflater = LayoutInflater.from(context);
        mDatas = datats;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View arg0)
        {
            super(arg0);
        }

        ImageView mImg;
        TextView mTxt;
    }

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = mInflater.inflate(R.layout.gallery_item,
                viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.mImg = (ImageView) view.findViewById(R.id.img1);
        return viewHolder;
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i)
    {
        viewHolder.mImg.setImageResource(mDatas.get(i));
    }

}

