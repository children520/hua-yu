package com.example.xiaojun.huayu.HuaYuan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYuanFragment;
import com.example.xiaojun.huayu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlantContentAdapter extends RecyclerView.Adapter<PlantContentAdapter.ViewHolder> {
    private List<PlantContent> mPlantContentList;


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mChineseNameView;
        private TextView mLatinNameView;
        private TextView mFamilyGenusView;
        private TextView mSoilView;
        private PlantContent mPlantContent;
        private ImageView IsNotAddPlantImageView;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView=view.findViewById(R.id.plant_image);
            mChineseNameView=view.findViewById(R.id.plant_chinese_name);
            mLatinNameView=view.findViewById(R.id.plant_latin_name);
            mFamilyGenusView=view.findViewById(R.id.plant_family_genus);
            mSoilView=view.findViewById(R.id.plant_soil);
            IsNotAddPlantImageView=view.findViewById(R.id.is_not_add_plant);

        }
        public void bindPlantContent(final PlantContent plantContent){
            mPlantContent=plantContent;
            mPlantContent.setChoice(true);
            mImageView.setImageResource(R.mipmap.apple);
            mChineseNameView.setText(mPlantContent.getPlantChineseName());
            mLatinNameView.setText(mPlantContent.getPlantLatinName());
            mFamilyGenusView.setText(mPlantContent.getPlantFamilyGenus());
            mSoilView.setText(mPlantContent.getPlantSoil());
            IsNotAddPlantImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog alertDialog=null;
                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    alertDialog=builder.setTitle("温馨提示").setMessage("确定要将该种植物添加到我的植物里么？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(HuaYuanFragment.getUserPlantContentList().size()==0){
                                        HuaYuanFragment.getUserPlantContentList().add(plantContent);
                                        Toast.makeText(v.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                    }else{
                                        for(PlantContent content:HuaYuanFragment.getUserPlantContentList()){
                                            if(plantContent.getPlantChineseName().equals(content.getPlantChineseName())){
                                                Toast.makeText(v.getContext(),"不能重复添加哦",Toast.LENGTH_SHORT).show();
                                                break;
                                            }else{
                                                HuaYuanFragment.getUserPlantContentList().add(plantContent);
                                                Toast.makeText(v.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                                break;
                                            }
                                        }
                                    }


                                }
                            }).create();
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    HuaYuanFragment.setPlantBirthday(simpleDateFormat.format(date));
                    alertDialog.show();


                }
            });
        }

        @Override
        public void onClick(View v) {

        }

    }

    public PlantContentAdapter(List<PlantContent> plantContentList){
        mPlantContentList=plantContentList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_content,null,false));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlantContent plantContent=mPlantContentList.get(position);
        holder.bindPlantContent(plantContent);

    }

    @Override
    public int getItemCount() {
        return mPlantContentList.size();
    }

}
