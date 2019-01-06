package com.example.xiaojun.huayu.HuaYuan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HomeActivity;
import com.example.xiaojun.huayu.HuaYuanFragment;
import com.example.xiaojun.huayu.NewUserRegist;
import com.example.xiaojun.huayu.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PlantContentAdapter extends RecyclerView.Adapter<PlantContentAdapter.ViewHolder> {
    private List<Plant> mPlantList;


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mChineseNameView;
        private TextView mLatinNameView;
        private TextView mFamilyGenusView;
        private TextView mSoilView;
        private Plant mPlant;
        private ImageView IsNotAddPlantImageView;
        private PlantLab mPlantLab;
        private String NowTime;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView=view.findViewById(R.id.plant_image);
            mChineseNameView=view.findViewById(R.id.plant_chinese_name);
            mLatinNameView=view.findViewById(R.id.plant_latin_name);
            mFamilyGenusView=view.findViewById(R.id.plant_family_genus);
            mSoilView=view.findViewById(R.id.plant_soil);
            IsNotAddPlantImageView=view.findViewById(R.id.is_not_add_plant);
            if (mPlantLab==null){
                mPlantLab=new PlantLab(view.getContext());
            }
        }
        public void bindPlantContent(final Plant plant){

            mPlant = plant;
            mPlant.setChoice(true);
            mImageView.setImageResource(R.mipmap.apple);
            mChineseNameView.setText(mPlant.getPlantChineseName());
            mLatinNameView.setText(mPlant.getPlantLatinName());
            mFamilyGenusView.setText(mPlant.getPlantFamilyGenus());
            mSoilView.setText(mPlant.getPlantSoil());
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

                                        long flag=  mPlantLab.addPlant(plant);
                                        if(flag==-1){
                                            Toast.makeText(v.getContext(),"添加失败",Toast.LENGTH_SHORT).show();
                                        }else {

                                            Toast.makeText(v.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                        }


                                    }else{
                                        for(Plant content:HuaYuanFragment.getUserPlantContentList()){
                                            if(plant.getPlantChineseName().equals(content.getPlantChineseName())){
                                                Toast.makeText(v.getContext(),"不能重复添加哦",Toast.LENGTH_SHORT).show();
                                                break;
                                            }else{
                                                long flag=  mPlantLab.addPlant(plant);
                                                if(flag==-1){
                                                    Toast.makeText(v.getContext(),"添加失败",Toast.LENGTH_SHORT).show();
                                                }else {
                                                    Toast.makeText(v.getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                                                }
                                                break;
                                            }
                                        }
                                    }


                                }
                            }).create();

                    alertDialog.show();


                }
            });
        }

        @Override
        public void onClick(View v) {

        }

    }

    public PlantContentAdapter(List<Plant> plantList){
        mPlantList = plantList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_content,null,false));
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Plant plant = mPlantList.get(position);
        holder.bindPlantContent(plant);

    }

    @Override
    public int getItemCount() {
        return mPlantList.size();
    }

}
