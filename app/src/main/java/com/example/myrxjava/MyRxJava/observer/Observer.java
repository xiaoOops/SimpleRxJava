package com.example.myrxjava.MyRxJava.observer;

/**
 * @version V1.0
 * @Title: Loan
 * @Description: 观察者、订阅者（数据接收者）
 * @date 2019/4/12
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public interface Observer<T> {

    void onSubscribe();//Rxjava 的源码中多了一个disposeable参数

    void onNext(T value);

    void onError(Throwable throwable);

    void onComplete();

}
