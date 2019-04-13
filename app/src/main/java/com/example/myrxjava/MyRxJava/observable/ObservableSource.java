package com.example.myrxjava.MyRxJava.observable;

import com.example.myrxjava.MyRxJava.observer.Observer;

/**
 * @version V1.0
 * @Title: Loan
 * @Description:
 * @date 2019/4/12
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public interface ObservableSource<T> {

    void subscribe(Observer<? super T> observer);//订阅（数据）

}
