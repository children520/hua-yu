package com.example.xiaojun.huayu.HuaYuan;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.R;
import com.example.xiaojun.huayu.RemindSettingActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PlantDetailFragment extends Fragment {
    private Plant mPlant;
    private static final String IMAGTURL="imageurl";
    private  static final String PLANTCHINESENAME="plantchinesename";
    private static final String PLANTLATINNAME="plantlatinname";
    private static final String PLANTFAMILYGENUS="plantfamilygenus";
    private static final String PLANTMORPHOLOGICALCHARACTERISTICS="plantmorphologicalcharacteristics";
    private static final String PLANTSOIL="plantsoil";

    private static final String PLANTBIRTHDAY="plantbirthday";

    private TextView plantChineseNameTextView;
    private TextView plantImageUrlTextView;
    private ImageView plantImageView;
    private TextView plantLatinNameTextView;
    private TextView plantFamilyGenusTextView;
    private TextView plantMorphologicalCharacteristicsTextView;
    private TextView plantSoilView;
    private LinearLayout mLinearLayout;
    private TextView plantBirthdayTextView;
    private TextView plantDrinkTimeTextView;
    private TextView plantFertilizationTimeTextView;
    private TextView plantScissorTimeTextView;
    private TextView plantChangeSoilTimeTextView;
    private TextView plantBreedTimeTextView;
    private String PlantBirthday;

    private String ChineseName;
    private Bitmap AppBitmap;
    private NotificationManager notificationManager;
    private Notification DrinkNotify,FertilizationNotify,ScissorNotify,ChangeSoilNotify,BreedNotify;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //mHuaYuanContent=HuaYuanContentLab.get(getActivity()).getHuaYuanContent(plantId,imageId,plantName);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plant_detail, container, false);
        AppBitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.app);
        notificationManager=(NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mLinearLayout=(LinearLayout)view.findViewById(R.id.remind_setting);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(getActivity(),RemindSettingActivity.class);
              startActivity(intent);
            }
        });

        ChineseName=getActivity().getIntent().getStringExtra(PLANTCHINESENAME);
        //String ImageUrl=getActivity().getIntent().getStringExtra(IMAGTURL);
        String PlantLatinName=getActivity().getIntent().getStringExtra(PLANTLATINNAME);
        String PlantFamilyGenus=getActivity().getIntent().getStringExtra(PLANTFAMILYGENUS);
        //String PlantMorphologicalCharacteristics=getActivity().getIntent().getStringExtra(PLANTMORPHOLOGICALCHARACTERISTICS);
        String PlantSoil=getActivity().getIntent().getStringExtra(PLANTSOIL);



        bindView(view);


        plantChineseNameTextView.setText(ChineseName);
        plantLatinNameTextView.setText(PlantLatinName);
        plantFamilyGenusTextView.setText(PlantFamilyGenus);
        plantSoilView.setText(PlantSoil);
        plantBirthdayTextView.setText(PlantBirthday);

        updateUI();

        return view;
    }
    @Override
    public void onResume(){
        super.onResume();

        updateUI();

    }

    private void updateUI(){
        /*
        plantDrinkTimeTextView.setText(vPlantDrinkTime+"小时后");
        plantFertilizationTimeTextView.setText(vPlantFertilizationTime+"小时后");
        plantScissorTimeTextView.setText(vPlantScissorTime+"天后");
        plantChangeSoilTimeTextView.setText(vPlantChangeSoilTime+"天后");
        plantBreedTimeTextView.setText(vPlantBreedTime+"天后");
        */
    }
    private void bindView(View view){
        plantChineseNameTextView=view.findViewById(R.id.plant_detail_chinese_name);
        plantImageView=view.findViewById(R.id.plant_detail_image);;
        plantLatinNameTextView=view.findViewById(R.id.plant_detail_latin_name);;
        plantFamilyGenusTextView=view.findViewById(R.id.plant_detail_family_genus);;
        plantSoilView=view.findViewById(R.id.plant_detail_soil);

        plantBirthdayTextView=view.findViewById(R.id.plant_detail_birthday);
        plantDrinkTimeTextView=view.findViewById(R.id.plant_detail_drink_water);
        plantFertilizationTimeTextView=view.findViewById(R.id.plant_detail_fertilization);
        plantScissorTimeTextView=view.findViewById(R.id.plant_detail_scissors);
        plantChangeSoilTimeTextView=view.findViewById(R.id.plant_detail_change_soil);
        plantBreedTimeTextView=view.findViewById(R.id.plant_detail_breed);
    }
    /*
    private void  CheckDrinkNotificate(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("remindSetting",Context.MODE_PRIVATE);
        boolean IsDrinkOpen=sharedPreferences.getBoolean("IsDrinkOpen",false);
        if(vPlantBreedTime==8&&IsDrinkOpen){
            Intent intent=new Intent(getActivity(),PlantDetailFragment.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(getActivity(),0,intent,0);
            Notification.Builder builder=new Notification.Builder(getActivity());
            builder.setContentTitle("花屿通知")
                    .setContentText("您的"+ChineseName+"植物需要浇水啦！")
                    .setTicker("来自花屿的通知信息")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.apple)
                    .setLargeIcon(AppBitmap)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            DrinkNotify=builder.build();
            notificationManager.notify(1,DrinkNotify);
        }

    }
    */
}
