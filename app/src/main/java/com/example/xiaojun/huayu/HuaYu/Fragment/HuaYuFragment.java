package com.example.xiaojun.huayu.HuaYu.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xiaojun.huayu.HuaYu.Activity.ArticleDetailActivity;
import com.example.xiaojun.huayu.HuaYu.Adapter.HuaYuContentAdapter;
import com.example.xiaojun.huayu.HuaYu.Bean.HuaYuContent;
import com.example.xiaojun.huayu.HuaYu.Bean.HuaYuUrl;
import com.example.xiaojun.huayu.HuaYu.Tools.ThumbnailDownloader;
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

public class HuaYuFragment extends Fragment {
    private static final String TAG="HuaYuFragment";
    private boolean IsPullRefresh;
    private List<String> ImageHtmlList;
    private HuaYuContentAdapter mAdapter;
    private RecyclerView RecyclerView;
    List<HuaYuContent> mHuaYuContentList=new ArrayList<>();
    List<String> ImageUrlList=new ArrayList<>();
    List<String> TitleList=new ArrayList<>();
    List<HuaYuContent> SearchList=new ArrayList<>();
    private static final String URL="url";
    private static final String ECODING = "UTF-8";
    private static final int UPDATE_VIEW=1;
    private static  int URLLISTLENGTH=0;
    // 获取img标签正则
    private static final String Title_REG = "<h2 class=\"rich_media_title\" id=\"activity-name\">?(.*?)(\"|>|\\s+)</h2>";
    // 获取src路径的正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http://mmbiz.qpic.cn/mmbiz_jpg/\"?(.*?)(\"|>|\\s+)";
    private SearchView HomeSearchView;
    private SwipeRefreshLayout SwipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView progressBarTextView;
    private List<String> urlList;
    private Handler responseHandler;
    private boolean IsSearch=false;
    private static ThumbnailDownloader<HuaYuContentAdapter.ViewHolder> thumbnailDownloader;;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_huayu,container,false);
        bindView(view);
        SwipeRefresh();
        HuaYusearch();
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        RecyclerView.setLayoutManager(layoutManager);
        AcquireUrl();
        responseHandler=new Handler();
        InitThumbnailDownloader(responseHandler);
        StartThumbnailDownloader();
        return view;
    }
    private void SwipeRefresh(){
        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                    SearchList.clear();
                    urlList.clear();
                    ImageUrlList.clear();
                    TitleList.clear();
                    AcquireUrl();
                    //StartThumbnailDownloader();
            }
        });
    }
    /*
    开始后台服务
     */
    private void InitThumbnailDownloader(Handler responseHandler){
        thumbnailDownloader=new ThumbnailDownloader<>(responseHandler);
        thumbnailDownloader.setThumbnailDownloadListener(
                new ThumbnailDownloader.ThumbnailDownloadListener<HuaYuContentAdapter.ViewHolder>() {
                    @Override
                    public void onThumbnailDownloaded(HuaYuContentAdapter.ViewHolder target, Bitmap bitmap) {
                        Drawable drawable=new BitmapDrawable(getResources(),bitmap);
                        target.bindDrawable(drawable);
                    }
                }
        );


    }
    private void StartThumbnailDownloader(){
        thumbnailDownloader.start();
        thumbnailDownloader.getLooper();
        Log.i(TAG,"background thread start");
    }
    private void StopThumbnailDownloader(){
        thumbnailDownloader.quit();
        thumbnailDownloader.getLooper();
        Log.i(TAG,"background thread start");
    }
    private void HuaYusearch(){
        Tools.WipeSearchViewUnderLine(HomeSearchView);
        HomeSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(TextUtils.isEmpty(query)){
                    Toast.makeText(getActivity(),"请输入查找内容",Toast.LENGTH_SHORT).show();

                }else{
                    SearchList.clear();
                    for(HuaYuContent huaYuContent:mHuaYuContentList){
                        if(huaYuContent.getTitle().contains(query)){
                            SearchList.add(huaYuContent);
                            continue;
                        }
                    }
                    if(SearchList.size()==0){
                        Toast.makeText(getActivity(),"你查找的内容不在列表中",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"查找成功",Toast.LENGTH_SHORT).show();
                        IsSearch=true;
                        HuaYuContentAdapter SearchAdapter;
                        SearchAdapter = new HuaYuContentAdapter(SearchList,ImageUrlList,TitleList);
                        Log.d("adapter",SearchAdapter.toString());
                        RecyclerView.setAdapter(SearchAdapter);
                        SearchAdapter.notifyDataSetChanged();
                        StopThumbnailDownloader();

                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VIEW:
                    Log.d("msg",msg.toString());

                    progressBar.setVisibility(View.GONE);
                    progressBarTextView.setVisibility(View.GONE);
                    if(SwipeRefreshLayout.isRefreshing()){
                        SwipeRefreshLayout.setRefreshing(false);
                        mAdapter.notifyDataSetChanged();
                    }
                    updateUI();
                    break;
            }
        }
    };
    private void updateUI(){
        if(mAdapter==null) {
            mAdapter = new HuaYuContentAdapter(mHuaYuContentList,ImageUrlList,TitleList);
            Log.d("adapter",mAdapter.toString());
            RecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }



    }
    @Override
    public void onResume(){
        super.onResume();
        HomeSearchView.clearFocus();

    }
    @Override
    public void onDestroyView(){
        super.onDestroyView();
        thumbnailDownloader.clearQueue();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        thumbnailDownloader.quit();
        Log.i(TAG,"BackGroud thread destroy");
    }
    public static ThumbnailDownloader getThumbnailDownloaderInstance(){
        return thumbnailDownloader;
    }
    public static Intent newIntent(Context packageContext,String url){
        Intent intent=new Intent(packageContext,ArticleDetailActivity.class);
        intent.putExtra(URL,url);
        return intent;
    }
    private void bindView(View view){
        progressBar=(ProgressBar)view.findViewById(R.id.progress_bar);
        progressBarTextView=(TextView)view.findViewById(R.id.progress_bar_text);
        SwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.huayu_swipe_refresh_layout);
        RecyclerView=(RecyclerView)view.findViewById(R.id.huayu_recycler_content);
        HomeSearchView=(SearchView)view.findViewById(R.id.huayu_searchView);
    }


    private void AcquireUrl() {
        BmobQuery<HuaYuUrl> bmobQuery = new BmobQuery<HuaYuUrl>();
        bmobQuery.findObjects(new FindListener<HuaYuUrl>() {
            @Override
            public void done(List<HuaYuUrl> huaYuUrlList, BmobException e) {
                if (e == null) {
                    urlList=new ArrayList<>();
                    for (HuaYuUrl huaYuUrl : huaYuUrlList) {
                        if(!urlList.contains(huaYuUrl.getUrl())) {
                            urlList.add(huaYuUrl.getUrl());
                            Log.d(TAG,huaYuUrl.getUrl());
                        }
                    }
                    for(int i=0;i<urlList.size();i++){
                        new RequestBomb().execute(urlList.get(i));
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());

                }
            }

        });


    }
    private void LoadHuaYuList(String url,String result){
        ImageHtmlList = getImageUrl(result);
        String Title=getTitle(result).trim();
        String ImageUrl=getImageSrc(ImageHtmlList);
        if(!TitleList.contains(Title)&&!ImageUrlList.contains(ImageUrl)) {
            TitleList.add(Title);
            ImageUrlList.add(ImageUrl);
            HuaYuContent huaYuContent = new HuaYuContent(Title,ImageUrl,url);
            mHuaYuContentList.add(huaYuContent);

        }
        if(ImageUrlList.size()==urlList.size()-1){
            Message message=new Message();
            message.what=UPDATE_VIEW;
            handler.sendMessage(message);

        }
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

    public class RequestBomb extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected String doInBackground(String... Url) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(Url[0]);
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
                LoadHuaYuList(Url[0],response.toString());
                return response.toString();
            } catch (Exception e) {
                Log.d("error", e.getMessage());
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
            return null;
        }
        @Override
        protected void onPostExecute(String result){

        }

    }


}
