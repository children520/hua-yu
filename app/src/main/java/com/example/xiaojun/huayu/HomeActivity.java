package com.example.xiaojun.huayu;

import android.app.FragmentTransaction;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.Bmob;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView HuaYuImageView;
    private ImageView HuaYuanImageView;
    private ImageView HuaJiImageView;
    private ImageView HuaHuImageView;
    private TextView  HuaYuTextView;
    private TextView HuaYuanTextView;
    private TextView HuaHuTextView;
    private TextView HuaJiTextView;
    private TextView HomeTextView;
    private TextView HuaYouHuTextView;
    private TextView BaiBaoShuTextView;
    private FrameLayout FLcontent,FLWcontent;
    private LinearLayout mLinearLayout;

    private HuaYuFragment mHuaYuFragment;
    private HuaYouHuiFragment mHuaYouHuFragment;
    private BaiBaoShuFragment baibaoshuFragment;
    private HuaYuanFragment huayuanFragment;
    private HuaJiFragment huajiFragment;

    private HuaHuFragment huahuFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Bmob.initialize(this, "c16bef06a867de181070610c9681e9c0");
        bindView();
        TopTitleBindView();



    }
    private void TopTitleBindView(){
        HomeTextView=(TextView)findViewById(R.id.top_title_home);
        HuaYouHuTextView=(TextView)findViewById(R.id.top_title_huayouhu);
        BaiBaoShuTextView=(TextView)findViewById(R.id.top_title_baibaoshu);
        FLWcontent=(FrameLayout)findViewById(R.id.activity_home_container);
        HomeTextView.setOnClickListener(this);
        HuaYouHuTextView.setOnClickListener(this);
        BaiBaoShuTextView.setOnClickListener(this);
    }
    private void TopTitleSelected(){
        HomeTextView.setSelected(false);
        HuaYouHuTextView.setSelected(false);
        BaiBaoShuTextView.setSelected(false);
    }
    private void hideTopFragment(FragmentTransaction transaction){
        if(mHuaYuFragment!=null){
            transaction.hide(mHuaYuFragment);
        }
        if(mHuaYouHuFragment!=null){
            transaction.hide(mHuaYouHuFragment);
        }
        if(baibaoshuFragment!=null){
            transaction.hide(baibaoshuFragment);
        }
    }

    private void bindView(){
        HuaYuImageView=(ImageView)findViewById(R.id.huayu_image);
        HuaYuanImageView=(ImageView)findViewById(R.id.huayuan_image);
        HuaJiImageView=(ImageView)findViewById(R.id.huaji_image);
        HuaHuImageView=(ImageView)findViewById(R.id.huahu_image);
        HuaYuTextView=(TextView)findViewById(R.id.huayu_text);
        HuaYuanTextView=(TextView)findViewById(R.id.huayuan_text);
        HuaJiTextView=(TextView)findViewById(R.id.huaji_text);
        HuaHuTextView=(TextView)findViewById(R.id.huahu_text);
        FLcontent=(FrameLayout)findViewById(R.id.activity_home_container);
        mLinearLayout=(LinearLayout)findViewById(R.id.top_title);
        HuaHuImageView.setOnClickListener(this);
        HuaHuTextView.setOnClickListener(this);
        HuaYuanTextView.setOnClickListener(this);
        HuaYuanImageView.setOnClickListener(this);
        HuaJiTextView.setOnClickListener(this);
        HuaJiImageView.setOnClickListener(this);
        HuaYuImageView.setOnClickListener(this);
        HuaYuTextView.setOnClickListener(this);


    }
    public void selected(){
        HuaYuanImageView.setSelected(false);
        HuaYuanTextView.setSelected(false);
        HuaYuImageView.setSelected(false);
        HuaYuTextView.setSelected(false);
        HuaJiTextView.setSelected(false);
        HuaJiImageView.setSelected(false);
        HuaHuTextView.setSelected(false);
        HuaHuImageView.setSelected(false);
    }
    public void hideAllFragment(FragmentTransaction transaction){
        if(mHuaYuFragment!=null){
            transaction.hide(mHuaYuFragment);
        }
        if(huayuanFragment!=null){
            transaction.hide(huayuanFragment);
        }
        if(huajiFragment!=null){
            transaction.hide(huajiFragment);
        }
        if(huahuFragment!=null){
            transaction.hide(huahuFragment);
        }

    }
    @Override
    public void onClick(View v) {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        hideTopFragment(transaction);
        switch(v.getId()) {
            case (R.id.huayu_image):
                selected();
                HuaYuImageView.setSelected(true);
                HuaYuTextView.setSelected(true);
                mLinearLayout.setVisibility(View.VISIBLE);
                Toast.makeText(this, "huayu", Toast.LENGTH_SHORT).show();
                if (mHuaYuFragment == null) {
                    mHuaYuFragment = new HuaYuFragment();
                    transaction.add(R.id.activity_home_container, mHuaYuFragment);
                } else {
                    transaction.show(mHuaYuFragment);
                }
                break;

            case R.id.huayuan_image:
                selected();
                TopTitleSelected();
                HuaYuanImageView.setSelected(true);
                HuaYuanTextView.setSelected(true);
                mLinearLayout.setVisibility(View.GONE);
                Toast.makeText(this, "huayuan", Toast.LENGTH_SHORT).show();
                if (huayuanFragment == null) {
                    huayuanFragment = new HuaYuanFragment();
                    transaction.add(R.id.activity_home_container, huayuanFragment);
                } else {
                    transaction.show(huayuanFragment);
                }
                break;

            case R.id.huaji_image:
                selected();
                TopTitleSelected();
                HuaJiImageView.setSelected(true);
                HuaJiTextView.setSelected(true);
                mLinearLayout.setVisibility(View.GONE);
                Toast.makeText(this, "huaji", Toast.LENGTH_SHORT).show();
                if (huajiFragment == null) {
                    huajiFragment = new HuaJiFragment();
                    transaction.add(R.id.activity_home_container, huajiFragment);
                } else {
                    transaction.show(huajiFragment);
                }
                break;

            case R.id.huahu_image:
                selected();
                TopTitleSelected();
                HuaHuTextView.setSelected(true);
                HuaHuImageView.setSelected(true);
                mLinearLayout.setVisibility(View.GONE);
                Toast.makeText(this, "huahu", Toast.LENGTH_SHORT).show();
                if (huahuFragment == null) {
                    huahuFragment = new HuaHuFragment();
                    transaction.add(R.id.activity_home_container, huahuFragment);
                } else {
                    transaction.show(huahuFragment);
                }
                break;
            case R.id.top_title_huayouhu:
                TopTitleSelected();
                HuaYouHuTextView.setSelected(true);

                if(mHuaYouHuFragment==null){
                    mHuaYouHuFragment=new HuaYouHuiFragment();
                    transaction.add(R.id.activity_home_container,mHuaYouHuFragment);
                }else{
                    transaction.show(mHuaYouHuFragment);
                }
                break;
            case R.id.top_title_home:
                TopTitleSelected();
                HomeTextView.setSelected(true);
                if(mHuaYuFragment==null){
                    mHuaYuFragment=new HuaYuFragment();
                    transaction.add(R.id.activity_home_container,mHuaYuFragment);
                }else{
                    transaction.show(mHuaYuFragment);
                }
                break;
            case R.id.top_title_baibaoshu:
                TopTitleSelected();
                BaiBaoShuTextView.setSelected(true);
                if(baibaoshuFragment==null){
                    baibaoshuFragment =new BaiBaoShuFragment();
                    transaction.add(R.id.activity_home_container,baibaoshuFragment);
                }else{
                    transaction.show(baibaoshuFragment);
                }
                break;
        }
        transaction.commit();
    }
}
