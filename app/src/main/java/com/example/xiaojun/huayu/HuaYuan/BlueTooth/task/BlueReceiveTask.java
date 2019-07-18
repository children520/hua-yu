package com.example.xiaojun.huayu.HuaYuan.BlueTooth.task;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

public class BlueReceiveTask extends Thread {
    private static final String TAG = "BlueReceiveTask";
    private BluetoothSocket mSocket;
    private Handler mHandler;

    public BlueReceiveTask(BluetoothSocket socket, Handler handler) {
        mSocket = socket;
        mHandler = handler;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        int bytes;
        while (true) {
            try {
                bytes = mSocket.getInputStream().read(buffer);

                mHandler.obtainMessage(0, bytes, -1, buffer).sendToTarget();

            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
            try {
                //线程睡眠20ms以避免过于频繁工作  50ms->20ms 2017.12.2
                //导致UI处理发回的数据不及时而阻塞
                Thread.sleep(20);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
