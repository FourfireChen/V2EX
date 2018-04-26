package com.fourfire.v2ex;

/**
 * Created by 45089 on 2018/4/17.
 */

public interface IPresenter {
    void destory();

    interface PresenterCallback<T> {
        void onLocalData(T localData, Exception e);

        void onNetData(T netData, Exception e);
    }
}
