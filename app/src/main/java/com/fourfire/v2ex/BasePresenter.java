package com.fourfire.v2ex;

import com.fourfire.v2ex.data.DataRepository;
import com.fourfire.v2ex.data.IDataRepository;

import org.litepal.crud.DataSupport;

import java.lang.ref.SoftReference;

/**
 * Created by 45089 on 2018/4/17.
 */

public abstract class BasePresenter<T extends IView> implements IPresenter
{
    private SoftReference<T> viewReference;
    private IDataRepository dataRepository;

    public BasePresenter(T view)
    {
        dataRepository = new DataRepository();
        viewReference = new SoftReference<T>(view);
    }

    public T getView()
    {
        return viewReference.get();
    }

    public IDataRepository getDataRepository()
    {
        return dataRepository;
    }

    @Override
    public void destory()
    {
        viewReference.clear();
        viewReference = null;
        dataRepository = null;
    }
}
