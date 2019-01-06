package com.example.xiaojun.huayu;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
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

import com.example.xiaojun.huayu.HuaYu.BaiBaoShuContentAdapter;
import com.example.xiaojun.huayu.HuaYu.HuaYuContent;
import com.example.xiaojun.huayu.HuaYu.WebInformation;

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
    private boolean IsPullRefresh;
    private List<String> ImageUrlList;
    private HuaYuContentAdapter mAdapter;
    private RecyclerView mRecyclerView;
    List<HuaYuContent> mHuaYuContentList=new ArrayList<>();
    List<String> ImageList=new ArrayList<>();
    List<String> TitleList=new ArrayList<>();
    List<HuaYuContent> SearchList=new ArrayList<>();
    private static final String URL="url";
    private static final String ECODING = "UTF-8";
    private static final int UPDATE_VIEW=1;
    // 获取img标签正则
    private static final String Title_REG = "<h2 class=\"rich_media_title\" id=\"activity-name\">?(.*?)(\"|>|\\s+)</h2>";
    // 获取src路径的正则
    private static final String IMGURL_REG = "<img.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String IMGSRC_REG = "http://mmbiz.qpic.cn/mmbiz_jpg/\"?(.*?)(\"|>|\\s+)";
    private SearchView HomeSearchView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_huayu,container,false);

        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(
                new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        mSwipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_refresh_layout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AcquireUrl();
                IsPullRefresh=true;

            }
        });
        mRecyclerView=(RecyclerView)view.findViewById(R.id.recycler_content);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        AcquireUrl();
        HomeSearchView=(SearchView)view.findViewById(R.id.home_searchView);
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
                        HuaYuContentAdapter SearchAdapter;
                        SearchAdapter = new HuaYuContentAdapter(SearchList,ImageUrlList,TitleList);
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
        return view;
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDATE_VIEW:
                    updateUI();
                    if(mSwipeRefreshLayout.isRefreshing()){
                        mSwipeRefreshLayout.setRefreshing(false);
                        mAdapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    };
    private void updateUI(){
        if(mAdapter==null) {
            mAdapter = new HuaYuContentAdapter(mHuaYuContentList,ImageUrlList,TitleList);
            Log.d("adapter",mAdapter.toString());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.notifyDataSetChanged();
        }



    }
    @Override
    public void onResume(){
        super.onResume();
        HomeSearchView.clearFocus();

    }

    public static Intent newIntent(Context packageContext,String url){
        Intent intent=new Intent(packageContext,ArticleDetailActivity.class);
        intent.putExtra(URL,url);
        return intent;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private TextView mTitleView;
        private TextView mContentView;
        private HuaYuContent mHuaYuContent;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mImageView = (ImageView) view.findViewById(R.id.huayu_content_image);
            mTitleView = (TextView) view.findViewById(R.id.huayu_content_title);
            mContentView=(TextView)view.findViewById(R.id.huayu_content_content);

        }
        public void bindHuaYuContent(HuaYuContent huaYuContent){
            mHuaYuContent=huaYuContent;
            try{
               DownloadImageTask downloadImageTask=new DownloadImageTask();
                Bitmap bitmap=downloadImageTask.doInBackground(mHuaYuContent.getImageId());
                mImageView.setImageBitmap(bitmap);
                mTitleView.setText(mHuaYuContent.getTitle().split("\\|\\|")[0]);
                mContentView.setText(mHuaYuContent.getTitle().split("\\|\\|")[1]);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onClick(View v) {
            Intent intent=newIntent(getActivity(),mHuaYuContent.getUrl());
            startActivity(intent);
        }

    }

    public class HuaYuContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private List<HuaYuContent> mHuaYuContentsList;
        private List<String> mImageUrlList;
        private List<String> mTitleList;

        public HuaYuContentAdapter(List<HuaYuContent> HuaYuContentsList,List<String> ImageUrlList,List<String> TitleList) {
            mHuaYuContentsList = HuaYuContentsList;
            mImageUrlList=ImageUrlList;
            mTitleList=TitleList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.huayu_content, parent, false);
            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            HuaYuContent HuaYuContent = mHuaYuContentsList.get(position);
            holder.bindHuaYuContent(HuaYuContent);

        }


        @Override
        public int getItemCount() {
            return mHuaYuContentsList.size();
        }
    }

    private void AcquireUrl() {
        BmobQuery<WebInformation> bmobQuery = new BmobQuery<WebInformation>();
        bmobQuery.setLimit(50);
        bmobQuery.findObjects(new FindListener<WebInformation>() {
            @Override
            public void done(List<WebInformation> WebInformationList, BmobException e) {
                if (e == null) {
                    List<String> urlList=new ArrayList<>();
                    for (WebInformation webInformation : WebInformationList) {
                        if(!urlList.contains(webInformation.getUrl())) {
                            urlList.add(webInformation.getUrl());
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


    public class DownloadImageTask extends AsyncTask<String,Integer,Bitmap>{
        Bitmap bitmap;
        @Override
        public Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == 200) {
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return bitmap;
        }
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
                        mHuaYuContentList.clear();
                        IsPullRefresh=false;
                    }
                    if(!TitleList.contains(Title)&&!ImageList.contains(ImageUrl)) {
                        TitleList.add(Title);
                        Log.d("TitleList",TitleList.toString());
                        ImageList.add(ImageUrl);
                        Log.d("ImageUrlList",ImageList.toString());
                        HuaYuContent huaYuContent = new HuaYuContent(Title,ImageUrl,Url);
                        Log.d("huaYuContent", huaYuContent.toString());
                        mHuaYuContentList.add(huaYuContent);
                        Log.d("List", mHuaYuContentList.toString());
                    }
                    Message message=new Message();
                    message.what=UPDATE_VIEW;
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
