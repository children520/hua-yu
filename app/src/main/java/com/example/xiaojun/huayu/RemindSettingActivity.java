package com.example.xiaojun.huayu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xiaojun.huayu.HuaYuan.Fragment.RemindSettingFragment;

public class RemindSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_setting);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.remind_setting_fragment_container);
        if(fragment==null){
            fragment=new RemindSettingFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.remind_setting_fragment_container,fragment)
                    .commit();
        }
    }
}
