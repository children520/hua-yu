package com.example.xiaojun.huayu;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xiaojun.huayu.HuaYu.BaiBaoShuContent;
import com.example.xiaojun.huayu.HuaYu.BaiBaoShuContentAdapter;

import java.util.ArrayList;
import java.util.List;

public class HuaJiFragment extends Fragment {
    private List<BaiBaoShuContent> BaiBaoShuContentLists=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initBaiBaoShuContent();
        View view = inflater.inflate(R.layout.huaji_fragment, container, false);
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.huaji_recycler_content);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        BaiBaoShuContentAdapter adapter=new BaiBaoShuContentAdapter(BaiBaoShuContentLists);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void initBaiBaoShuContent() {

    }
}
