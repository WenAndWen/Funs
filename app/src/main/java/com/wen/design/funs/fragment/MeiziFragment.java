package com.wen.design.funs.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wen.design.funs.R;
import com.wen.design.funs.adapter.MeiziRecyclerAdapter;
import com.wen.design.funs.bean.MeiziRecyclerBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MeiziFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<MeiziRecyclerBean> mList;
    private Handler hander1;
    private MeiziRecyclerAdapter adapter;
    private Element ele;

    //回调接口
    public interface ScrollListener {
        void listener(int scroll);
    }
    private ScrollListener mListener;

    public void setScrollListener(ScrollListener mListener){
        this.mListener = mListener;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_b, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_b);

        RecyclerView.LayoutManager layout=new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layout);

        adapter = new MeiziRecyclerAdapter(list());
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setOnScrollListener(new HideScrollListener());
        hander1 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Toast.makeText(getActivity(), msg.getData().getString("picText"), Toast.LENGTH_SHORT).show();
                //添加模拟数据到第一项
                //mMusicMessage.add(0, msg.getData().getString("messageText"));//接受msg传递过来的参数);
                //mMusicTitle.add(0, msg.getData().getString("titleText"));//接受msg传递过来的参数);
                // mMusicPicUrl.add(0, msg.getData().getString("picText"));//接受msg传递过来的参数);
                //RecyclerView列表进行UI数据更新
                MeiziRecyclerBean bean = new MeiziRecyclerBean( msg.getData().getString("picText")
                );
                mList.add(bean);
                adapter.notifyItemInserted(0);
                //如果在第一项添加模拟数据需要调用 scrollToPosition（0）把列表移动到顶端（可选）
                mRecyclerView.scrollToPosition(0);


                super.handleMessage(msg);
            }
        };
        //子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                parseMusic();
            }
        }).start();


        return view;
    }

    private List<MeiziRecyclerBean> list() {
        mList = new ArrayList<>();

       /* for (int i = 0;i < 20;i++){
            HomeRecyclerBean bean = new HomeRecyclerBean("自定义回调隐藏BottomNavigationView");
            mList.add(bean);
        }*/
        return mList;
    }

    //滑动监听
    class HideScrollListener extends RecyclerView.OnScrollListener{
        private static final int HIDE_HEIGHT=40;
        private int scrolledInstance=0;
        private boolean toolbarVisible=true;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if ((toolbarVisible&&dy>0)||!toolbarVisible&&dy<0){
                //recycler向上滚动时dy为正，向下滚动时dy为负数
                scrolledInstance+=dy;
            }
            if (scrolledInstance>HIDE_HEIGHT&&toolbarVisible){//当recycler向上滑动距离超过设置的默认值并且toolbar可见时，隐藏toolbar和fab
                onHide();
                scrolledInstance=0;
                toolbarVisible=false;
            }else if (scrolledInstance<-HIDE_HEIGHT&&!toolbarVisible){//当recycler向下滑动距离超过设置的默认值并且toolbar不可见时，显示toolbar和fab
                onShow();
                scrolledInstance=0;
                toolbarVisible=true;
            }
        }
    }

    private void onHide() {
        mListener.listener(0);
    }

    private void onShow() {
        mListener.listener(1);
    }
    public void parseMusic() {
        try {
            Document doc = Jsoup.connect("http://www.aidewc.com/sort/Android_jc").get();
            Elements elements2 = doc.getElementsByClass("posts-gallery-img");
           //#FLY > section > div > div > article:nth-child(1) > div > div.posts-gallery-img > a > img
            for (int i = 0;i < elements2.size();i++)
            {

                //获取美女图片
                String musicPic = elements2.get(i).select("a").select("img").text();
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                //往Bundle中存放数据i
                bundle.putString("picText",musicPic);
                msg.setData(bundle);
                hander1.sendMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }}
