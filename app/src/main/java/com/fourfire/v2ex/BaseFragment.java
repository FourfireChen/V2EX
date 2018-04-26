package com.fourfire.v2ex;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by 45089 on 2018/4/17.
 */


public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IView {
    private T presenter;

    public T getPresenter() {
        return presenter;
    }

    public void setPresenter(T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destory();
        presenter = null;
    }

    @Override
    public void toast(String content) {
        Toast.makeText(getActivity(), content, Toast.LENGTH_LONG).show();
    }
}
