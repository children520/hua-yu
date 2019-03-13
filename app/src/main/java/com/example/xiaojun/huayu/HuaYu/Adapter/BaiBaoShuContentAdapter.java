package com.example.xiaojun.huayu.HuaYu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYu.Activity.ArticleDetailActivity;
import com.example.xiaojun.huayu.HuaYu.Bean.BaiBaoShuContent;
import com.example.xiaojun.huayu.HuaYu.Fragment.BaiBaoShuFragment;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;

import java.util.List;


public class BaiBaoShuContentAdapter  extends RecyclerView.Adapter<BaiBaoShuContentAdapter.ViewHolder> {
    private List<BaiBaoShuContent> mBaiBaoShuContentsList;
    private static final String URL="url";
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTitleView;
        private Context mContext;
        private BaiBaoShuContent mBaiBaoShuContent;
        BaiBaoShuFragment baiBaoShuFragment=new BaiBaoShuFragment();
        public ViewHolder(View view) {
            super(view);
            mContext=view.getContext();
            view.setOnClickListener(this);
            mImageView=(ImageView)view.findViewById(R.id.baibaoshu_content_image);
            mTitleView=(TextView)view.findViewById(R.id.baibaoshu_content_title);
        }
        public void BindBaiBaoShuContent(BaiBaoShuContent baiBaoShuContent){
            mBaiBaoShuContent=baiBaoShuContent;
            try{
                Tools.LoadImage(mImageView,baiBaoShuContent.getImageUrl());
                mTitleView.setText(baiBaoShuContent.getTitle().split("\\|\\|")[0]);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(),ArticleDetailActivity.class);
            intent.putExtra(URL,mBaiBaoShuContent.getUrl());
            v.getContext().startActivity(intent);
        }
    }
    public BaiBaoShuContentAdapter(List<BaiBaoShuContent> BaiBaoShuContentsList){
        mBaiBaoShuContentsList=BaiBaoShuContentsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.baibaoshu_content,parent,false);
        BaiBaoShuContentAdapter.ViewHolder holder=new BaiBaoShuContentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaiBaoShuContent baiBaoShuContent=mBaiBaoShuContentsList.get(position);
        holder.BindBaiBaoShuContent(baiBaoShuContent);



    }



    @Override
    public int getItemCount() {
        return mBaiBaoShuContentsList.size();
    }

}
