package com.example.xiaojun.huayu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.example.xiaojun.huayu.HuaYuan.PlantContent;
import com.example.xiaojun.huayu.HuaYuan.PlantContentAdapter;
import com.example.xiaojun.huayu.HuaYuan.UserPlantContentAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HuaYuanFragment extends Fragment {
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


    private static final int UPDATE_VIEW=3;
    private static List<PlantContent> mUserPlantContentList=new ArrayList<>();




    private static String PlantBirthday;
    private List<String> singleList=new ArrayList<>();
    private List<PlantContent> plantList=new ArrayList<>();
    private TextView cityText, timeText, weatherText, tempText, windText, wetText;
    private WeatherSearchQuery query;
    private WeatherSearch weathersearch;
    private AMapLocationClient mlocationClient=null;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption=null;
    private ImageView addPlant;
    private AlertDialog AddPlantAlertDialog;
    private int plantIndex;
    private RecyclerView mRecyclerView;
    private UserPlantContentAdapter mAdapter;
    private View AddPlantView;
    private AlertDialog.Builder AddPlantBuilder;
    private SearchView AddPlantSearchView;
    private ImageView AddPlantImageView;
    private ImageView IsNotAddPlantImageView;
    private RecyclerView AddPlantRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huayuan, container, false);
        requestLocation();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.huayuan_recycler_content);
        StaggeredGridLayoutManager LayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(LayoutManager);

        updateUI();

        addPlant=(ImageView)view.findViewById(R.id.add_plant);
        cityText = (TextView) view.findViewById(R.id.weather_city);
        timeText = (TextView) view.findViewById(R.id.weather_time);
        weatherText = (TextView) view.findViewById(R.id.weather);
        tempText = (TextView) view.findViewById(R.id.weather_temp);
        wetText = (TextView) view.findViewById(R.id.weather_wet);
        windText=(TextView)view.findViewById(R.id.weather_wind);

        AddPlantBuilder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        AddPlantView=layoutInflater.inflate(R.layout.add_plant_dialog,null,false);

        AddPlantSearchView=AddPlantView.findViewById(R.id.add_plant_search);
        AddPlantImageView=AddPlantView.findViewById(R.id.add_plant_image);
        AddPlantRecyclerView=AddPlantView.findViewById(R.id.add_plant_recycler_content);

        AddPlantBuilder.setView(AddPlantView);
        AddPlantBuilder.setCancelable(false);
        AddPlantAlertDialog=AddPlantBuilder.create();
        AddPlantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlantAlertDialog.dismiss();
            }
        });
        AddPlantSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                if(TextUtils.isEmpty(query)){
                    Toast.makeText(getActivity(),"请输入查找内容",Toast.LENGTH_SHORT).show();
                }

                BmobQuery<PlantContent> bmobQuery = new BmobQuery<PlantContent>();
                bmobQuery.findObjects(new FindListener<PlantContent>() {
                    @Override
                    public void done(List<PlantContent> list, BmobException e) {
                        if (e == null) {
                            HashSet<PlantContent> plantSet =new HashSet<>();
                          for (PlantContent plantContent :list){
                              Log.d("name",plantContent.getPlantChineseName());
                              if (query.equals(plantContent.getPlantChineseName())){
                                      plantSet.add(plantContent);
                                  }
                          }
                            plantList.clear();
                            Iterator<PlantContent> iterator=plantSet.iterator();
                            while (iterator.hasNext()){
                                plantList.add(iterator.next());
                            }
                            Message message=new Message();
                            message.what=UPDATE_VIEW;
                            handler.sendMessage(message);
                        } else {
                            Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        addPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plantList.clear();
                AddPlantAlertDialog.show();
                Message message=new Message();
                message.what=UPDATE_VIEW;
                handler.sendMessage(message);
            }
        });

        return view;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VIEW:
                    StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                    AddPlantRecyclerView.setLayoutManager(layoutManager);
                    PlantContentAdapter adapter = new PlantContentAdapter(plantList);
                    AddPlantRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    public void onResume(){
        super.onResume();
        updateUI();

    }
    public static Intent newIntent(Context packageContext,String ImageUrl,String PlantChineseName,
                                   String PlantLatinName,String PlantFamilyGenus,
                                   String PlantMorphologicalCharacteristics,String PlantSoil,
                                   int PlantDrinkTime, int PlantFertilizationTime,int PlantScissorTime,
                                   int PlantChangeSoilTime,int PlantBreedTime){
        Intent intent=new Intent(packageContext,PlantDetailActivity.class);
        intent.putExtra(IMAGTURL,ImageUrl);
        intent.putExtra(PLANTCHINESENAME,PlantChineseName);
        intent.putExtra(PLANTLATINNAME,PlantLatinName);
        intent.putExtra(PLANTFAMILYGENUS,PlantFamilyGenus);
        intent.putExtra(PLANTMORPHOLOGICALCHARACTERISTICS,PlantMorphologicalCharacteristics);
        intent.putExtra(PLANTSOIL,PlantSoil);

        intent.putExtra(PLANTBIRTHDAY,PlantBirthday);
        intent.putExtra(PLANTDRINKTIME,PlantDrinkTime);
        intent.putExtra(PLANTFERTILIZATIONTIME,PlantFertilizationTime);
        intent.putExtra(PLANTSCISSORTIME,PlantScissorTime);
        intent.putExtra(PLANTCHANGESOILTIME,PlantChangeSoilTime);
        intent.putExtra(PLANTBREEDTIME,PlantBreedTime);
        return intent;
    }
    private void updateUI(){

       if(mAdapter==null) {

           mAdapter = new UserPlantContentAdapter(mUserPlantContentList);
           Log.d("adapter",mAdapter.toString());
           mRecyclerView.setAdapter(mAdapter);
       }else {
           mAdapter.notifyDataSetChanged();
       }
    }
    private void AddPlantUpdateUI(){

        if(mAdapter==null) {

            mAdapter = new UserPlantContentAdapter(mUserPlantContentList);
            Log.d("adapter",mAdapter.toString());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }
    }
    private void requestWeatherInformation(String cityName){
        Log.d("city",cityName);
        query = new WeatherSearchQuery(cityName, WeatherSearchQuery.WEATHER_TYPE_LIVE);
        weathersearch = new WeatherSearch(getActivity());
        weathersearch.setOnWeatherSearchListener(new MySearchLocationListener());
        weathersearch.setQuery(query);
        weathersearch.searchWeatherAsyn();
    }
    private void requestLocation(){
        initLocation();
        mlocationClient.startLocation();

    }
    private void initLocation(){
        mlocationClient = new AMapLocationClient(getActivity().getApplicationContext());

        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new MyLocationListener());
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        if(mlocationClient!=null){
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.stopLocation();
            mlocationClient.startLocation();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(5000);
        mLocationOption.setNeedAddress(true);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mlocationClient.stopLocation();
    }



    public class MySearchLocationListener implements WeatherSearch.OnWeatherSearchListener {
        @Override
        public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
            if (rCode == 1000) {
                if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                    LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                    Log.d("weather",weatherlive.toString());
                    timeText.setText(weatherlive.getReportTime() + "发布");
                    cityText.setText(weatherlive.getCity());
                    tempText.setText(weatherlive.getTemperature() + "°");
                    wetText.setText("湿度" + weatherlive.getHumidity() + "%");
                    weatherText.setText(weatherlive.getWeather());
                    windText.setText(weatherlive.getWindDirection() + "风  " + weatherlive.getWindPower() + "级");

                }
            }else{

                Toast.makeText(getActivity(),"获取天气信息失败"+rCode,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

        }
    }


    public class MyLocationListener implements AMapLocationListener {

        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    String cityName = amapLocation.getCity().replace("市", "");
                    Log.d("city",cityName);
                    requestWeatherInformation(cityName);

                } else {
                    Toast.makeText(getActivity(),"获取定位信息失败",Toast.LENGTH_SHORT).show();
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());

                }
            }
        }
    }
    public static List<PlantContent> getUserPlantContentList() {
        return mUserPlantContentList;
    }

    public void setUserPlantContentList(List<PlantContent> userPlantContentList) {
        mUserPlantContentList = userPlantContentList;
    }
    public static void setPlantBirthday(String plantBirthday) {
        PlantBirthday = plantBirthday;
    }

}
