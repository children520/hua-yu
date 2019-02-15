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
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYu.HomeActivity;
import com.example.xiaojun.huayu.R;
import com.example.xiaojun.huayu.UserLab;


public class LoginActivity extends Activity {
    private static final String USERNAME="username";
    private TextView mNewUserRegist;
    private Button mLoginButton;
    private EditText mUserName;
    private EditText mPassword;
    private UserLab mUserLab;
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
        if(mUserLab==null){
            mUserLab=new UserLab(this);
        }
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                intent.putExtra(USERNAME,"xiaojun");
                startActivity(intent);//此处需要到最后

                String userName=mUserName.getText().toString();
                String passWord=mPassword.getText().toString();
                long flag=mUserLab.queryUser(userName,passWord);

                if(flag==-1){
                    Toast.makeText(LoginActivity.this,"输入的用户名或者密码不正确",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    mEditor=mSharedPreferences.edit();
                    if(autoLogin.isChecked()){
                        mEditor.putBoolean("auto_login",true);
                    }else {
                        mEditor.clear();
                    }
                    mEditor.apply();

                }

            }
        });
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
