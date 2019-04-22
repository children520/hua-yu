package com.example.xiaojun.huayu.HuaYu.Tools;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BombFetchr {
    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url=new URL(urlSpec);
        HttpURLConnection connection=(HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
            InputStream in =connection.getInputStream();
            if(connection.getResponseCode()!=HttpURLConnection.HTTP_OK){
                throw  new IOException(connection.getResponseMessage()+": with"+urlSpec);
            }
            int bytesRead=0;
            byte[] buffer=new byte[1024];
            while ((bytesRead=in.read(buffer))>0){
                outputStream.write(buffer,0,bytesRead);
            }
            outputStream.close();
            return outputStream.toByteArray();
        }finally {
            connection.disconnect();
        }

    }
    public String getUrlString(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }


}
