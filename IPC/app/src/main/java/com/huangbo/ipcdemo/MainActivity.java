package com.huangbo.ipcdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.huangbo.ipcdemo.connection.AidlServiceConnection;
import com.huangbo.ipcdemo.connection.MessengerServiceConnection;
import com.huangbo.ipcdemo.service.AidlService;
import com.huangbo.ipcdemo.service.MessagerService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private MessengerServiceConnection mMessengerServiceConnection;
    private AidlServiceConnection mAidlServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessengerServiceConnection = new MessengerServiceConnection();
        mAidlServiceConnection = new AidlServiceConnection();
    }

    public void bindService(View v) {
        Intent intent = new Intent(this, MessagerService.class);
        bindService(intent, mMessengerServiceConnection, Context.BIND_AUTO_CREATE);
    }
    public void bindAidlService(View v) {
        Intent intent = new Intent(this, AidlService.class);
        bindService(intent, mAidlServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
//        unbindService(mMessengerServiceConnection);
        unbindService(mAidlServiceConnection);
        mAidlServiceConnection.unregist();
        super.onDestroy();
    }
}
