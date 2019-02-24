package com.example.xiaojun.huayu.HuaJi.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.xiaojun.huayu.HuaJi.Adapter.HuaJiContentAdapter;
import com.example.xiaojun.huayu.HuaJi.Bean.HuaJiContent;
import com.example.xiaojun.huayu.HuaYu.Bean.BaiBaoShuContent;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HuaJiFragment extends Fragment implements View.OnClickListener{
    private static final int SEED_PLANT_UPDATE=1;
    private static final int POLYNUTRIENTFERTIZIZER_UPDATE=2;
    private static final int SOILMEDIA_UPDATE=3;
    private List<BaiBaoShuContent> BaiBaoShuContentLists=new ArrayList<>();
    private TextView SeedPlantTextView;
    private TextView PolynutrientFertilizerTextView;
    private TextView SoilMediaTextView;
    private TextView DiseaseAgentsTextView;
    private TextView PlantProductTextView;
    private TextView PlantGuideTextView;
    private HuaJiContentAdapter mAdapter;
    private List<HuaJiContent> FineGoodLists=new ArrayList<>();
    private RecyclerView recyclerView;
    private static final char SEEDPLANT='s';
    private static final char POLYNUTRIENTFERTILIZER='f';
    private static final char SOILMEDIA='m';
    private static final char DISEASEAGENTS='d';
    private static final char PLANTPRODUCT='p';
    private static final char PLANTGUIDE='g';
    private SearchView HuaJiSearchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_huaji, container, false);
        bindView(view);
        Tools.WipeSearchViewUnderLine(HuaJiSearchView);
        recyclerView=(RecyclerView)view.findViewById(R.id.huaji_recycler_content);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SEED_PLANT_UPDATE:
                    updateUI();
                    break;
                case POLYNUTRIENTFERTIZIZER_UPDATE:
                    updateUI();
                    break;
                case SOILMEDIA_UPDATE:
                    updateUI();
                    break;
            }
        }
    };
    private void bindView(View view){
        SeedPlantTextView=view.findViewById(R.id.seed_plant);
        PolynutrientFertilizerTextView=view.findViewById(R.id.polynutrient_fertilizer);
        SoilMediaTextView=view.findViewById(R.id.soil_media);
        DiseaseAgentsTextView=view.findViewById(R.id.disease_agents);
        PlantProductTextView=view.findViewById(R.id.plant_product);
        PlantGuideTextView=view.findViewById(R.id.plant_guide);
        HuaJiSearchView=view.findViewById(R.id.huaji_searchView);
        SeedPlantTextView.setOnClickListener(this);
        PolynutrientFertilizerTextView.setOnClickListener(this);
        SoilMediaTextView.setOnClickListener(this);
        DiseaseAgentsTextView.setOnClickListener(this);
        PlantProductTextView.setOnClickListener(this);
        PlantGuideTextView.setOnClickListener(this);
    }
    public void selected(){
        SeedPlantTextView.setSelected(false);
        PolynutrientFertilizerTextView.setSelected(false);
        SoilMediaTextView.setSelected(false);
        DiseaseAgentsTextView.setSelected(false);
        PlantProductTextView.setSelected(false);
        PlantGuideTextView.setSelected(false);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.seed_plant):
                AcquireUrl(SEEDPLANT,SEED_PLANT_UPDATE);
                break;
            case (R.id.polynutrient_fertilizer):
                AcquireUrl(POLYNUTRIENTFERTILIZER,POLYNUTRIENTFERTIZIZER_UPDATE);

                break;
            case (R.id.soil_media):
                AcquireUrl(SOILMEDIA,SOILMEDIA_UPDATE);

                break;
            case (R.id.disease_agents):


                break;
            case (R.id.plant_product):


                break;
            case (R.id.plant_guide):


                break;
        }
    }
    private void updateUI(){
        if(mAdapter==null) {
            mAdapter = new HuaJiContentAdapter(FineGoodLists);
            Log.d("adapter",mAdapter.toString());
            recyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
            Log.d("adapter",mAdapter.toString());
        }

    }
    private void AcquireUrl(final char GoodsSort, final int updateView) {
        BmobQuery<HuaJiContent> bmobQuery = new BmobQuery<HuaJiContent>();
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(new FindListener<HuaJiContent>() {
            @Override
            public void done(List<HuaJiContent> HuaJiContentList, BmobException e) {
                if (e == null) {
                    HashSet<HuaJiContent> set=new HashSet<>();
                    for (HuaJiContent huaJiContent: HuaJiContentList) {
                        if (huaJiContent.getGoodsSort() == GoodsSort) {
                            Log.d("sort", huaJiContent.getGoodsSort() + "");
                            set.add(huaJiContent);
                        }
                    }
                    Iterator<HuaJiContent> it=set.iterator();
                    FineGoodLists.clear();
                    while(it.hasNext()){
                        FineGoodLists.add(it.next());
                    }
                    Log.d("list",FineGoodLists.toString());
                    Message message=new Message();
                    message.what=updateView;
                    handler.sendMessage(message);

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }


            }

        });

    }
}
