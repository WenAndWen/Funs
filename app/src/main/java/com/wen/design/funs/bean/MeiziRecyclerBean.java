package com.wen.design.funs.bean;

/**
 * Created by wen on 2018/2/8.
 */

public class MeiziRecyclerBean {
    /**
     * Created by hawvu on 2018/2/6.
     */
    private String image;
    //音乐标题，音乐图片，音乐信息，访问量，音乐地址
    public MeiziRecyclerBean(String image){
        this.image= image;
    }

    public String getImage() {
        return image;
    }
}

