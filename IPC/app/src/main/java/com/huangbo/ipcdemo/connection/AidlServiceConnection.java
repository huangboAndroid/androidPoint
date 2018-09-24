package com.huangbo.ipcdemo.connection;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.huangbo.ipcdemo.aidl.Book;
import com.huangbo.ipcdemo.aidl.IBookManager;
import com.huangbo.ipcdemo.aidl.IOnNewBookArrivedListener;

import java.util.List;


/**
 * created by huangbo at 2018/9/22 10:26
 */
public class AidlServiceConnection extends IOnNewBookArrivedListener.Stub implements ServiceConnection {
    private final static String TAG = "AidlServiceConnection";
    private IBookManager mIBookManager;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mIBookManager = IBookManager.Stub.asInterface(iBinder);
        try {
            List<Book> bookList = mIBookManager.getBookList();
            Log.e(TAG, "list : "+bookList.toString());
            mIBookManager.addBook(new Book("3", "c++"));
            mIBookManager.registListener(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    @Override
    public void onNewBookArrived(Book book) throws RemoteException {
        Log.e("received new book", book.toString() + " : "+Thread.currentThread().getName());
    }

    public void unregist() {
        try {
            mIBookManager.unregistListener(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
