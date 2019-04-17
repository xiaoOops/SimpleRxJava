package com.example.myrxjava.MyRxJava.observable;


import com.example.myrxjava.MyRxJava.observer.Observer;
import com.example.myrxjava.MyRxJava.utils.CheckUtils;
import com.example.myrxjava.MyRxJava.utils.RLog;

/**
 * @version V1.0
 * @Title: Loan
 * @Description: 常规模式
 * @date 2019/4/12
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public final class ObservableCreate<T> extends Observable<T> {

    //source 为create 中创建的ObservableOnSubscribe对象
    final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }


    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        //传入的observer为数据接收者，即观察者
        CreateEmitter<T> createEmitter = new CreateEmitter<>(observer);
        //通知数据接收者开始接收数据
        observer.onSubscribe();
        try {
            //emitter开始执行，其发出的事件会传递到observer
            RLog.printInfo("开始订阅 ObservableCreate");
            source.subscribe(createEmitter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 把Emitter发出的事件发送给数据接收者observer
     */
    static class CreateEmitter<T> implements ObservableEmitter<T> {

        final Observer<? super T> observer;


        CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }


        @Override
        public void onNext(T value) {
            CheckUtils.checkNotNull(value);
            RLog.printInfo("ObservableCreate 接收到 = " + value);
            observer.onNext(value);
        }

        @Override
        public void onError(Throwable e) {
            observer.onError(e);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }

        @Override
        public ObservableEmitter<T> serialize() {
            return null;
        }
    }
}
