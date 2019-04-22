package com.example.xiaojun.huayu.HuaYu.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class ThumbnailDownloader<T> extends HandlerThread {
    private static  final String TAG="ThumbnailDownloader";
    private Boolean mHasQuit=false;
    private static final int MESSAGE_DOWNLOAD=0;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private ThumbnailDownloadListener<T> thumbnailDownloadListener;

    private ConcurrentHashMap<T, String> mRequestMap=new ConcurrentHashMap<>();
    public interface ThumbnailDownloadListener<T>{
        void onThumbnailDownloaded(T target,Bitmap thumbnail);
    }
    public void setThumbnailDownloadListener(ThumbnailDownloadListener<T> listener){
        thumbnailDownloadListener=listener;
    }

    public ThumbnailDownloader(Handler responseHandler) {
        super(TAG);
        mResponseHandler=responseHandler;
    }
    @Override
    protected void onLooperPrepared(){
        mRequestHandler=new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==MESSAGE_DOWNLOAD){
                    T target=(T)msg.obj;
                    Log.i(TAG,"got a request for url"+mRequestMap.get(target));
                    handlerRequest(target);
                }
            }
        };
    }
    @Override
    public boolean quit(){
        mHasQuit=true;
        return super.quit();
    }
    private void handlerRequest(final T target){
        try {
            final String url=mRequestMap.get(target);
            if(url==null){
                return;
            }
            byte[] bitmapBytes=new BombFetchr().getUrlBytes(url);
            final Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapBytes,0,bitmapBytes.length);
            Log.i(TAG,"bitmap created");
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(mRequestMap.get(target)!=url||mHasQuit){
                        return;
                    }
                    mRequestMap.remove(target);
                    thumbnailDownloadListener.onThumbnailDownloaded(target,bitmap);
                }

            });
        }catch (IOException e){
            Log.e(TAG,"error downloading image",e);
        }
    }
    public void clearQueue(){
        mResponseHandler.removeMessages(MESSAGE_DOWNLOAD);
    }
    public void queneThumbnail(T target,String url){
        if(url==null){
            mRequestMap.remove(target);
        }else{
            mRequestMap.put(target,url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD,target).sendToTarget();
        }
        Log.i(TAG,"url:"+url);
    }

}
