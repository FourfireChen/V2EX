package com.fourfire.v2ex.data.localdata;


import com.fourfire.v2ex.data.IDataRepository.ModelCallback;
import com.fourfire.v2ex.data.bean.V2EXPost;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/17.
 */

public interface ILocalData {
    void getPostsFromLocal(ModelCallback<ArrayList<V2EXPost>> modelCallback);

    void getPostFromLocal(int position, ModelCallback<V2EXPost> postModelCallback);
}
