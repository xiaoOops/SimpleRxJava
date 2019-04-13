package com.example.myrxjava.MyRxJava.observable;

import com.example.myrxjava.MyRxJava.observer.Observer;

/**
 * @version V1.0
 * @Title: Loan
 * @Description: 被观察者（数据发送者）
 * @date 2019/4/12
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public abstract class Observable<T> implements ObservableSource<T> {

    public static <T> Observable<T> creat(ObservableOnSubscribe<T> source) {
        return new ObservableCreate<>(source);
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> Observer);

}
