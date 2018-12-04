package com.example.xiaojun.huayu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.xiaojun.huayu.HuaHu.MyFocusOnFragment;

public class HuaHuActivity extends AppCompatActivity {
    private MyFocusOnFragment mMyFocusOnFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huahu);
        int id = getIntent().getIntExtra("MyFocusOn", 0);
        if (id == 1) {
            android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
            if (mMyFocusOnFragment == null) {
                mMyFocusOnFragment = new MyFocusOnFragment();
                transaction.add(R.id.huahu_fragment_container, mMyFocusOnFragment);
            } else {
                transaction.show(mMyFocusOnFragment);
            }
            transaction.commit();

        }
    }
}
