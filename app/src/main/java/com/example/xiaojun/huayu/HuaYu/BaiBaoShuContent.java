package com.example.xiaojun.huayu.HuaYu;

public class BaiBaoShuContent {
    private String imageUrl;
    private String title;




    private String content;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

    public BaiBaoShuContent(String imageUrl,String title,String url){
        this.imageUrl=imageUrl;
        this.title=title;

        this.url=url;
    }

}
