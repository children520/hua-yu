package com.example.xiaojun.huayu.HuaYu.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYu.Bean.HuaYouHuiContent;
import com.example.xiaojun.huayu.R;

import java.util.List;

public class HuaYouHuiContentAdapter extends RecyclerView.Adapter<HuaYouHuiContentAdapter.ViewHolder> {
private List<HuaYouHuiContent> mHuaYouHuiContentList;
private static final String URL="url";
static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mContentView;
    private TextView mUserNameView;
    private HuaYouHuiContent mHuaYouHuiContent;

    public ViewHolder(View view) {
        super(view);
        view.setOnClickListener(this);
        mImageView=(ImageView)view.findViewById(R.id.huayouhui_image);
        mUserNameView=(TextView)view.findViewById(R.id.huayouhui_username);
        mTitleView=(TextView)view.findViewById(R.id.huayouhui_title);
        mContentView=(TextView)view.findViewById(R.id.huayouhui_content);
    }
    public void BindHuaYouHuiContent(HuaYouHuiContent huaYouHuiContent){
        mHuaYouHuiContent=huaYouHuiContent;
        try{
            mTitleView.setText(mHuaYouHuiContent.getDynamicTitle());
            mUserNameView.setText(mHuaYouHuiContent.getUserName());
            mContentView.setText(mHuaYouHuiContent.getDynamicContent());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {

    }
}
    public HuaYouHuiContentAdapter(List<HuaYouHuiContent> HuaYouHuiContentsList){
        mHuaYouHuiContentList=HuaYouHuiContentsList;
    }






    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.huayouhui_content,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HuaYouHuiContent huaYouHuiContent=mHuaYouHuiContentList.get(position);
        holder.BindHuaYouHuiContent(huaYouHuiContent);
    }

    @Override
    public int getItemCount() {
        return mHuaYouHuiContentList.size();
    }

}


