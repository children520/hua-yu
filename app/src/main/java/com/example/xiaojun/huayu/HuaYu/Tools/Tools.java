package com.example.xiaojun.huayu.HuaYu.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

public class Tools {
    public static void WipeSearchViewUnderLine(SearchView searchView){
        if (searchView != null) {
            try {        //--拿到字节码
                Class<?> argClass = searchView.getClass();
                //--指定某个私有属性,mSearchPlate是搜索框父布局的名字
                Field ownField = argClass.getDeclaredField("mSearchPlate");
                //--暴力反射,只有暴力反射才能拿到私有属性
                ownField.setAccessible(true);
                View mView = (View) ownField.get(searchView);
                //--设置背景
                mView.setBackgroundColor(Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void LoadImage(final ImageView imageView, String imageUrl){
        new AsyncTask<String, Void, Bitmap>() {
            //该方法运行在后台线程中，因此不能在该线程中更新UI，UI线程为主线程
            @Override
            protected Bitmap doInBackground(String... params) {
                Bitmap bitmap = null;
                try {
                    String url = params[0];
                    URL HttpURL = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) HttpURL.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            //在doInBackground 执行完成后，onPostExecute 方法将被UI 线程调用，
            // 后台的计算结果将通过该方法传递到UI线程，并且在界面上展示给用户.
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if(bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }.execute(imageUrl);
    }
    public static Bitmap scaleMatrix(Bitmap bitmap, int width, int height){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scaleW = width/w;
        float scaleH = height/h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    public static int getSreenWidth(Context context){
        Point point=new Point();
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display=manager.getDefaultDisplay();
        display.getSize(point);
        return point.x;
    }
    public static int getSreenHeight(Context context){
        Point point=new Point();
        WindowManager manager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        Display display=manager.getDefaultDisplay();
        display.getSize(point);
        return point.y;
    }




}
