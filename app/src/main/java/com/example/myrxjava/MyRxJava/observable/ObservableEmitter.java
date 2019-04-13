package com.example.myrxjava.MyRxJava.observable;

/**
 * @version V1.0
 * @Title: Loan
 * @Description:
 * @date 2019/4/12
 * @author:xiaox
 * @email:303378520@qq.com
 * @replace author:
 * @replace date:
 */
public interface ObservableEmitter<T> extends Emitter<T> {

    ObservableEmitter<T> serialize();

}
