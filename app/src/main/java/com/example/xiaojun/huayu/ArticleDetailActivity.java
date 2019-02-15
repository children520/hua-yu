package com.example.xiaojun.huayu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.xiaojun.huayu.HuaYu.Fragment.ArticleDetailFragment;

public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.article_detail_fragment_container);
        if(fragment==null){
            fragment=new ArticleDetailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.article_detail_fragment_container,fragment)
                    .commit();
        }
    }

}
