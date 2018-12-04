package com.example.xiaojun.huayu.HuaYu;

import cn.bmob.v3.BmobObject;

public class BaiBaoShuInformation extends BmobObject {
    public String getBaiBaoShuUrl() {
        return BaiBaoShuUrl;
    }

    public void setBaiBaoShuUrl(String baiBaoShuUrl) {
        BaiBaoShuUrl = baiBaoShuUrl;
    }

    private String BaiBaoShuUrl;
}
