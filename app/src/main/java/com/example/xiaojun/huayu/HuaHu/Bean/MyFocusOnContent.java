package com.example.xiaojun.huayu.HuaHu.Bean;

public class MyFocusOnContent {
    private int imageId;
    private String userName;
    private boolean isFocusOn;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isFocusOn() {
        return isFocusOn;
    }

    public void setFocusOn(boolean focusOn) {
        isFocusOn = focusOn;
    }
    public MyFocusOnContent(int imageId,String userName,boolean isFocusOn){
        this.imageId=imageId;
        this.userName=userName;
        this.isFocusOn=isFocusOn;
    }
}
