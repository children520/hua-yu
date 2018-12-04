package com.example.xiaojun.huayu.HuaHu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiaojun.huayu.R;

import java.util.ArrayList;
import java.util.List;

public class MyFocusOnFragment extends Fragment {
    private List<MyFocusOnContent> MyFocusContentLists=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myfocuson, container, false);
        initMyFocusOnContent();
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.myfocuson_recycler_content);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        MyFocusOnContentAdapter adapter=new MyFocusOnContentAdapter(MyFocusContentLists);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void initMyFocusOnContent() {
        MyFocusOnContent one =new MyFocusOnContent(R.mipmap.apple,"username",false);
        MyFocusContentLists.add(one);
        }

}
