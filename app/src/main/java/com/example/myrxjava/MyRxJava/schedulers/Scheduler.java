package com.example.myrxjava.MyRxJava.schedulers;

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
public abstract class Scheduler {

    public abstract void scheduleDirect(Runnable runnable);

    public abstract static class Worker extends NamedRunnable {

    }


    public static abstract class NamedRunnable implements Runnable {

        @Override
        public final void run() {
            excute();
        }

        protected abstract void excute();
    }

}
