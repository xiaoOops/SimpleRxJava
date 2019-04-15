package com.example.myrxjava.MyRxJava.schedulers;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * @version V1.0
 * @Title: Loan
 * @Description: 主线程调度器
 * @date 2019/4/15
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public final class AndroidScheduler extends Scheduler {

    private final Handler handler = new Handler(Looper.getMainLooper());

    public static AndroidScheduler getInstance() {
        return AndroidSchedulersHolder.INSTANCE;
    }

    @Override
    public void scheduleDirect(Runnable runnable) {
        //此处使用的主线程的Handler，已完成了线程的切换
        Message message = handler.obtainMessage();
        message.obj = this;
        handler.sendMessage(message);
    }

    private static final class AndroidSchedulersHolder {
        private static AndroidScheduler INSTANCE = new AndroidScheduler();
    }


}
