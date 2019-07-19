package com.example.xiaojun.huayu.UserLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYu.Activity.MainActivity;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;
import com.example.xiaojun.huayu.UserLogin.Activity.UnableRegistActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


public class NewUserRegistActivity extends AppCompatActivity {
    @BindView(R.id.edt_phone)
    EditText mEdtPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_info)
    TextView mTvInfo;
    private static boolean IsRegist=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_regist);
        ButterKnife.bind(this);
        Bmob.initialize(this, "c16bef06a867de181070610c9681e9c0");
        if(Tools.readIsReigistStatusSharedPreference(this)){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
    /*
    短信发送
     */
    @OnClick({R.id.btn_send, R.id.btn_signup_or_login})
    public void onViewClicked(final View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                String phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "HuaYu", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.requestSMSCode(phone, "HuaYu", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "发送验证码成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "发送验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }
            case R.id.btn_signup_or_login: {
                String phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = mEdtCode.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Intent intent=new Intent(NewUserRegistActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            IsRegist=true;
                            Tools.writeIsRegistToSharedPreference(IsRegist,view.getContext());
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "注册失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            }

            default:
                break;
        }
    }
    @Override
    public void onPause(){
        super.onPause();

    }

    public  void UnableRegist(View view){
        Intent intent=new Intent(NewUserRegistActivity.this, UnableRegistActivity.class);
        startActivity(intent);
    }





}
