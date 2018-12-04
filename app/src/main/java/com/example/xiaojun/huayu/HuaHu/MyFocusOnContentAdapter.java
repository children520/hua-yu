package com.example.xiaojun.huayu.HuaHu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.xiaojun.huayu.R;

import java.util.List;

public class MyFocusOnContentAdapter  extends RecyclerView.Adapter< MyFocusOnContentAdapter.ViewHolder> {
private List<MyFocusOnContent> mMyFocusOnContentsList;
static class ViewHolder extends RecyclerView.ViewHolder{
    private ImageView mImageView;
    private TextView mTextView;
    private ToggleButton mToggleButton;

    public ViewHolder(View view) {
        super(view);
        mImageView=(ImageView)view.findViewById(R.id.myfocuson_content_image);
        mTextView=(TextView)view.findViewById(R.id.myfocuson_username);
        mToggleButton=(ToggleButton)view.findViewById(R.id.myfocus_isfocuson);
    }
}
    public MyFocusOnContentAdapter(List<MyFocusOnContent> MyFocusOnContentsList){
        mMyFocusOnContentsList=MyFocusOnContentsList;
    }
    @Override
    public MyFocusOnContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.myfocuson_content,parent,false);
        MyFocusOnContentAdapter.ViewHolder holder=new MyFocusOnContentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyFocusOnContentAdapter.ViewHolder holder, int position) {
        MyFocusOnContent myFocusOnContent=mMyFocusOnContentsList.get(position);
        holder.mImageView.setImageResource(myFocusOnContent.getImageId());
        holder.mTextView.setText(myFocusOnContent.getUserName());
        holder.mToggleButton.setChecked(myFocusOnContent.isFocusOn());
    }



    @Override
    public int getItemCount() {
        return mMyFocusOnContentsList.size();
    }

}
