// IOnNewBookArrivedListener.aidl
package com.huangbo.ipcdemo.aidl;
import com.huangbo.ipcdemo.aidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
     void onNewBookArrived(in Book book);
}
