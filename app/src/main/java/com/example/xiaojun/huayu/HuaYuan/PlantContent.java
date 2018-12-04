package com.example.xiaojun.huayu.HuaYuan;

import cn.bmob.v3.BmobObject;

public class PlantContent extends BmobObject {
    private String ImageUrl;
    private String PlantChineseName;
    private String PlantLatinName;
    private String PlantFamilyGenus;
    private boolean IsChoice;

    private String PlantSoil;
    private String PlantMorphologicalCharacteristics;

    private int PlantDrinkTime;
    private int PlantFertilizateTime;
    private int PlantScissorTime;
    private int PlantChangeSoilTime;
    private int PlantBreedTime;

    public int getPlantDrinkTime() {
        return PlantDrinkTime;
    }

    public void setPlantDrinkTime(int plantDrinkTime) {
        PlantDrinkTime = plantDrinkTime;
    }

    public int getPlantFertilizateTime() {
        return PlantFertilizateTime;
    }

    public void setPlantFertilizateTime(int plantFertilizateTime) {
        PlantFertilizateTime = plantFertilizateTime;
    }

    public int getPlantScissorTime() {
        return PlantScissorTime;
    }

    public void setPlantScissorTime(int plantScissorTime) {
        PlantScissorTime = plantScissorTime;
    }

    public int getPlantChangeSoilTime() {
        return PlantChangeSoilTime;
    }

    public void setPlantChangeSoilTime(int plantChangeSoilTime) {
        PlantChangeSoilTime = plantChangeSoilTime;
    }

    public int getPlantBreedTime() {
        return PlantBreedTime;
    }

    public void setPlantChangeBreedTime(int plantBreedTime) {
        PlantBreedTime = plantBreedTime;
    }





    public boolean isChoice() {
        return IsChoice;
    }

    public void setChoice(boolean choice) {
        IsChoice = choice;
    }

    public String getPlantSoil() {
        return PlantSoil;
    }

    public void setPlantSoil(String plantSoil) {
        PlantSoil = plantSoil;
    }



    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPlantChineseName() {
        return PlantChineseName;
    }

    public void setPlantChineseName(String plantChineseName) {
        PlantChineseName = plantChineseName;
    }

    public String getPlantLatinName() {
        return PlantLatinName;
    }

    public void setPlantLatinName(String plantLatinName) {
        PlantLatinName = plantLatinName;
    }

    public String getPlantFamilyGenus() {
        return PlantFamilyGenus;
    }

    public void setPlantFamilyGenus(String plantFamilyGenus) {
        PlantFamilyGenus = plantFamilyGenus;
    }

    public String getPlantMorphologicalCharacteristics() {
        return PlantMorphologicalCharacteristics;
    }

    public void setPlantMorphologicalCharacteristics(String plantMorphologicalCharacteristics) {
        PlantMorphologicalCharacteristics = plantMorphologicalCharacteristics;
    }




}
