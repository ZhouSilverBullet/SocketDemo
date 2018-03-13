package cn.com.zangguo.shopping.socketdemo;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private MyServiceConnection conn;
    private Intent serviceIntent;
    private TextView textView;
    private ListReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        registeredReceive();
        handleService();
    }

    private void initView() {
        textView = findViewById(R.id.fg_text);
    }

    private void registeredReceive() {
        IntentFilter intentFilter = new IntentFilter(SocketClientService.MY_ACTION);
        receiver = new ListReceiver();
        registerReceiver(receiver, intentFilter);
    }

    private void handleService() {
        conn = new MyServiceConnection();
        bindService(new Intent(this, SocketClientService.class), conn, BIND_AUTO_CREATE);
        serviceIntent = new Intent(this, SocketClientService.class);
        startService(serviceIntent);
    }

    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    private class ListReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                Log.e(TAG, "intent == null");
                return;
            }

            String data = intent.getStringExtra("data");
            Log.e(TAG, "ReceiveRunnable s: " + data);
            if (!TextUtils.isEmpty(data)) {
                textView.setText(data);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (conn != null) {
            unbindService(conn);
            stopService(serviceIntent);
            unregisterReceiver(receiver);
        }
    }
}
