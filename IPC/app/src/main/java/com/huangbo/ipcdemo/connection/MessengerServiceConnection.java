package com.huangbo.ipcdemo.connection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;


/**
 * created by huangbo at 2018/9/22 10:26
 */
public class MessengerServiceConnection implements ServiceConnection {
    private Messenger mMessenger;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mMessenger = new Messenger(iBinder);
        Message msg = Message.obtain(null, 0);
        Bundle bundle = new Bundle();
        bundle.putString("msg", "hello, I am Client");
        msg.setData(bundle);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}
