package com.fourfire.v2ex.main.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.fourfire.v2ex.IPresenter.*;
import com.fourfire.v2ex.BasePresenter;
import com.fourfire.v2ex.data.IDataRepository;
import com.fourfire.v2ex.data.bean.Reply;
import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.main.detail.DetailContract.*;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/18.
 */

public class DetailPresenter extends BasePresenter<DetailActivity> implements DetailContract.IPostDetailPresenter
{
    private IPostDetailView detailView;
    private IDataRepository dataRepository;
    private V2EXPost posts;
    private int replyCount = 10;

    public DetailPresenter(DetailActivity view)
    {
        super(view);
        detailView = getView();
        dataRepository = getDataRepository();
    }

    @Override
    public void inflatePost(final int count, final int position)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                dataRepository.getPostDetail(position, new PresenterCallback<V2EXPost>()
                {
                    @Override
                    public void onLocalData(final V2EXPost localData, Exception e)
                    {
                        if(e == null)
                        {
                            ((Activity)detailView).runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    detailView.showPost(limitPostReply(count, localData));
                                }
                            });
                        }
                    }
                    @Override
                    public void onNetData(final V2EXPost netData, Exception e)
                    {
                        if(e == null)
                        {
                            posts = netData;
                            ((Activity)detailView).runOnUiThread(new Runnable() {
                                @Override
                                public void run()
                                {
                                    detailView.showPost(limitPostReply(count, posts));
                                    detailView.finishRefresh();
                                }
                            });
                        }
                    }
                });
            }
        }).start();

    }

    @Override
    public void loadMorePosts(int position)
    {
        replyCount += 5;
        ((Activity)detailView).runOnUiThread(new Runnable() {
            @Override
            public void run()
            {
                detailView.showPost(limitPostReply(replyCount, posts));
                detailView.finishLoadMore();
            }
        });
    }


    private V2EXPost limitPostReply(int replyCount, V2EXPost posts)
    {
        V2EXPost tem = new V2EXPost();
        String content = posts.getContent();
        String time = posts.getTime();
        String lastReply = posts.getLastReply();
        String title = posts.getTitle();
        String author = posts.getAuthor();
        byte[] bytes = posts.getAnthorAvatar();
        String url = posts.getUrl();
        ArrayList<Reply> replies = new ArrayList<>();
        if(posts.getReplies() != null)
        {
            if(posts.getReplies().size() > replyCount)
            {
                for(int i = 0; i < replyCount; i++)
                {
                    replies.add(posts.getReplies().get(i));
                }
            }else
            {
                replies.addAll(posts.getReplies());
            }
        }
        if(content != null)
            tem.setContent(content);
        if(time != null)
            tem.setTime(time);
        if(title != null)
            tem.setTitle(title);
        if(author != null)
            tem.setAuthor(author);
        if(bytes != null)
            tem.setAnthorAvatar(bytes);
        if(lastReply != null)
            tem.setLastReply(lastReply);
        if(url != null)
            tem.setUrl(url);
        if(replies != null)
            tem.setReplies(replies);
        return tem;
    }
}