package com.huangbo.ipcdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * created by huangbo at 2018/9/18 21:09
 */
public class MessagerService extends Service {
    private static final String TAG = "MessagerService";

    private final Messenger mMessenger = new Messenger(new MessengerHandler());
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }



    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e(TAG, "receive msg from Client: "+ msg.getData().getString("msg"));
                    break;
                    default:
                        super.handleMessage(msg);
            }

        }
    }
}
