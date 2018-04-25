package com.fourfire.v2ex.data;

import android.util.Log;

import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.data.localdata.ILocalData;
import com.fourfire.v2ex.data.localdata.LocalDataRepository;
import com.fourfire.v2ex.data.remotedata.IRemoteData;
import com.fourfire.v2ex.data.remotedata.RemoteRepository;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import com.fourfire.v2ex.IPresenter.*;
/**
 * Created by 45089 on 2018/4/17.
 */

public class DataRepository implements IDataRepository
{
    private ILocalData localData;
    private IRemoteData remoteData;

    public DataRepository()
    {
        this.remoteData = new RemoteRepository();
        this.localData = new LocalDataRepository();
    }

    @Override
    public void getPostList(final int page, final PresenterCallback<ArrayList<V2EXPost>> presenterCallback)
    {
        localData.getPostsFromLocal(new ModelCallback<ArrayList<V2EXPost>>() {
            @Override
            public void onSuccess(final ArrayList<V2EXPost> result)
            {
                presenterCallback.onLocalData(result, null);
                remoteData.getPostsFromNet(page, new ModelCallback<ArrayList<V2EXPost>>() {
                    @Override
                    public void onSuccess(ArrayList<V2EXPost> result)
                    {
                        presenterCallback.onNetData(result, null);
                        DataSupport.deleteAll(V2EXPost.class, null);
                        DataSupport.saveAll(result);
                    }
                    @Override
                    public void onFail(Exception e)
                    {
                        presenterCallback.onNetData(null, new Exception("网络读取数据失败，本地读取数据成功"));
                    }
                });
            }
            @Override
            public void onFail(Exception e)
            {
                Log.e("本地取post", e.getMessage());
                presenterCallback.onLocalData(null, e);
                remoteData.getPostsFromNet(page, new ModelCallback<ArrayList<V2EXPost>>() {
                    @Override
                    public void onSuccess(ArrayList<V2EXPost> result)
                    {
                        presenterCallback.onNetData(result, null);
                        DataSupport.saveAll(result);
                    }
                    @Override
                    public void onFail(Exception e)
                    {
                        presenterCallback.onNetData(null, e);
                    }
                });
            }
        });
    }

    @Override
    public void getPostDetail(int position, final PresenterCallback<V2EXPost> presenterCallback)
    {
        localData.getPostFromLocal(position, new ModelCallback<V2EXPost>() {
            @Override
            public void onSuccess(V2EXPost result)
            {
                presenterCallback.onLocalData(result, null);
                remoteData.getPostDetailFromNet(result, new ModelCallback<V2EXPost>() {
                    @Override
                    public void onSuccess(V2EXPost result)
                    {
                        presenterCallback.onNetData(result, null);
                        //此处是更新，result和上面那个其实是同一个
                        result.save();
                    }
                    @Override
                    public void onFail(Exception e)
                    {
                        Log.e("获取详细信息失败", e.getMessage());
                        presenterCallback.onNetData(null, new Exception("获取网络详细信息失败"));
                    }
                });
            }
            @Override
            public void onFail(Exception e)
            {
                Log.e("获取本地posts失败", e.getMessage());
                presenterCallback.onLocalData(null, new Exception("本地没有找到对应Post"));
            }
        });
    }

}
