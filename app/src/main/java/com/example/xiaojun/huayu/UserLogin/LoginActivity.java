package com.example.xiaojun.huayu.UserLogin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYu.Activity.HomeActivity;
import com.example.xiaojun.huayu.R;


public class LoginActivity extends Activity {
    private static final String USERNAME="username";
    private TextView mNewUserRegist;
    private Button mLoginButton;
    private EditText mUserName;
    private EditText mPassword;
    private String BackUserName;
    private CheckBox autoLogin;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mSharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAutoLogin=mSharedPreferences.getBoolean("auto_login",false);
        if(isAutoLogin){
            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
            startActivity(intent);
        }
        autoLogin=(CheckBox)findViewById(R.id.auto_login);
        BackUserName=getIntent().getStringExtra("username");
        mLoginButton=(Button)findViewById(R.id.login_btn);
        mUserName=(EditText)findViewById(R.id.login_account);
        if(BackUserName!=null) {
            mUserName.setText(BackUserName);
        }
        mPassword=(EditText)findViewById(R.id.login_pwd);

    }
    public void NewUserRegist(View v){
        Intent intent=new Intent(LoginActivity.this,NewUserRegistActivity.class);
        startActivity(intent);
    }
    public void UnableLogin(View v){
        Intent intent=new Intent(LoginActivity.this,UnableLoginActivity.class);
        startActivity(intent);
    }


}
