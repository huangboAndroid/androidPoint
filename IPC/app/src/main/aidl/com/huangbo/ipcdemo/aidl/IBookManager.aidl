// IBookManager.aidl
package com.huangbo.ipcdemo.aidl;
import com.huangbo.ipcdemo.aidl.Book;
import com.huangbo.ipcdemo.aidl.IOnNewBookArrivedListener;
// Declare any non-default types here with import statements

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registListener(IOnNewBookArrivedListener listener);
    void unregistListener(IOnNewBookArrivedListener listener);
}
