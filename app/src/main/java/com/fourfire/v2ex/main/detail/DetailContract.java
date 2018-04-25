package com.fourfire.v2ex.main.detail;

import android.widget.TextView;

import com.fourfire.v2ex.IPresenter;
import com.fourfire.v2ex.IView;
import com.fourfire.v2ex.data.bean.Reply;
import com.fourfire.v2ex.data.bean.V2EXPost;

/**
 * Created by 45089 on 2018/4/17.
 */

public interface DetailContract
{
    interface IPostDetailPresenter extends IPresenter
    {
        void inflatePost(int replyCount, int position);
        void loadMorePosts(int position);
    }

    interface IPostDetailView extends IView
    {
        void showPost(V2EXPost v2EXPost);
        void finishRefresh();
        void finishLoadMore();
    }
}
