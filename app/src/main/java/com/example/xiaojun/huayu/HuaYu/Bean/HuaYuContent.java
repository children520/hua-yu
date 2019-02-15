package com.example.xiaojun.huayu.HuaYu.Bean;

import java.util.UUID;

public class HuaYuContent {
    private String Title;


    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    private String ImageUrl;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    private String Url;

    public HuaYuContent(String Title,String ImageUrl,String Url){
        this.Url=Url;
        this.Title=Title;
        this.ImageUrl=ImageUrl;
    }


}
