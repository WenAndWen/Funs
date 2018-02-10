package com.wen.design.funs.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wen.design.funs.R;
import com.wen.design.funs.adapter.GalleryAdapter;
import com.wen.design.funs.adapter.HomeRecyclerAdapter;
import com.wen.design.funs.bean.HomeRecyclerBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<HomeRecyclerBean> mList;
    private Handler hander;
    private HomeRecyclerAdapter adapter;
    private Element ele;
    private RecyclerView mRecyclerViewb;
    private GalleryAdapter mAdapter;
    private List<Integer> mDatas;
    private NestedScrollView mNestedScrollView;

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
        View view = inflater.inflate(R.layout.fragment_a, container, false);
        mDatas = new ArrayList<>(Arrays.asList(R.mipmap.img1,
                R.mipmap.img2, R.mipmap.img3, R.mipmap.img1, R.mipmap.img2,
                R.mipmap.img3, R.mipmap.img1, R.mipmap.img2, R.mipmap.img3));
        mNestedScrollView=(NestedScrollView)view.findViewById(R.id.scrollView);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    // 向下滑动
                    onShow();
                }

                if (scrollY < oldScrollY) {
                    // 向上滑动
                    onHide();
                }

                if (scrollY == 0) {
                    onShow();
                    // 顶部
                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 底部
                }
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_a);
        mRecyclerViewb = (RecyclerView) view.findViewById(R.id.recycler_b);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerViewb.setLayoutManager(linearLayoutManager);
        //设置适配器
        mAdapter = new GalleryAdapter(getActivity(), mDatas);
        mRecyclerViewb.setAdapter(mAdapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

         adapter = new HomeRecyclerAdapter(list());
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.setOnScrollListener(new HideScrollListener());
        hander = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //Toast.makeText(ScrollingActivity.this, msg.getData().getString("urlText"), Toast.LENGTH_SHORT).show();
                //添加模拟数据到第一项
                //mMusicMessage.add(0, msg.getData().getString("messageText"));//接受msg传递过来的参数);
                //mMusicTitle.add(0, msg.getData().getString("titleText"));//接受msg传递过来的参数);
               // mMusicPicUrl.add(0, msg.getData().getString("picText"));//接受msg传递过来的参数);
                //RecyclerView列表进行UI数据更新
                HomeRecyclerBean bean = new HomeRecyclerBean( msg.getData().getString("titleText"),msg.getData().getString("picText"),
                        msg.getData().getString("messageText"),msg.getData().getString("amountText"),msg.getData().getString("urlText")
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

    private List<HomeRecyclerBean> list() {
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
            Document doc = Jsoup.connect("http://www.wyyrp.com/").get();
            Elements elements = doc.getElementsByClass("item-list-block-cover");
            Elements elements2 = doc.select("article.item-list-block-content");
            //采集 时间、评论、访问量
            Elements elements3=doc.getElementsByClass("item-list-block-meta");
            for (int i = 0;i < elements2.size();i++)
            {
                //获取音乐图片
                String musicPic = elements.get(i).select("img").attr("src");
                //获取音乐标题
                String musicTitle =elements2.get(i).select("a").attr("title")  ;
                //获取音乐地址
                String musicUrl = elements2.select("a").attr("href") ;
                //获取音乐信息
                String musicMessage =elements2.get(i).getElementsByTag("p").text() ;
                //获取访问量
                String musicLookAmount=elements3.get(i).select("a").get(2).text();
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                //往Bundle中存放数据
                bundle.putString("picText",musicPic);
                bundle.putString("titleText",musicTitle);
                bundle.putString("urlText",musicUrl);
                bundle.putString("messageText",musicMessage);
                bundle.putString("amountText",musicLookAmount);
                msg.setData(bundle);
                hander.sendMessage(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

}}
