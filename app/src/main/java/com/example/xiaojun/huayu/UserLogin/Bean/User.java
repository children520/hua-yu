package com.example.xiaojun.huayu.UserLogin.Bean;

import cn.bmob.v3.BmobUser;

/**
 *
 *
 *
 */
public class User extends BmobUser {
    /**
     * TODO 昵称
     */
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
}
