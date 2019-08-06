package com.example.myrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myrxjava.MyRxJava.observable.Observable;
import com.example.myrxjava.MyRxJava.observable.ObservableEmitter;
import com.example.myrxjava.MyRxJava.observable.ObservableOnSubscribe;
import com.example.myrxjava.MyRxJava.observer.Observer;
import com.example.myrxjava.MyRxJava.schedulers.Schedulers;
import com.example.myrxjava.MyRxJava.utils.RLog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_base).setOnClickListener(this);
        findViewById(R.id.btn_scheduler).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_base:
                testBase();
                break;
            case R.id.btn_scheduler:
                testScheduler();
                break;
        }
    }

    /**
     * observeOn() 影响的是数据发送之后的线程
     * subscribeOn() 影响的是数据发送之前的线程
     */
    @SuppressWarnings("all")
    private void testScheduler() {
        Observable.creat(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                RLog.printInfo("subscribe 开始发送");
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })
                //通过 1、2、3 可知,多次调用subcribeOn只有顺序写的第一次有效
                //并不是因为第二次不会切换线程,而是代码最先是从subscribe最近的一个操作符开始执行的
                // 3.接到下游通知,再通知自己的上游,可以发数据了,此处线程又进行了切换
                .subscribeOn(Schedulers.IO)
                // 2.subcribeOn是在发送数据前就切换了线程，通知上游开始订阅开始了,可以发数据了，此时已经切换了线程
                .subscribeOn(Schedulers.ANDROID_MAIN_THREAD)
                // 1.离subscribe最近的操作符最先执行,通知上游(即Observable)订阅开始了
                //  observeOn 是在接收到数据之后处理时切换线程,此时通知上游发送数据,还未切线程
                .observeOn(Schedulers.ANDROID_MAIN_THREAD)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe() {
                        RLog.printInfo("MainActivity 开始订阅 = ");
                    }

                    @Override
                    public void onNext(Integer value) {
                        RLog.printInfo("MainActivity 接收到 = " + value);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        /*
         *  [Thread: main]_开始订阅 ObservableObserveOn
         *  [Thread: main]_开始订阅 ObservableSubscribeOn
         *  [Thread: main]_这里切换了线程
         *  [Thread: RxJava IO Thread #1]_开始订阅 ObservableSubscribeOn
         *  [Thread: RxJava IO Thread #1]_这里切换了线程
         *  [Thread: RxJava IO Thread #1]_开始订阅 ObservableCreate
         *  [Thread: RxJava IO Thread #1]_subscribe 开始发送
         *  [Thread: RxJava IO Thread #1]_ObservableCreate 发送  1
         *  [Thread: RxJava IO Thread #1]_ObservableSubscribeOn 接收到 = 1
         *  [Thread: RxJava IO Thread #1]_ObservableSubscribeOn 接收到 = 1
         *  [Thread: RxJava IO Thread #1]_ObservableCreate 发送  2
         *  [Thread: RxJava IO Thread #1]_ObservableSubscribeOn 接收到 = 2
         *  [Thread: RxJava IO Thread #1]_ObservableSubscribeOn 接收到 = 2
         *  [Thread: RxJava IO Thread #1]_ObservableCreate 发送  3
         *  [Thread: RxJava IO Thread #1]_ObservableSubscribeOn 接收到 = 3
         *  [Thread: RxJava IO Thread #1]_ObservableSubscribeOn 接收到 = 3
         *
         *  因为ObservableObserveOn被切换到了主线程了，要通过handler处理，所以此时会有延迟
         *  [Thread: main]_MainActivity 开始订阅
         *  [Thread: main]_ ObservableObserveOn 接收到 = 1
         *  [Thread: main]_MainActivity 接收到 = 1
         *  [Thread: main]_ ObservableObserveOn 接收到 = 2
         *  [Thread: main]_MainActivity 接收到 = 2
         *  [Thread: main]_ ObservableObserveOn 接收到 = 3
         *  [Thread: main]_MainActivity 接收到 = 3
         *
         */


    }

    private void testBase() {
        Observable<Integer> observable = Observable.creat(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        });
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe() {
                RLog.printInfo("observer 开始接收");
            }


            @Override
            public void onNext(Integer value) {
                RLog.printInfo("observer 接收到 = " + value);
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {
                RLog.printInfo("observer 接收完成 ");
            }

        };
        observable.subscribe(observer);
    }
}
