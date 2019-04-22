package com.example.xiaojun.huayu.HuaYu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYu.Bean.HuaYuContent;
import com.example.xiaojun.huayu.HuaYu.Fragment.HuaYuFragment;
;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;
import com.example.xiaojun.huayu.UserLogin.LoginActivity;

import java.util.List;

public class HuaYuContentAdapter extends RecyclerView.Adapter<HuaYuContentAdapter.ViewHolder> {
    private List<HuaYuContent> mHuaYuContentsList;
    private List<String> mImageUrlList;
    private List<String> mTitleList;
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTitleView;
        private Context mContext;
        private TextView mContentView;
        private HuaYuContent mHuaYuContent;

        public ViewHolder(View view) {
            super(view);
            mContext=view.getContext();
            view.setOnClickListener(this);
            mImageView = (ImageView) view.findViewById(R.id.huayu_content_image);
            mTitleView = (TextView) view.findViewById(R.id.huayu_content_title);
            mContentView=(TextView)view.findViewById(R.id.huayu_content_content);
        }
        public void bindHuaYuContent(HuaYuContent huaYuContent){
            mHuaYuContent=huaYuContent;
            try{
                mTitleView.setText(mHuaYuContent.getTitle().split("\\|\\|")[0]);
                mContentView.setText(mHuaYuContent.getTitle().split("\\|\\|")[1]);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        public void bindDrawable(Drawable drawable){
            mImageView.setImageDrawable(drawable);
        }

        @Override
        public void onClick(View v) {
            Intent intent=HuaYuFragment.newIntent(v.getContext(),mHuaYuContent.getUrl());
            v.getContext().startActivity(intent);
        }

    }
    public HuaYuContentAdapter(List<HuaYuContent> HuaYuContentsList,List<String> ImageUrlList,List<String> TitleList) {
        mHuaYuContentsList = HuaYuContentsList;
        mImageUrlList=ImageUrlList;
        mTitleList=TitleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.huayu_content, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HuaYuContent HuaYuContent = mHuaYuContentsList.get(position);
        holder.bindHuaYuContent(HuaYuContent);
        HuaYuFragment.getThumbnailDownloaderInstance().queneThumbnail(holder,HuaYuContent.getImageUrl());



    }


    @Override
    public int getItemCount() {
        return mHuaYuContentsList.size();
    }

}