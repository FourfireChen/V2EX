package com.fourfire.v2ex.data;

import com.fourfire.v2ex.IPresenter;
import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.IPresenter.*;
import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/17.
 */

public interface IDataRepository
{

    void getPostList(int page, PresenterCallback<ArrayList<V2EXPost>> presenterCallback);
    void getPostDetail(int position, PresenterCallback<V2EXPost> v2EXPostPresenterCallback);

    interface ModelCallback<T>
    {
        void onSuccess(T result);
        void onFail(Exception e);
    }
}
