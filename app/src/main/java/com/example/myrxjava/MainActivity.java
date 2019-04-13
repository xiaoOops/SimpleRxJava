package com.example.myrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.myrxjava.MyRxJava.observable.Observable;
import com.example.myrxjava.MyRxJava.observable.ObservableEmitter;
import com.example.myrxjava.MyRxJava.observable.ObservableOnSubscribe;
import com.example.myrxjava.MyRxJava.observer.Observer;
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_base:
                testBase();
                break;
        }
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
