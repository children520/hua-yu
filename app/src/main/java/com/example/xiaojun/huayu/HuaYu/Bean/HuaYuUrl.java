package com.example.xiaojun.huayu.HuaYu.Bean;


import java.util.UUID;

import cn.bmob.v3.BmobObject;

public class HuaYuUrl extends BmobObject {
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

}
