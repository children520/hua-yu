package com.example.xiaojun.huayu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xiaojun.huayu.HuaJi.GoodsRecommendFragment;

public class GoodsRecommendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_recommend);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.goods_recommend_fragment_container);
        if (fragment == null) {
            fragment = new GoodsRecommendFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.goods_recommend_fragment_container, fragment)
                    .commit();
        }
    }
}
