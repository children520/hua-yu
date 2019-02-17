package com.example.xiaojun.huayu.UserLogin;


import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import com.example.xiaojun.huayu.R;
public class UserMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_reset_password, R.id.btn_reset_sms, R.id.btn_bind, R.id.btn_unbind, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset_password:

                break;
            case R.id.btn_reset_sms:

                break;
            case R.id.btn_bind:

                break;
            case R.id.btn_unbind:

                break;
            case R.id.btn_exit:
                BmobUser.logOut();
                finish();
                break;
            default:
                break;
        }
    }
}
