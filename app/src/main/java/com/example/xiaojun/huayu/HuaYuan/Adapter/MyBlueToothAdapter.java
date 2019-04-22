package com.example.xiaojun.huayu.HuaYuan.Adapter;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYuan.Bean.BlueTooth;
import com.example.xiaojun.huayu.R;

import java.util.List;

public class MyBlueToothAdapter extends RecyclerView.Adapter<MyBlueToothAdapter.ViewHolder> {
    private List<BlueTooth> BlueToothLists;
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView addressText;
        TextView nameText;
        public ViewHolder(View view){
            super(view);
            view.setOnClickListener(this);
            addressText=view.findViewById(R.id.blue_tooth_address);
            nameText=view.findViewById(R.id.blue_tooth_name);
        }
        public void BindBlueTooth(BlueTooth blueTooth){
            addressText.setText(blueTooth.getAddress());
            nameText.setText(blueTooth.getName()+":");
        }

        @Override
        public void onClick(View v) {

        }
    }
    public MyBlueToothAdapter(List<BlueTooth> list){
        BlueToothLists=list;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blue_tooth_content,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BlueTooth blueTooth=BlueToothLists.get(position);
        Log.d("bluetooth",blueTooth.toString());
        holder.BindBlueTooth(blueTooth);
    }

    @Override
    public int getItemCount() {
        return BlueToothLists.size();
    }


}
