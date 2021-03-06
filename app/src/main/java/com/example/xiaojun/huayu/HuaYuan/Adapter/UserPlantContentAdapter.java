package com.example.xiaojun.huayu.HuaYuan.Adapter;

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

import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.example.xiaojun.huayu.HuaYuan.DataBase.PlantLab;
import com.example.xiaojun.huayu.HuaYuan.Fragment.HuaYuanFragment;
import com.example.xiaojun.huayu.HuaYuan.Utils.PollingUtils;
import com.example.xiaojun.huayu.R;

import java.util.List;

public class UserPlantContentAdapter extends RecyclerView.Adapter<UserPlantContentAdapter.ViewHolder> {
    private List<Plant> mPlantList;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mChineseNameView;
        private TextView mLatinNameView;
        private TextView mFamilyGenusView;
        private TextView mSoilView;
        private Plant mPlant;
        private ImageView deletePlantImageView;
        private PlantLab plantLab;
        public ViewHolder(View view) {
            super(view);
            plantLab=new PlantLab(view.getContext());
            view.setOnClickListener(this);
            mImageView=view.findViewById(R.id.plant_image);
            mChineseNameView=view.findViewById(R.id.plant_chinese_name);
            mLatinNameView=view.findViewById(R.id.plant_latin_name);
            mFamilyGenusView=view.findViewById(R.id.plant_family_genus);
            mSoilView=view.findViewById(R.id.plant_soil);
            deletePlantImageView=view.findViewById(R.id.delete_plant);

        }
        public void bindPlantContent(final Plant plant){
            mPlant = plant;
            Log.d("getPlantBreedTime()",mPlant.getPlantBreedTime()+"");
            Tools.LoadImage(mImageView,plant.getImageUrl());
            mChineseNameView.setText(mPlant.getPlantChineseName());
            mLatinNameView.setText(mPlant.getPlantLatinName());
            mFamilyGenusView.setText(mPlant.getPlantFamilyGenus());
            mSoilView.setText(mPlant.getPlantSoil());
            deletePlantImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog alertDialog=null;
                    AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                    alertDialog=builder.setTitle("温馨提示").setMessage("确定要将该种植物从我的植物删除么？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    plantLab.deletePlant(mPlant);
                                    v.getContext().sendBroadcast(new Intent("deleteplant"));


                                }
                            }).create();

                    alertDialog.show();

                }
            });
        }

        @Override
        public void onClick(View v) {

            Intent intent=HuaYuanFragment.newIntent(v.getContext(), mPlant.getImageUrl(),
                    mPlant.getPlantChineseName(), mPlant.getPlantLatinName(),
                    mPlant.getPlantFamilyGenus(), mPlant.getPlantMorphologicalCharacteristics(),
                    mPlant.getPlantSoil(),mPlant.getPlantBirthday(),  mPlant.getPlantDrinkTime(), mPlant.getPlantFertilizateTime(),
                    mPlant.getPlantScissorTime(), mPlant.getPlantChangeSoilTime(), mPlant.getPlantBreedTime());
            v.getContext().startActivity(intent);


        }

    }

    public UserPlantContentAdapter(List<Plant> plantList){
        mPlantList = plantList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_plant_content,null,false));
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

