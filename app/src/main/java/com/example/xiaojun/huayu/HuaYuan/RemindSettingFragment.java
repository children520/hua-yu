package com.example.xiaojun.huayu.HuaYuan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remind_setting, container, false);
        DrinkSwitch=view.findViewById(R.id.drink_switch);
        ScissorSwitch=view.findViewById(R.id.scissor_switch);
        FertilizationSwitch=view.findViewById(R.id.fertilization_switch);
        ChangeSoilSwitch=view.findViewById(R.id.change_soil_switch);
        BreedSwitch=view.findViewById(R.id.breed_switch);
        DrinkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    IsDrinkOpen=true;
                    DrinkSwitch.setChecked(true);
                }else {
                    IsDrinkOpen=false;

                }
            }
        });
        return view;
    }
    public static boolean isIsDrinkOpen() {
        return IsDrinkOpen;
    }

    public static boolean isIsScissorOpen() {
        return IsScissorOpen;
    }

    public static boolean isIsFertilizationOpen() {
        return IsFertilizationOpen;
    }

    public static boolean isIsChangeSoilOpen() {
        return IsChangeSoilOpen;
    }

    public static boolean isIsBreedOpen() {
        return IsBreedOpen;
    }

}
