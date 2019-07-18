package com.example.xiaojun.huayu.HuaYuan.Fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import android.widget.ImageView;


import android.widget.ProgressBar;
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
import com.example.xiaojun.huayu.HuaYuan.Activity.BlueToothActivity;
import com.example.xiaojun.huayu.HuaYuan.Adapter.PlantContentAdapter;
import com.example.xiaojun.huayu.HuaYuan.Adapter.UserPlantContentAdapter;
import com.example.xiaojun.huayu.HuaYuan.Bean.Plant;
import com.example.xiaojun.huayu.HuaYuan.DataBase.PlantLab;
import com.example.xiaojun.huayu.HuaYuan.Utils.PollingUtils;
import com.example.xiaojun.huayu.HuaYuan.Activity.PlantDetailActivity;
import com.example.xiaojun.huayu.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

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
    private static List<Plant> mUserPlantList =new ArrayList<>();
    private List<Plant> plantList=new ArrayList<>();
    private TextView cityText, timeText, weatherText, tempText, windText, wetText;
    private WeatherSearchQuery query;
    private WeatherSearch weathersearch;
    private AMapLocationClient mlocationClient=null;
    private AMapLocationClientOption mLocationOption=null;
    private ImageView addPlant;
    private AlertDialog AddPlantAlertDialog;
    private RecyclerView mRecyclerView;
    private UserPlantContentAdapter mAdapter;
    private View AddPlantView;
    private AlertDialog.Builder AddPlantBuilder;

    private SearchView AddPlantSearchView;
    private ImageView AddPlantImageView;

    private RecyclerView AddPlantRecyclerView;
    public static TextView TPtextview,HMtextView,DItextview;
    private PlantLab plantLab;
    private Button blueBoothButton;

    private SwipeRefreshLayout HuanYuanSwipeRefreshLayout;


    @Override
    public void onCreate(Bundle paramBundle){
        super.onCreate(paramBundle);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huayuan, container, false);
        if(plantLab==null){
            plantLab=new PlantLab(getActivity());
        }
        requestLocation();
        bindView(view);
        StaggeredGridLayoutManager LayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(LayoutManager);

        updateUI();
        if(!mUserPlantList.isEmpty()){
            for(Plant plant:mUserPlantList){
                PollingUtils.startAllService(getActivity(),plant);
            }
        }
        addPlantAndBlueToothDialog();
        bindAddPlantView();
        CloseBlueAndAddPlantDialog();
        AddPlantSearch();
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
        SwipeSwipeRefreshPlantList();
        SendBroadcast();
        blueBoothConnect();

        return view;
    }
    private void SwipeSwipeRefreshPlantList(){
        HuanYuanSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                plantLab.displayPlantList(mUserPlantList);
                mAdapter = new UserPlantContentAdapter(mUserPlantList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                HuanYuanSwipeRefreshLayout.setRefreshing(false);
            }
        });
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
        requestLocation();
    }


    private void CloseBlueAndAddPlantDialog(){
        AddPlantImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlantAlertDialog.dismiss();
            }
        });

    }
    private void bindAddPlantView(){
        AddPlantSearchView=AddPlantView.findViewById(R.id.add_plant_search);
        AddPlantImageView=AddPlantView.findViewById(R.id.add_plant_image);
        AddPlantRecyclerView=AddPlantView.findViewById(R.id.add_plant_recycler_content);

    }
    private void addPlantAndBlueToothDialog(){
        AddPlantBuilder=new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        AddPlantView=layoutInflater.inflate(R.layout.add_plant_dialog,null,false);
        AddPlantBuilder.setView(AddPlantView);
        AddPlantBuilder.setCancelable(false);
        AddPlantAlertDialog=AddPlantBuilder.create();


    }
    private void bindView(View view){
        mRecyclerView = (RecyclerView) view.findViewById(R.id.huayuan_recycler_content);
        addPlant=(ImageView)view.findViewById(R.id.add_plant);
        blueBoothButton=(Button) view.findViewById(R.id.blue_connect);
        cityText = (TextView) view.findViewById(R.id.weather_city);
        timeText = (TextView) view.findViewById(R.id.weather_time);
        weatherText = (TextView) view.findViewById(R.id.weather);
        tempText = (TextView) view.findViewById(R.id.weather_temp);
        wetText = (TextView) view.findViewById(R.id.weather_wet);
        windText=(TextView)view.findViewById(R.id.weather_wind);
        HuanYuanSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.huanyuan_swipe_refresh_layout);
        TPtextview=(TextView)view.findViewById(R.id.now_temp);
        HMtextView=(TextView)view.findViewById(R.id.now_humidity);
        DItextview=(TextView)view.findViewById(R.id.now_illumination);
    }

    private void blueBoothConnect(){
        blueBoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BlueToothActivity.class);
                startActivity(intent);
            }
        });
    }


    private void SendBroadcast(){
        UpdateUIBroadcastReceiver updateUIBroadcastReceiver=new UpdateUIBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("myBroadCast");
        getActivity().registerReceiver(updateUIBroadcastReceiver,intentFilter);
    }
    private void  AddPlantSearch(){
        AddPlantSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(final String query) {
                if(TextUtils.isEmpty(query)){
                    Toast.makeText(getActivity(),"请输入查找内容",Toast.LENGTH_SHORT).show();
                }
                BmobQuery<Plant> bmobQuery = new BmobQuery<Plant>();
                bmobQuery.findObjects(new FindListener<Plant>() {
                    @Override
                    public void done(List<Plant> list, BmobException e) {
                        if (e == null) {
                            HashSet<Plant> plantSet =new HashSet<>();
                            for (Plant plant :list){
                                Log.d("name", plant.getPlantChineseName());
                                if (query.equals(plant.getPlantChineseName())){
                                    plantSet.add(plant);
                                }
                            }
                            plantList.clear();
                            Iterator<Plant> iterator=plantSet.iterator();
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
    }
    public static Intent newIntent(Context packageContext,String ImageUrl,String PlantChineseName,
                                   String PlantLatinName,String PlantFamilyGenus,
                                   String PlantMorphologicalCharacteristics,String PlantSoil,String PlantBirthday,
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
           plantLab.displayPlantList(mUserPlantList);
           mAdapter = new UserPlantContentAdapter(mUserPlantList);

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
        Log.d("提示","获取地址位置");
        mlocationClient.startLocation();

    }
    private void initLocation(){
        mlocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(new MyLocationListener());
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        if(mlocationClient!=null){
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.stopLocation();
            mlocationClient.startLocation();
        }
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        //mLocationOption.setOnceLocationLatest(true);
        //mLocationOption.setNeedAddress(true);
       //mLocationOption.setMockEnable(true);
        mLocationOption.setHttpTimeOut(20000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        PollingUtils.stopAllService(getActivity());
        mlocationClient.stopLocation();
        mlocationClient.onDestroy();
    }



    public class MySearchLocationListener implements WeatherSearch.OnWeatherSearchListener {
        @Override
        public void onWeatherLiveSearched(LocalWeatherLiveResult weatherLiveResult, int rCode) {
            if (rCode == 1000) {
                if (weatherLiveResult != null && weatherLiveResult.getLiveResult() != null) {
                    LocalWeatherLive weatherlive = weatherLiveResult.getLiveResult();
                    timeText.setText(weatherlive.getReportTime() + "发布");
                    cityText.setText(weatherlive.getCity());
                    tempText.setText(weatherlive.getTemperature() + "°");
                    wetText.setText("湿度" + weatherlive.getHumidity() + "%");
                    weatherText.setText(weatherlive.getWeather());
                    windText.setText(weatherlive.getWindDirection() + "风  " + weatherlive.getWindPower() + "级");

                }
            }else{
                Log.d("code",rCode+"");
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
                    String country =amapLocation.getCountry();

                    requestWeatherInformation(cityName);

                } else {
                    //Toast.makeText(getActivity(),"获取定位信息失败",Toast.LENGTH_SHORT).show();
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());

                }
            }
        }
    }
    public static List<Plant> getUserPlantContentList() {
        return mUserPlantList;
    }
    public class UpdateUIBroadcastReceiver extends BroadcastReceiver {
        private final String ACTION_BOOT ="myBroadCast";
        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_BOOT.equals(intent.getAction())){
                plantLab.displayPlantList(mUserPlantList);
                Log.d("mUserPlantList",mUserPlantList.toString());
                mAdapter = new UserPlantContentAdapter(mUserPlantList);
                Log.d("adapter",mAdapter.toString());
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }
        }
    }



}


