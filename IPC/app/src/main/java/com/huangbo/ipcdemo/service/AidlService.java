package com.huangbo.ipcdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.huangbo.ipcdemo.aidl.Book;
import com.huangbo.ipcdemo.aidl.IBookManager;
import com.huangbo.ipcdemo.aidl.IOnNewBookArrivedListener;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * created by huangbo at 2018/9/21 21:08
 */
public class AidlService extends Service {

    private CopyOnWriteArrayList<Book> mBooks = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();
    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);
    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }

        @Override
        public void registListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if(!mListenerList.contains(listener)) {
                mListenerList.add(listener);
                Log.e("regist", "success");
            } else {
                Log.e("regist", "you has already registed");
            }
        }

        @Override
        public void unregistListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if(mListenerList.contains(listener)) {
                mListenerList.remove(listener);
                Log.e("unregist", "success");
            } else {
                Log.e("unregist", "you has already unregist");
            }
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBooks.add(new Book("1", "android"));
        mBooks.add(new Book("2", "ios"));
        new Thread(new ServiceWork()).start();
    }

    private void addBook(Book book) {
        mBooks.add(book);
        for(IOnNewBookArrivedListener listener : mListenerList) {
            try {
                listener.onNewBookArrived(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


    private class ServiceWork implements Runnable {

        @Override
        public void run() {
            while(!mIsServiceDestoryed.get()) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("book store", "add a book");
                addBook(new Book(String.valueOf(mBooks.size()+1), "book"+String.valueOf(mBooks.size()+1)));
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsServiceDestoryed.set(true);
    }
}
