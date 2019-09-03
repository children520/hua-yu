package com.example.xiaojun.huayu.HuaYuan.Fragment;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaYu.Tools.BombFetchr;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.example.xiaojun.huayu.HuaYuan.Utils.PollingUtils;
import com.example.xiaojun.huayu.R;
import com.example.xiaojun.huayu.HuaYuan.Activity.RemindSettingActivity;

import java.io.IOException;


public class PlantDetailFragment extends Fragment {
    private Plant mPlant;
    private static final String TAG="PlantDetailFragment";
    private static final String IMAGTURL="imageurl";
    private  static final String PLANTCHINESENAME="plantchinesename";
    private static final String PLANTLATINNAME="plantlatinname";
    private static final String PLANTFAMILYGENUS="plantfamilygenus";
    private static final String PLANTMORPHOLOGICALCHARACTERISTICS="plantmorphologicalcharacteristics";
    private static final String PLANTSOIL="plantsoil";

    private static final String PLANTBIRTHDAY="plantbirthday";
    private static final String PLANTDRINKTIME="plantdrinktime";
    private static final String PLANTFERTILIZATIONTIME="plantfertilizationtime";
    private static final String PLANTSCISSORTIME="plantscissortime";
    private static final String PLANTCHANGESOILTIME="plantchangesoiltime";
    private static final String PLANTBREEDTIME="plantbreedtime";

    private TextView plantChineseNameTextView;
    private ImageView plantImageView;
    private TextView plantLatinNameTextView;
    private TextView plantFamilyGenusTextView;
    private TextView plantSoilView;
    private RelativeLayout remindRelativeLayout;
    private TextView plantBirthdayTextView;
    private TextView plantDrinkTimeTextView;
    private TextView plantFertilizationTimeTextView;
    private TextView plantScissorTimeTextView;
    private TextView plantChangeSoilTimeTextView;
    private TextView plantBreedTimeTextView;
    private String PlantBirthday;

    private String ChineseName,ImageUrl,PlantLatinName,PlantFamilyGenus,PlantSoil;
    private NotificationManager notificationManager;


    private static int PlantDrinkTime,PlantFertilizationTime,PlantScissorTime,PlantChangeSoilTime,PlantBreedTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mHuaYuanContent=HuaYuanContentLab.get(getActivity()).getHuaYuanContent(plantId,imageId,plantName);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_detail, container, false);

        notificationManager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        bindView(view);
        GetExtra();

        Tools.LoadImage(plantImageView,ImageUrl);
        SetText();
        updateUI();
        RemindRelativeLayoutClick();
        return view;
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    private void SetText(){
        plantChineseNameTextView.setText(ChineseName);
        plantLatinNameTextView.setText(PlantLatinName);
        plantFamilyGenusTextView.setText(PlantFamilyGenus);
        plantSoilView.setText(PlantSoil);
        plantBirthdayTextView.setText(PlantBirthday);
    }
    private void RemindRelativeLayoutClick(){
        remindRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),RemindSettingActivity.class);
                startActivity(intent);
            }
        });
    }
    private void GetExtra(){
        ChineseName=getActivity().getIntent().getStringExtra(PLANTCHINESENAME);
        ImageUrl=getActivity().getIntent().getStringExtra(IMAGTURL);
        PlantLatinName=getActivity().getIntent().getStringExtra(PLANTLATINNAME);
        PlantFamilyGenus=getActivity().getIntent().getStringExtra(PLANTFAMILYGENUS);
        //String PlantMorphologicalCharacteristics=getActivity().getIntent().getStringExtra(PLANTMORPHOLOGICALCHARACTERISTICS);
        PlantSoil=getActivity().getIntent().getStringExtra(PLANTSOIL);
        PlantBirthday=getActivity().getIntent().getStringExtra(PLANTBIRTHDAY);
        PlantBreedTime=getActivity().getIntent().getIntExtra(PLANTBREEDTIME,1);
        PlantFertilizationTime=getActivity().getIntent().getIntExtra(PLANTFERTILIZATIONTIME,1);
        PlantScissorTime=getActivity().getIntent().getIntExtra(PLANTSCISSORTIME,1);
        PlantChangeSoilTime=getActivity().getIntent().getIntExtra(PLANTCHANGESOILTIME,1);
        PlantDrinkTime=getActivity().getIntent().getIntExtra(PLANTDRINKTIME,1);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    private void updateUI(){
        int timediff=(int)PollingUtils.timeDiff(PlantBirthday);
        if(timediff%PlantDrinkTime==0){
            plantDrinkTimeTextView.setText(PlantDrinkTime+"小时后");
        }else{
            plantDrinkTimeTextView.setText(PlantDrinkTime-(timediff%PlantDrinkTime)+"小时后");
        }
        /*
        if(timediff%PlantFertilizationTime==0){
            plantFertilizationTimeTextView.setText(PlantFertilizationTime+"小时后");
        }else{
            plantFertilizationTimeTextView.setText(PlantFertilizationTime-timediff%PlantFertilizationTime+"小时后");
        }
        if(timediff%PlantScissorTime==0){
            plantScissorTimeTextView.setText(PlantScissorTime+"小时后");
        }else{
            plantScissorTimeTextView.setText(PlantScissorTime-timediff%PlantScissorTime+"小时后");
        }
        if(timediff%PlantChangeSoilTime==0){
            plantChangeSoilTimeTextView.setText(PlantChangeSoilTime+"小时后");
        }else{
            plantChangeSoilTimeTextView.setText(PlantChangeSoilTime-timediff%PlantChangeSoilTime+"小时后");
        }
        if(timediff%PlantChangeSoilTime==0){
            plantBreedTimeTextView.setText(PlantBreedTime+"小时后");
        }else{
            plantBreedTimeTextView.setText(PlantBreedTime-timediff%PlantBreedTime+"小时后");
        }
    */

    }
    private void bindView(View view){
        plantChineseNameTextView=view.findViewById(R.id.plant_detail_chinese_name);
        plantImageView=view.findViewById(R.id.plant_detail_image);;
        plantLatinNameTextView=view.findViewById(R.id.plant_detail_latin_name);;
        plantFamilyGenusTextView=view.findViewById(R.id.plant_detail_family_genus);;
        plantSoilView=view.findViewById(R.id.plant_detail_soil);
        remindRelativeLayout=(RelativeLayout)view.findViewById(R.id.remind_setting);
        plantBirthdayTextView=view.findViewById(R.id.plant_detail_birthday);
        plantDrinkTimeTextView=view.findViewById(R.id.plant_detail_drink_water);
        plantFertilizationTimeTextView=view.findViewById(R.id.plant_detail_fertilization);
        plantScissorTimeTextView=view.findViewById(R.id.plant_detail_scissors);
        plantChangeSoilTimeTextView=view.findViewById(R.id.plant_detail_change_soil);
        plantBreedTimeTextView=view.findViewById(R.id.plant_detail_breed);
    }



}
