package com.example.xiaojun.huayu.UserLogin;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYu.Activity.HomeActivity;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;

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
    @BindView(R.id.edt_nickname)
    EditText mEdtNickName;
    private EditText mUserName;
    private EditText mTwoPassword;
    private EditText mPassword;
    private Button mRegistButton;
    private SQLiteDatabase mDatabase;

    private boolean IsRegist=false;
    private static final String USERNAME="username";
    private static final String ARG_CRIME_ID="crime_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_regist);
        ButterKnife.bind(this);
        Bmob.initialize(this, "c16bef06a867de181070610c9681e9c0");
        if(Tools.readIsReigistStatusSharedPreference(this)){
            startActivity(new Intent(this,HomeActivity.class));
        }
        /*
        if(mUserLab==null){
             mUserLab=new UserLab(this);
        }

        mRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsUserNameAndPswValid()){
                    String userName=mUserName.getText().toString().trim();
                    String password=mPassword.getText().toString().trim();
                    String twopassword=mTwoPassword.getText().toString().trim();
                    if(password.equals(twopassword)==false){
                        Toast.makeText(NewUserRegistActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    }else {
                        User user=new User(userName,password);
                        long flag= mUserLab.addUser(user);
                        if(flag==-1){
                            Toast.makeText(NewUserRegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NewUserRegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(NewUserRegistActivity.this,LoginActivity.class);
                            intent.putExtra(USERNAME,userName);
                            startActivity(intent);
                        }
                    }
                }




                    }



        });
        */
    }


    @OnClick({R.id.btn_send, R.id.btn_signup_or_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send: {
                String phone = mEdtPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "HuaYu", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板。
                 */
                BmobSMS.requestSMSCode(phone, "HuaYu", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                        } else {
                            mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
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
                final String nickname = mEdtNickName.getText().toString().trim();
                if (TextUtils.isEmpty(nickname)) {
                    Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
                    return;
                }
                BmobSMS.verifySmsCode(phone, code, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            IsRegist=true;
                            Tools.writeIsRegistToSharedPreference(IsRegist,nickname,getApplicationContext());
                            Intent intent=new Intent(NewUserRegistActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                        } else {
                            mTvInfo.append("验证码验证失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
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
    public boolean IsUserNameAndPswValid(){
        if (mUserName==null||mUserName.getText().toString().equals("")){
            Toast.makeText(NewUserRegistActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if((mPassword==null||mPassword.getText().toString().equals(""))){
            Toast.makeText(NewUserRegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mTwoPassword==null||mTwoPassword.getText().toString().equals("")){
            Toast.makeText(NewUserRegistActivity.this,"验证密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
    public  void UnableRegist(View view){
        Intent intent=new Intent(NewUserRegistActivity.this,UnableRegistActivity.class);
        startActivity(intent);
    }





}
