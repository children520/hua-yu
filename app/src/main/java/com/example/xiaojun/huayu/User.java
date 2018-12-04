package com.example.xiaojun.huayu;

import java.util.UUID;

public class User {
    private UUID mId;
    private String mUserName;
    private String mPassword;

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public User(String mUserName,String mPassword){
        super();
        this.mUserName=mUserName;
        this.mPassword=mPassword;
    }

}
