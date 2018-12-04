package com.example.xiaojun.huayu;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class NewUserRegist extends AppCompatActivity {
    private EditText mUserName;
    private EditText mTwoPassword;
    private EditText mPassword;
    private Button mRegistButton;
    private SQLiteDatabase mDatabase;
    private UserLab mUserLab;
    private static final String USERNAME="username";
    private static final String ARG_CRIME_ID="crime_id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_regist);
        mUserName=(EditText)findViewById(R.id.regist_username);
        mPassword=(EditText)findViewById(R.id.regist_edit_pwd);
        mTwoPassword=(EditText)findViewById(R.id.regist_edit_two_pwd);
        mRegistButton=(Button)findViewById(R.id.regist_btn);
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
                        Toast.makeText(NewUserRegist.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    }else {
                        User user=new User(userName,password);
                        long flag= mUserLab.addUser(user);
                        if(flag==-1){
                            Toast.makeText(NewUserRegist.this,"注册失败",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NewUserRegist.this,"注册成功",Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(NewUserRegist.this,LogIn.class);
                            intent.putExtra(USERNAME,userName);
                            startActivity(intent);
                        }
                    }
                }




                    }



        });
    }

    @Override
    public void onPause(){
        super.onPause();

    }
    public boolean IsUserNameAndPswValid(){
        if (mUserName==null||mUserName.getText().toString().equals("")){
            Toast.makeText(NewUserRegist.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if((mPassword==null||mPassword.getText().toString().equals(""))){
            Toast.makeText(NewUserRegist.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(mTwoPassword==null||mTwoPassword.getText().toString().equals("")){
            Toast.makeText(NewUserRegist.this,"验证密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
    public  void UnableRegist(View view){
        Intent intent=new Intent(NewUserRegist.this,UnableRegistActivity.class);
        startActivity(intent);
    }


}
