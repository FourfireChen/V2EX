package com.fourfire.v2ex.data.localdata;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import com.fourfire.v2ex.data.IDataRepository.*;
import com.fourfire.v2ex.data.bean.V2EXPost;

import org.litepal.crud.DataSupport;

/**
 * Created by 45089 on 2018/4/17.
 */

public class LocalDataRepository implements ILocalData
{
    AsyncTask getPost;


    @Override
    public void getPostsFromLocal(ModelCallback<ArrayList<V2EXPost>> modelCallback)
    {
        if(DataSupport.count("v2expost") > 0)
        {
            List<V2EXPost> v2EXPosts =  DataSupport.findAll(V2EXPost.class);
            if(v2EXPosts != null && v2EXPosts.size() > 0)
            {
                modelCallback.onSuccess((ArrayList<V2EXPost>) v2EXPosts);
            }else
            {
                modelCallback.onFail(new Exception("本地没有数据"));
            }
        }else
        {
            modelCallback.onFail(new Exception("本地没有数据"));
        }
    }

    @Override
    public void getPostFromLocal(final int position, final ModelCallback<V2EXPost> postModelCallback)
    {
        getPostsFromLocal(new ModelCallback<ArrayList<V2EXPost>>() {
            @Override
            public void onSuccess(ArrayList<V2EXPost> result)
            {
                if(position < result.size())
                    postModelCallback.onSuccess(result.get(position));
                else
                    postModelCallback.onFail(new Exception("没有查到该Post，有毒"));
            }
            @Override
            public void onFail(Exception e)
            {
                postModelCallback.onFail(e);
            }
        });
    }
}
