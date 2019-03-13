package com.example.xiaojun.huayu.HuaYu.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYu.Adapter.HuaYouHuiContentAdapter;
import com.example.xiaojun.huayu.HuaYu.Bean.HuaYouHuiContent;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class HuaYouHuiFragment extends Fragment {
    private static final String USERNAME="username";
    private List<HuaYouHuiContent> mHuaYouHuiContentList =new ArrayList<>();
    private ImageView PublicDynamicImageView;
    private AlertDialog PublicDynamicDialog;
    private View PublicDynamicView;
    private AlertDialog.Builder PublicDynamicBuilder;
    private EditText TitleEditText;
    private EditText ContentEditText;
    private TextView TimeTextView;
    private static final int UPDATE_VIEW=2;
    private RecyclerView recyclerView;
    private HuaYouHuiContentAdapter Adapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss //获取当前时间
    Date date = new Date(System.currentTimeMillis());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_huayouhui,container,false);
        mSwipeRefreshLayout=view.findViewById(R.id.huayouhui_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AcquireDynamic();
            }
        });
        recyclerView=(RecyclerView)view.findViewById(R.id.huayouhu_recycler_content);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        AcquireDynamic();

        PublicDynamicBuilder=new AlertDialog.Builder(getActivity());
        final LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        PublicDynamicView=layoutInflater.inflate(R.layout.public_dynamic_dialog,null,false);
        TitleEditText=(EditText)PublicDynamicView.findViewById(R.id.public_dynamic_input_title);
        ContentEditText=(EditText)PublicDynamicView.findViewById(R.id.public_dynamic_input_content);
        TimeTextView=(TextView)PublicDynamicView.findViewById(R.id.public_dynamic_time);
        PublicDynamicBuilder.setView(PublicDynamicView);
        PublicDynamicBuilder.setCancelable(false);
        PublicDynamicDialog=PublicDynamicBuilder.create();
        TimeTextView.setText(simpleDateFormat.format(date));
        PublicDynamicView.findViewById(R.id.public_dynamic_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicDynamicDialog.dismiss();
            }
        });
        PublicDynamicView.findViewById(R.id.public_dynamic_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicDynamicDialog.dismiss();
            }
        });
        PublicDynamicView.findViewById(R.id.public_dynamic_ensure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(TitleEditText.getText())&&TextUtils.isEmpty(ContentEditText.getText())){
                    Toast.makeText(getActivity(),"你未填写信息哦",Toast.LENGTH_SHORT).show();
                }else{
                    PublicDynamic(Tools.readNickNameStatusSharedPreference(getActivity()),TitleEditText.getText().toString(),ContentEditText.getText().toString());
                }
            }
        });
        PublicDynamicImageView=(ImageView)view.findViewById(R.id.public_dynamic);
        PublicDynamicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicDynamicDialog.show();
            }
        });

        return view;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VIEW:
                    updateUI();
                    if(mSwipeRefreshLayout.isRefreshing()){
                        mSwipeRefreshLayout.setRefreshing(false);
                        Adapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    };
    private void updateUI(){
        if(Adapter==null) {
            Adapter = new HuaYouHuiContentAdapter(mHuaYouHuiContentList);
            Log.d("adapter",Adapter.toString());
            recyclerView.setAdapter(Adapter);
        }else {
            Adapter.notifyDataSetChanged();
        }



    }
    private void AcquireDynamic(){
        BmobQuery<HuaYouHuiContent> query=new BmobQuery<>();
        query.setLimit(50);
        query.findObjects(new FindListener<HuaYouHuiContent>() {
            @Override
            public void done(List<HuaYouHuiContent> list, BmobException e) {
                if (e == null) {
                    Set<HuaYouHuiContent> set=new HashSet<>();
                    for (HuaYouHuiContent huaYouHuiContent :list) {
                        set.add(huaYouHuiContent);
                        }
                        Iterator<HuaYouHuiContent> it=set.iterator();
                        mHuaYouHuiContentList.clear();
                        while(it.hasNext()){
                            mHuaYouHuiContentList.add(it.next());
                        }
                        /*
                        String userName= huaYouHuiContent.getUserName();
                        String title= huaYouHuiContent.getDynamicTitle();
                        String content= huaYouHuiContent.getDynamicContent();

                        if(!TitleList.contains(title)&&!UserNameList.contains(userName)&&!ContentList.contains(content)){
                            Log.d("TitleList",TitleList.toString());
                            Log.d("UserNameList",UserNameList.toString());
                            Log.d("ContentList",ContentList.toString());
                            HuaYouHuiContent information=new HuaYouHuiContent(userName,title,content);
                            TitleList.add(title);
                            ContentList.add(content);
                            UserNameList.add(userName);
                            */








                    Message message=new Message();
                    message.what=UPDATE_VIEW;
                    handler.sendMessage(message);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
    private void PublicDynamic(String UserName,String Title,String Content){
        HuaYouHuiContent huaYouHuiContent =new HuaYouHuiContent(UserName,Title,Content);
        huaYouHuiContent.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(getActivity(),"发布成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(),"发布失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
