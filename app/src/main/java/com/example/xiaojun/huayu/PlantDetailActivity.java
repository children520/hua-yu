package com.example.xiaojun.huayu;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xiaojun.huayu.HuaHu.MyFocusOnFragment;
import com.example.xiaojun.huayu.HuaYuan.PlantDetailFragment;
import com.example.xiaojun.huayu.HuaYuan.PlantDetailService;
import com.example.xiaojun.huayu.HuaYuan.RemindSettingFragment;

public class PlantDetailActivity extends AppCompatActivity {
    private LinearLayout mLinearLayout;
    private RemindSettingFragment mRemindSettingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =new Intent(this,PlantDetailService.class);
        startService(intent);
        setContentView(R.layout.activity_plant_detail);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.plant_detail_fragment_container);
        if(fragment==null){
            fragment=new PlantDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.plant_detail_fragment_container,fragment)
                    .commit();
        }

    }
}
