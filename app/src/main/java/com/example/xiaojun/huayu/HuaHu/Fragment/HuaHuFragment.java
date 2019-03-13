package com.example.xiaojun.huayu.HuaHu.Fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaHu.Activity.HuaHuActivity;
import com.example.xiaojun.huayu.HuaYu.Bean.BaiBaoShuContent;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;

import java.util.ArrayList;
import java.util.List;

public class HuaHuFragment  extends Fragment implements View.OnClickListener {
    private List<BaiBaoShuContent> BaiBaoShuContentLists=new ArrayList<>();
    private LinearLayout MyFocusOn,MyDynamic,MyCollection,BrowsingHistory,MyShopping;
    private TextView AboutHuaYu,HelpAndFeedBack,ChangePassword,SwitchAccount,Exit;
    private LinearLayout TopTitleLinearLayout;
    private RelativeLayout HuaHuRelativeLayout;
    private TextView UserNameTextView;
    private TextView ChangePwdTextView;
    private TextView UserPlantCountView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huahu, container, false);
        BindView(view);
        UserNameTextView.setText(Tools.readNickNameStatusSharedPreference(getActivity()));
        return view;
    }
    public void BindView(View view){
        HuaHuRelativeLayout=(RelativeLayout)view.findViewById(R.id.huahu);
        UserPlantCountView=view.findViewById(R.id.huahu_user_plant_count);
        MyFocusOn=(LinearLayout)view.findViewById(R.id.my_focus_on);
        MyDynamic=(LinearLayout)view.findViewById(R.id.my_dynamic);
        BrowsingHistory=(LinearLayout)view.findViewById(R.id.browsing_history);
        MyShopping=(LinearLayout)view.findViewById(R.id.my_shopping);
        UserNameTextView=(TextView)view.findViewById(R.id.huahu_user_name);
        ChangePwdTextView=(TextView)view.findViewById(R.id.change_password);
        ChangePwdTextView.setOnClickListener(this);
        MyFocusOn.setOnClickListener(this);
        MyDynamic.setOnClickListener(this);
        BrowsingHistory.setOnClickListener(this);
        MyShopping.setOnClickListener(this);
    }
    public void hideAllFragment(FragmentTransaction transaction){


    }
    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.my_focus_on:
                Intent intent=new Intent(getActivity(), HuaHuActivity.class);
                intent.putExtra("code",1);
                startActivity(intent);
                break;
            case R.id.change_password:
                Intent changePwdIntent=new Intent(getActivity(),HuaHuActivity.class);
                changePwdIntent.putExtra("code",2);
                startActivity(changePwdIntent);
                break;
        }
    }
}
