package com.example.xiaojun.huayu.HuaYu;


import java.util.UUID;

import cn.bmob.v3.BmobObject;

public class WebInformation extends BmobObject {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
