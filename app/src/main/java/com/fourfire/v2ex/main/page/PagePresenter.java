package com.fourfire.v2ex.main.page;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.fourfire.v2ex.BasePresenter;
import com.fourfire.v2ex.data.IDataRepository;
import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.main.page.PageContract.*;

import java.util.ArrayList;

import static com.fourfire.v2ex.util.ID.ISFINISHLOADMORE;
import static com.fourfire.v2ex.util.ID.ISFINISHREFRESH;
import static com.fourfire.v2ex.util.ID.POST;

/**
 * Created by 45089 on 2018/4/17.
 */

public class PagePresenter extends BasePresenter<PageFragment> implements IPagePresenter
{
    private IDataRepository dataRepository ;
    private ArrayList<V2EXPost> v2EXPosts;
    private int currentCount = 10;
    private IPageView pageView;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg)
        {
            ArrayList<V2EXPost> v2EXPosts = null;
            if((v2EXPosts = (ArrayList<V2EXPost>) msg.getData().getSerializable(POST)) != null)
            {
                pageView.showPosts(v2EXPosts);
                if(msg.getData().getBoolean(ISFINISHREFRESH))
                    pageView.finishRefresh();
                if(msg.getData().getBoolean(ISFINISHLOADMORE))
                    pageView.finishLoadMore();
                return true;
            }
            return false;
        }
    });


    public PagePresenter(PageFragment view)
    {
        super(view);
        dataRepository = getDataRepository();
        pageView = getView();
        v2EXPosts = new ArrayList<>();
    }


    @Override
    public void inflatePosts(final int count, final int page)
    {
        new Thread(new Runnable() {
            @Override
            public void run()
            {
                dataRepository.getPostList(page, new PresenterCallback<ArrayList<V2EXPost>>() {
                    @Override
                    public void onLocalData(ArrayList<V2EXPost> localData, Exception e)
                    {
                        if(e == null)
                        {
                            notifyPostsShow(false, localData, count);
                        }
                    }
                    @Override
                    public void onNetData(ArrayList<V2EXPost> netData, Exception e)
                    {
                        if(e == null)
                        {
                            notifyPostsShow(true, netData, count);
                        }
                    }
                });
            }
        }).start();
    }

    @Override
    public void loadMorePosts(int page)
    {
        currentCount+=5;
        Message message = new Message();
        Bundle bundle = new Bundle();
        ArrayList<V2EXPost> tempList = new ArrayList<>();
        tempList.addAll(v2EXPosts.subList(0, currentCount));
        bundle.putBoolean(ISFINISHLOADMORE, true);
        bundle.putSerializable(POST, tempList);
        message.setData(bundle);
        handler.sendMessage(message);
    }

    private void notifyPostsShow(boolean isFinsihRefresh, ArrayList<V2EXPost> result, int postCount)
    {
        v2EXPosts.clear();
        v2EXPosts.addAll(result);
        currentCount = postCount;
        ArrayList<V2EXPost> tempList = new ArrayList<>();
        tempList.addAll(v2EXPosts.subList(0, postCount));
        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ISFINISHREFRESH, isFinsihRefresh);
        bundle.putSerializable(POST, tempList);
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
