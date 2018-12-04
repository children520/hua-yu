package com.example.xiaojun.huayu;

import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    private TextView mTextView;

   public HomeFragment(){

   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        mTextView = (TextView)view.findViewById(R.id.txt_content);
        return view;
    }
}
