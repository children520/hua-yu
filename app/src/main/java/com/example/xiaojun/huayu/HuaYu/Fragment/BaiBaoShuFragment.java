package com.example.xiaojun.huayu.HuaYu.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.xiaojun.huayu.ArticleDetailActivity;
import com.example.xiaojun.huayu.HuaYu.Adapter.BaiBaoShuContentAdapter;
import com.example.xiaojun.huayu.HuaYu.Bean.BaiBaoShuContent;
import com.example.xiaojun.huayu.HuaYu.Bean.BaiBaoShuUrl;
import com.example.xiaojun.huayu.HuaYu.Tools.Tools;
import com.example.xiaojun.huayu.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class BaiBaoShuFragment extends Fragment {
    private boolean IsPullRefresh;
    private BaiBaoShuContentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<String> ImageUrlList;
    private List<String> TitleList=new ArrayList<>();
    private  List<String> ImageList=new ArrayList<>();
    List<BaiBaoShuContent> SearchList=new ArrayList<>();
    private static final int UPDATE_BAIBAOSHU_VIEW=2;
    private static final String URL="url";
    private List<BaiBaoShuContent> BaiBaoShuContentLists=new ArrayList<>();
    private static final String Title_REG = "<h2 class=\"rich_media_title\" id=\"activity-name\">?(.*?)(\"|>|\\s+)</h2>";
    // 获取src路径的正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http://mmbiz.qpic.cn/mmbiz_jpg/\"?(.*?)(\"|>|\\s+)";
    private SearchView BaiBaoShuSearchView;
    private SwipeRefreshLayout BaiBaoShuSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baibaoshu, container, false);
        BaiBaoShuSearchView=(SearchView)view.findViewById(R.id.baibaoshu_searchView);
        BaiBaoShuSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.baibaoshu_swipe_refresh_layout);
        BaiBaoShuSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AcquireUrl();
                IsPullRefresh=true;

            }
        });
        Tools.WipeSearchViewUnderLine(BaiBaoShuSearchView);
        BaiBaoShuSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query)){
                    Toast.makeText(getActivity(),"请输入查找内容",Toast.LENGTH_SHORT).show();

                }else{
                    SearchList.clear();
                    for(BaiBaoShuContent baiBaoShuContent:BaiBaoShuContentLists){
                        if(baiBaoShuContent.getTitle().contains(query)){
                            SearchList.add(baiBaoShuContent);
                            continue;
                        }
                    }
                    if(SearchList.size()==0){
                        Toast.makeText(getActivity(),"你查找的内容不在列表中",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"查找成功",Toast.LENGTH_SHORT).show();
                        Log.d("TitleList",TitleList.toString());
                        Log.d("imageList",ImageList.toString());
                        BaiBaoShuContentAdapter SearchAdapter;
                        SearchAdapter = new BaiBaoShuContentAdapter(SearchList);
                        Log.d("adapter",SearchAdapter.toString());
                        mRecyclerView.setAdapter(SearchAdapter);
                        SearchAdapter.notifyDataSetChanged();

                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mRecyclerView=(RecyclerView)view.findViewById(R.id.baibaoshu_recycler_content);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        AcquireUrl();
        return view;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_BAIBAOSHU_VIEW:
                    updateUI();
                    BaiBaoShuSwipeRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    @Override
    public void onResume(){
        super.onResume();
        BaiBaoShuSearchView.clearFocus();
        updateUI();
    }
    private void updateUI(){
        if(mAdapter==null) {
            mAdapter = new BaiBaoShuContentAdapter(BaiBaoShuContentLists);
            Log.d("adapter",mAdapter.toString());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }


    }

    private void AcquireUrl() {
        BmobQuery<BaiBaoShuUrl> bmobQuery = new BmobQuery<BaiBaoShuUrl>();
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(new FindListener<BaiBaoShuUrl>() {
            @Override
            public void done(List<BaiBaoShuUrl> baiBaoShuUrlList, BmobException e) {
                if (e == null) {
                    List<String> urlList=new ArrayList<>();
                    for (BaiBaoShuUrl baiBaoShuUrl : baiBaoShuUrlList) {
                        if (!urlList.contains(baiBaoShuUrl.getBaiBaoShuUrl())) {
                            urlList.add(baiBaoShuUrl.getBaiBaoShuUrl());
                        }
                    }
                    Log.d("urlList",urlList.toString());
                    for(int i=0;i<urlList.size();i++){
                        sendRequestWithHttpURLConnection(urlList.get(i));
                    }

                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }


            }

        });

    }
    public void newIntent(String url){
        Intent intent=new Intent(getActivity(),ArticleDetailActivity.class);
        Log.d("url",url);
        intent.putExtra(URL,url);
        startActivity(intent);

    }
    public void sendRequestWithHttpURLConnection(final String Url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    Log.d("url",Url);
                    URL url = new URL(Url);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    String html = "" + response;

                    ImageUrlList = getImageUrl(html);
                    String Title=getTitle(html).trim();
                    String ImageUrl=getImageSrc(ImageUrlList);

                    Log.d("Image",ImageUrl);
                    Log.d("Title", Title);
                    if(IsPullRefresh){
                        TitleList.clear();
                        ImageList.clear();
                        BaiBaoShuContentLists.clear();
                        IsPullRefresh=false;
                    }
                    if(!TitleList.contains(Title)&&!ImageList.contains(ImageUrl)) {
                        TitleList.add(Title);
                        Log.d("TitleList", TitleList.toString());
                        ImageList.add(ImageUrl);
                        Log.d("ImageUrlList", ImageList.toString());
                        BaiBaoShuContent baiBaoShuContent = new BaiBaoShuContent(ImageUrl, Title, Url);
                        Log.d("BaiBaoShuContent", baiBaoShuContent.toString());
                        BaiBaoShuContentLists.add(baiBaoShuContent);
                        Log.d("List", BaiBaoShuContentLists.toString());
                    }

                    Message message=new Message();
                    message.what=UPDATE_BAIBAOSHU_VIEW;
                    handler.sendMessage(message);


                    return ;
                } catch (Exception e) {
                    Log.d("error",e.getMessage());
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private String getTitle(String HTML){
        String Title="";
        Matcher matcher=Pattern.compile(Title_REG).matcher(HTML);
        while (matcher.find()){
            Title=matcher.group(1);
        }
        return Title;
    }
    private List<String> getImageUrl(String HTML) {
        Matcher matcher = Pattern.compile(IMGURL_REG).matcher(HTML);
        List<String> listImgUrl = new ArrayList<String>();
        while (matcher.find()) {
            listImgUrl.add(matcher.group());
        }
        return listImgUrl;
    }

    private String getImageSrc(List<String> listImageUrl) {
        String ImgSrc="";
        for (String image : listImageUrl) {
            Matcher matcher = Pattern.compile(IMGSRC_REG).matcher(image);
            while (matcher.find()) {
                ImgSrc=matcher.group().substring(0, matcher.group().length() - 1);
            }
        }
        return ImgSrc;
    }
}
