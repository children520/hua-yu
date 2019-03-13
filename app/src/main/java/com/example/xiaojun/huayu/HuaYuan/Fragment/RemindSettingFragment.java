package com.example.xiaojun.huayu.HuaYuan.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.xiaojun.huayu.HuaYuan.Service.DrinkService;
import com.example.xiaojun.huayu.HuaYuan.Utils.PollingUtils;
import com.example.xiaojun.huayu.R;

public class RemindSettingFragment extends Fragment {
    private Switch DrinkSwitch;
    private Switch ScissorSwitch;
    private Switch FertilizationSwitch;
    private Switch ChangeSoilSwitch;
    private Switch BreedSwitch;
    private static boolean IsDrinkOpen;
    private static boolean IsScissorOpen;
    private static boolean IsFertilizationOpen;
    private static boolean IsChangeSoilOpen;
    private static boolean IsBreedOpen;
    public static final String ISDRINKOPEN="IsDrinkOpen";
    public static final String ISSCISSOROPEN="IsDrinkOpen";
    public static final String ISFERTILIZATIONOPEN="IsFertilizationOpen";
    public static final String ISCHANGESOILOPEN="IsChangeSoilOpen";
    public static final String ISBREEDOPEN="IsBreedOpen";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_setting, container, false);
        bindView(view);
        checkSwitchState();
        DrinkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    storeSwitchStage(ISDRINKOPEN,isChecked);
                }else {
                    storeSwitchStage(ISDRINKOPEN,isChecked);
                }
            }
        });
        ScissorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    storeSwitchStage(ISSCISSOROPEN,isChecked);
                }else {
                    storeSwitchStage(ISSCISSOROPEN,isChecked);
                }
            }
        });
        FertilizationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    storeSwitchStage(ISFERTILIZATIONOPEN,isChecked);
                }else {
                    storeSwitchStage(ISFERTILIZATIONOPEN,isChecked);
                }
            }
        });
        ChangeSoilSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    storeSwitchStage(ISCHANGESOILOPEN,isChecked);
                }else {
                    storeSwitchStage(ISCHANGESOILOPEN,isChecked);
                }
            }
        });
        BreedSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    storeSwitchStage(ISBREEDOPEN,isChecked);
                }else {
                    storeSwitchStage(ISBREEDOPEN,isChecked);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume(){
        checkSwitchState();
        super.onResume();

}
    public void bindView(View view){
        DrinkSwitch=view.findViewById(R.id.drink_switch);
        ScissorSwitch=view.findViewById(R.id.scissor_switch);
        FertilizationSwitch=view.findViewById(R.id.fertilization_switch);
        ChangeSoilSwitch=view.findViewById(R.id.change_soil_switch);
        BreedSwitch=view.findViewById(R.id.breed_switch);
    }
    public static boolean getSwitchState(Context context,String key){
        boolean SwitchState;
        SharedPreferences sharedPreferences=context.getSharedPreferences("remindSetting",Context.MODE_PRIVATE);
        SwitchState=sharedPreferences.getBoolean(key,true);
        return SwitchState;
    }
    public void checkSwitchState(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("remindSetting",Context.MODE_PRIVATE);
        IsDrinkOpen=sharedPreferences.getBoolean(ISDRINKOPEN,true);
        DrinkSwitch.setChecked(IsDrinkOpen);
        IsBreedOpen=sharedPreferences.getBoolean(ISBREEDOPEN,true);
        BreedSwitch.setChecked(IsBreedOpen);
        IsChangeSoilOpen=sharedPreferences.getBoolean(ISCHANGESOILOPEN,true);
        ChangeSoilSwitch.setChecked(IsChangeSoilOpen);
        IsFertilizationOpen=sharedPreferences.getBoolean(ISFERTILIZATIONOPEN,true);
        FertilizationSwitch.setChecked(IsFertilizationOpen);
        IsScissorOpen=sharedPreferences.getBoolean(ISSCISSOROPEN,true);
        ScissorSwitch.setChecked(IsScissorOpen);
    }

    public void storeSwitchStage(String key,boolean value){
        SharedPreferences.Editor editor=getActivity().getSharedPreferences("remindSetting",Context.MODE_PRIVATE).edit();
        editor.putBoolean(key,value);
        editor.apply();
    }


}
