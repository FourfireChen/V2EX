package com.fourfire.v2ex.main.page;

import com.fourfire.v2ex.IPresenter;
import com.fourfire.v2ex.IView;
import com.fourfire.v2ex.data.bean.V2EXPost;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/17.
 */

public interface PageContract {
    interface IPageView extends IView {
        void showPosts(ArrayList<V2EXPost> v2EXPosts);

        void finishRefresh();

        void finishLoadMore();
    }

    interface IPagePresenter extends IPresenter {
        void inflatePosts(int count, int page);

        void loadMorePosts(int page);
    }
}

