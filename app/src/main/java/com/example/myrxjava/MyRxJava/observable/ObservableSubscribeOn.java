package com.example.myrxjava.MyRxJava.observable;

import com.example.myrxjava.MyRxJava.observer.Observer;
import com.example.myrxjava.MyRxJava.schedulers.Scheduler;
import com.example.myrxjava.MyRxJava.utils.RLog;

/**
 * @version V1.0
 * @Title: Loan
 * @Description:
 * @date 2019/4/16
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public class ObservableSubscribeOn<T> extends Observable<T> {

    private final ObservableSource<T> source;
    private final Scheduler scheduler;

    public ObservableSubscribeOn(Observable<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(final Observer<? super T> observer) {
        final ObservableSubscribe<T> subscribe = new ObservableSubscribe<>(observer);
        scheduler.scheduleDirect(new Scheduler.Worker() {
            @Override
            protected void excute() {
                //在此处切换线程
                RLog.printInfo("开始订阅 ObservableSubscribeOn");
                RLog.printInfo("这里切换了线程");
                source.subscribe(subscribe);
            }
        });
    }

    static final class ObservableSubscribe<T> implements Observer<T> {

        final Observer<? super T> observer;

        public ObservableSubscribe(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public void onSubscribe() {
            RLog.printInfo(" ObservableSubscribeOn onSubscribe ");
            observer.onSubscribe();
        }

        @Override
        public void onNext(T value) {
            RLog.printInfo("ObservableSubscribeOn 接收到 = " + value);
            observer.onNext(value);
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
