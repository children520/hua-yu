package com.example.xiaojun.huayu.HuaYu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.xiaojun.huayu.HuaYu.Adapter.HuaYuContentAdapter;
import com.example.xiaojun.huayu.HuaYu.Bean.HuaYuContent;
import com.example.xiaojun.huayu.HuaYu.Fragment.HuaYuFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HuaYuContentLab {
    private static List<HuaYuContent> mHuaYuContentList;
    private static HuaYuContentLab sHuaYuContentLab;
    private Context mContext;
    private List<String> ImageSrcList;
    private List<String> ImageUrlList;
    private List<String> TitleList;
    private List<String> urlList=new ArrayList<>();

    private HuaYuContentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    public static HuaYuContentLab get(Context context){
        if(sHuaYuContentLab ==null){
            sHuaYuContentLab =new HuaYuContentLab(context);
        }
        return sHuaYuContentLab;
    }



    private HuaYuContentLab(Context context){

        /*
        for(int i=0;i<urlList.size();i++){
            HuaYuContent huaYuContent=new HuaYuContent(TitleList.get(i),ImageSrcList.get(i),urlList.get(i));
            mHuaYuContentList.add(huaYuContent);
        }

        for(int i=0;i<5;i++){
            HuaYuContent huaYuContent=new HuaYuContent("郁金香",R.mipmap.yujinxiang,"雍容华贵","https://mp.weixin.qq.com/s/-DKC3mZ86Yhjqiq3CHnq-w");
            mHuaYuContentList.add(huaYuContent);
        }
        */
    }

    public List<HuaYuContent> getHuaYuContentList(){
        return mHuaYuContentList;
    }


    public void addPlant(HuaYuContent huaYuContent){
        mHuaYuContentList.add(huaYuContent);

    }





    private void showResponse(final String response) {

    }

}
