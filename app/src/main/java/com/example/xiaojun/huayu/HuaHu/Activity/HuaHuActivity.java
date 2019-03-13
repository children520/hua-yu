package com.example.xiaojun.huayu.HuaHu.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.xiaojun.huayu.HuaHu.Fragment.ChangePassordFragment;
import com.example.xiaojun.huayu.R;

public class HuaHuActivity extends AppCompatActivity {

    private ChangePassordFragment mChangePassordFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huahu);
        int id = getIntent().getIntExtra("code", 0);
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (id == 1) {


        }else if(id==2){
            if ( mChangePassordFragment == null) {
                mChangePassordFragment = new  ChangePassordFragment();
                transaction.add(R.id.huahu_fragment_container, mChangePassordFragment);
            } else {
                transaction.show(mChangePassordFragment);
            }
            transaction.commit();
        }
    }
}
