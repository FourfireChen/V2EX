package com.fourfire.v2ex.data.remotedata;


import com.fourfire.v2ex.data.IDataRepository.ModelCallback;
import com.fourfire.v2ex.data.bean.V2EXPost;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/17.
 */

public interface IRemoteData {
    void getPostsFromNet(int page, ModelCallback<ArrayList<V2EXPost>> modelCallback);

    void getPostDetailFromNet(V2EXPost v2EXPost, ModelCallback<V2EXPost> postModelCallback);
}
