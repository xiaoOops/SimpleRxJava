package com.example.myrxjava.MyRxJava.schedulers;

/**
 * Created by joybar on 2018/6/13.
 */

public final class Schedulers {
	public static final Scheduler IO = IoScheduler.getInstance();
	public static final Scheduler ANDROID_MAIN_THREAD = AndroidScheduler.getInstance();

}
