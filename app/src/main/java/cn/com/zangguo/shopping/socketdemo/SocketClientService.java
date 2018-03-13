package cn.com.zangguo.shopping.socketdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by admin on 2018/3/13.
 */

public class SocketClientService extends Service {
    public static final String TAG = "SocketClientService";
    public static final String MY_ACTION = "cn.com.zangguo.shopping";


    private LocalBroadcastManager manager;
    private Socket socket;
    private boolean isConnected = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (manager == null) {
            manager = LocalBroadcastManager.getInstance(this);
        }
        Log.e(TAG, " SocketClientService onCreate ");
        new Thread(new ReceiveRunnable()).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isConnected = false;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ReceiveRunnable implements Runnable {

        @Override
        public void run() {
            while (isConnected) {
                try {
                    socket = new Socket("172.16.163.24", 4869);
                    isConnected = socket.isConnected();
                    if(isConnected) {
                        Log.e(TAG, "ReceiveRunnable 连接成功！");
                    }
                    InputStream is = socket.getInputStream();
                    int len = -1;
                    byte[] bs = new byte[1024 *4];
                    String s = null;
                    while ((len = is.read(bs)) != -1) {
                        s = new String(bs, 0, len);
                        Log.e(TAG, "ReceiveRunnable s: " + s);
                        Intent intent = new Intent();
                        intent.setAction(MY_ACTION);
                        intent.putExtra("data", s);
                        sendBroadcast(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "ReceiveRunnable 连接失败！");
                }
            }
        }
    }
}
