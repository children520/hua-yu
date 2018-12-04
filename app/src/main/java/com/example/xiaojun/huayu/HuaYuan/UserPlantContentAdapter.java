package com.example.xiaojun.huayu.HuaYuan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYuanFragment;
import com.example.xiaojun.huayu.PlantDetailActivity;
import com.example.xiaojun.huayu.R;

import java.util.List;

public class UserPlantContentAdapter extends RecyclerView.Adapter<UserPlantContentAdapter.ViewHolder> {
    private List<PlantContent> mPlantContentList;
    private static final int LAYOUT_TYPE_ONE=1;
    private static final int LAYOUT_TYPE_TWO=2;

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


        }
        public void bindPlantContent(final PlantContent plantContent){
            mPlantContent=plantContent;
            mPlantContent.setChoice(true);
            mImageView.setImageResource(R.mipmap.apple);
            mChineseNameView.setText(mPlantContent.getPlantChineseName());
            mLatinNameView.setText(mPlantContent.getPlantLatinName());
            mFamilyGenusView.setText(mPlantContent.getPlantFamilyGenus());
            mSoilView.setText(mPlantContent.getPlantSoil());

        }

        @Override
        public void onClick(View v) {
            Intent intent=HuaYuanFragment.newIntent(v.getContext(),mPlantContent.getImageUrl(),
                    mPlantContent.getPlantChineseName(),mPlantContent.getPlantLatinName(),
                    mPlantContent.getPlantFamilyGenus(),mPlantContent.getPlantMorphologicalCharacteristics(),
                    mPlantContent.getPlantSoil(),mPlantContent.getPlantDrinkTime(),mPlantContent.getPlantFertilizateTime(),
                    mPlantContent.getPlantScissorTime(),mPlantContent.getPlantChangeSoilTime(),mPlantContent.getPlantBreedTime());
            v.getContext().startActivity(intent);
        }

    }

    public UserPlantContentAdapter(List<PlantContent> plantContentList){
        mPlantContentList=plantContentList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder= new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_plant_content,null,false));
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

