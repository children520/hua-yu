package com.example.xiaojun.huayu.HuaYu;

import cn.bmob.v3.BmobObject;

public class HuaYouHuiContent extends BmobObject {
    private String UserName;
    private String DynamicTitle;
    private String DynamicContent;
    public HuaYouHuiContent(String UserName, String DynamicTitle, String DynamicContent){
        this.UserName=UserName;
        this.DynamicTitle=DynamicTitle;
        this.DynamicContent=DynamicContent;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getDynamicTitle() {
        return DynamicTitle;
    }

    public void setDynamicTitle(String dynamicTitle) {
        DynamicTitle = dynamicTitle;
    }

    public String getDynamicContent() {
        return DynamicContent;
    }

    public void setDynamicContent(String dynamicContent) {
        DynamicContent = dynamicContent;
    }


}
