package com.wen.design.funs.bean;

/**
 * Created by wen on 2018/2/8.
 */

public class HomeRecyclerBean {
    /**
     * Created by hawvu on 2018/2/6.
     */
        private String title,image,message,amount,url;
        //音乐标题，音乐图片，音乐信息，访问量，音乐地址
        public HomeRecyclerBean(String title, String image, String message, String amount, String url){

            this.title = title;
            this.image= image;
            this.message=message;
            this.amount=amount;
            this.url=url;
        }

        public String getTitle() {
            return title;
        }
        public String getImage() {
            return image;
        }
        public String getMessage() {
            return message;
        }
        public String getAmount() {
            return amount;
        }
        public String getUrl() {
            return url;
        }
}

