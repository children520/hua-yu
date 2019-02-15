package com.example.xiaojun.huayu.HuaJi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaojun.huayu.GoodsRecommendActivity;
import com.example.xiaojun.huayu.HuaJiFragment;
import com.example.xiaojun.huayu.R;

import java.util.List;

public class HuaJiContentAdapter  extends RecyclerView.Adapter<com.example.xiaojun.huayu.HuaJi.HuaJiContentAdapter.ViewHolder> {
    private List<HuaJiContent> mHuaJiContentsList;
    private static final String URL="url";
    private static final String TITLE="title";
    private static final String PRICE="price";
    private static final String SELLER="seller";
    private static final String IMAGEURL="imageurl";
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTitleView;
        private TextView mPriceView;
        private TextView mSellerView;
        private Context mContext;
        private HuaJiContent mHuaJiContent;
        HuaJiFragment huaJiFragment=new HuaJiFragment();
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView=view.findViewById(R.id.huaji_image);
            mTitleView=view.findViewById(R.id.huaji_title);
            mPriceView=view.findViewById(R.id.huaji_price);
            mSellerView=view.findViewById(R.id.huaji_seller);
        }
        public void BindHuaJiContent(HuaJiContent huajiContent){
            mHuaJiContent=huajiContent;
            try{

                mImageView.setImageResource(R.mipmap.apple);
                mTitleView.setText(mHuaJiContent.getTitle());
                mPriceView.setText(mHuaJiContent.getPrice()+"");
                mSellerView.setText(mHuaJiContent.getSeller());

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(v.getContext(),GoodsRecommendActivity.class);
            intent.putExtra(TITLE,mHuaJiContent.getTitle());
            intent.putExtra(PRICE,mHuaJiContent.getPrice());
            intent.putExtra(IMAGEURL,mHuaJiContent.getImageUrl());
            intent.putExtra(SELLER,mHuaJiContent.getSeller());
            v.getContext().startActivity(intent);
        }
    }
    public HuaJiContentAdapter(List<HuaJiContent> HuaJiContentsList){
        mHuaJiContentsList=HuaJiContentsList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.huaji_content,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HuaJiContent huaJiContent=mHuaJiContentsList.get(position);
        holder.BindHuaJiContent(huaJiContent);
    }




    @Override
    public int getItemCount() {
        return mHuaJiContentsList.size();
    }

}
