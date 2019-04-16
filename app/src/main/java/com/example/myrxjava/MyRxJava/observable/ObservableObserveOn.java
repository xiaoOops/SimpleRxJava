package com.example.myrxjava.MyRxJava.observable;

import com.example.myrxjava.MyRxJava.observer.Observer;
import com.example.myrxjava.MyRxJava.schedulers.Scheduler;
import com.example.myrxjava.MyRxJava.utils.RLog;

/**
 * @version V1.0
 * @Title: Loan
 * @Description:
 * @date 2019/4/15
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public class ObservableObserveOn<T> extends Observable<T> {

    private final Scheduler scheduler;
    private final ObservableSource<T> source;

    public ObservableObserveOn(ObservableSource<T> source, Scheduler scheduler) {
        this.scheduler = scheduler;
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        ObserveOnObserver<T> observableObserver = new ObserveOnObserver<T>(observer, scheduler);
        RLog.printInfo("调用subscribeActual observer = " + observer.getClass().getSimpleName());
        source.subscribe(observableObserver);
    }

    static final class ObserveOnObserver<T> implements Observer<T> {

        final Observer<? super T> observer;
        final Scheduler scheduler;

        ObserveOnObserver(Observer<? super T> observer, Scheduler scheduler) {
            this.observer = observer;
            this.scheduler = scheduler;
        }

        @Override
        public void onSubscribe() {
            scheduler.scheduleDirect(new Scheduler.Worker() {
                @Override
                protected void excute() {
                    //我在此处切换线程
                    observer.onSubscribe();
                }
            });
        }

        @Override
        public void onNext(final T value) {
            scheduler.scheduleDirect(new Scheduler.Worker() {
                @Override
                protected void excute() {
                    observer.onNext(value);
                }
            });
        }

        @Override
        public void onError(final Throwable throwable) {
            scheduler.scheduleDirect(new Scheduler.Worker() {
                @Override
                protected void excute() {
                    observer.onError(throwable);
                }
            });
        }

        @Override
        public void onComplete() {
            scheduler.scheduleDirect(new Scheduler.Worker() {
                @Override
                protected void excute() {
                    observer.onComplete();
                }
            });
        }
    }
}
