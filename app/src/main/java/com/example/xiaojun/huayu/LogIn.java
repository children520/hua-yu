package com.example.xiaojun.huayu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



public class LogIn extends Activity {
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
        setContentView(R.layout.login);
        mSharedPreferences=PreferenceManager.getDefaultSharedPreferences(this);
        boolean isAutoLogin=mSharedPreferences.getBoolean("auto_login",false);
        if(isAutoLogin){
            Intent intent=new Intent(LogIn.this,HomeActivity.class);
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
                Intent intent=new Intent(LogIn.this,HomeActivity.class);
                intent.putExtra(USERNAME,"xiaojun");
                startActivity(intent);//此处需要到最后

                String userName=mUserName.getText().toString();
                String passWord=mPassword.getText().toString();
                long flag=mUserLab.queryUser(userName,passWord);

                if(flag==-1){
                    Toast.makeText(LogIn.this,"输入的用户名或者密码不正确",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LogIn.this,"登录成功",Toast.LENGTH_SHORT).show();
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
        Intent intent=new Intent(LogIn.this,NewUserRegist.class);
        startActivity(intent);
    }
    public void UnableLogin(View v){
        Intent intent=new Intent(LogIn.this,UnableLogin.class);
        startActivity(intent);
    }


}
