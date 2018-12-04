package com.example.xiaojun.huayu.HuaYu;

import java.util.UUID;

public class HuaYuContent {
    private String Title;
    private UUID id;

    public void setUrl(String url) {
        Url = url;
    }

    private String Url;
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }
    private String ImageId;
    private String Content;
    public void setTitle(String title) {
        Title = title;
    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }



    public HuaYuContent(String Title, String ImageId,String Url){
        this.ImageId=ImageId;
        this.Title=Title;
        this.Url=Url;
    }

    public String getUrl() {
        return Url;
    }
}
