package com.fourfire.v2ex.data.bean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by 45089 on 2018/4/18.
 */

public class V2EXPost extends Reply
{
    private String title, lastReply, url;
    private List<Reply> replies = new ArrayList<>();
    //private List<byte[]> pictures = new ArrayList<>();


    public V2EXPost()
    {
    }

    public V2EXPost(String author, String content, String time, byte[] anthorAvatar, String title, String lastReply, String url, List<Reply> replies)
    {
        super(author, content, time, anthorAvatar);
        this.title = title;
        this.lastReply = lastReply;
        this.url = url;
        this.replies = replies;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLastReply()
    {
        return lastReply;
    }

    public void setLastReply(String lastReply)
    {
        this.lastReply = lastReply;
    }

    public String getUrl()
    {
        return "https://www.v2ex.com" + url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public List<Reply> getReplies()
    {
        return replies;
    }

    public void setReplies(List<Reply> replies)
    {
        this.replies = replies;
    }
    /**
    public List<byte[]> getPictures()
    {
        return pictures;
    }

    public void setPictures(List<byte[]> pictures)
    {
        this.pictures = pictures;
    }*/
}
