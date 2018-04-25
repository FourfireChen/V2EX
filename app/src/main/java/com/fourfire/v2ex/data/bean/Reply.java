package com.fourfire.v2ex.data.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by 45089 on 2018/4/17.
 */

public class Reply extends DataSupport
{
    private String author, content, time;
    private byte[] anthorAvatar;

    public Reply()
    {
    }

    public Reply(String author, String content, String time, byte[] anthorAvatar)
    {
        this.author = author;
        this.content = content;
        this.time = time;
        this.anthorAvatar = anthorAvatar;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public byte[] getAnthorAvatar()
    {
        return anthorAvatar;
    }

    public void setAnthorAvatar(byte[] anthorAvatar)
    {
        this.anthorAvatar = anthorAvatar;
    }
}
