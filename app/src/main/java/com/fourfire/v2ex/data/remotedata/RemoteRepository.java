package com.fourfire.v2ex.data.remotedata;

import com.fourfire.v2ex.data.bean.Reply;
import com.fourfire.v2ex.data.bean.V2EXPost;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.fourfire.v2ex.data.IDataRepository.*;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by 45089 on 2018/4/17.
 */

public class RemoteRepository implements IRemoteData
{

    @Override
    public void getPostsFromNet(int page, ModelCallback<ArrayList<V2EXPost>> modelCallback)
    {
        ArrayList<V2EXPost> v2EXPosts = new ArrayList<>();
        Document mainDoc = null;
        String tab = null;
        switch (page)
        {
            case 0:
                tab = "/?tab=all";
                break;
            case 1:
                tab = "/?tab=hot";
                break;
            case 2:
                //技术页
                tab = "/?tab=tech";
                break;
            case 3:
                //创意页
                tab = "/?tab=creative";
                break;
            case 4:
                //好玩
                tab = "/?tab=play";
                break;
            case 5:
                //apple
                tab = "/?tab=apple";
                break;
            case 6:
                //酷工作
                tab = "/?tab=jobs";
                break;
            case 7:
                //交易
                tab = "/?tab=deals";
                break;
            case 8:
                //城市
                tab = "/?tab=city";
                break;
            case 9:
                //问与答
                tab = "/?tab=qna";
                break;
            case 10:
                //R2
                tab = "/?tab=r2";
                break;
            default:
                tab = "/?tab=all";
                break;
        }
        try
        {
            Connection conn = Jsoup.connect("https://www.v2ex.com" + tab);
            conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.header("Accept-Encoding", "gzip, deflate, sdch");
            conn.header("Accept-Language", "zh-CN,zh;q=0.8");
            conn.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
            mainDoc = conn.get();
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        if(mainDoc == null)
            return;
        Element mainBody = mainDoc.body();
        Elements postEle = mainBody.getElementsByClass("cell item");
        for(Element element: postEle)
        {
            Elements baseInfo = element.getElementsByTag("a");
            String title = baseInfo.get(1).text();
            String url = baseInfo.get(1).attr("href");
            String author = baseInfo.get(3).text();
            byte[] avatar = getImageBytes(element.getElementsByTag("img").attr("src"));
            String[] timeAndLastReply = element.getElementsByTag("span").get(1).text().split("•");
            if(timeAndLastReply.length > 2)
            {
                String time = timeAndLastReply[2];
                String lastReply = null;
                if(timeAndLastReply.length > 3)
                    lastReply = timeAndLastReply[3];
                V2EXPost v2EXPost = new V2EXPost(author, null, time, avatar, title, lastReply, url, null);
                v2EXPosts.add(v2EXPost);
            }else
            {
                V2EXPost v2EXPost = new V2EXPost(author, null, null, avatar, title, null, url, null);
                v2EXPosts.add(v2EXPost);
            }
        }
        if(v2EXPosts.size() > 0)
            modelCallback.onSuccess(v2EXPosts);
        else
            modelCallback.onFail(new Exception());
    }

    @Override
    public void getPostDetailFromNet(V2EXPost v2exPost, ModelCallback<V2EXPost> postModelCallback)
    {
        Document detailDoc = null;
        try
        {
            detailDoc = Jsoup.parse(new URL(v2exPost.getUrl()), 10000);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        if(detailDoc == null)
        {
            postModelCallback.onFail(new Exception("获取网络信息失败"));
            return;
        }
        Element detailBody = detailDoc.body();
        Element contentEle = detailBody.getElementsByClass("topic_content").get(0);
        String content = contentEle.html();
        v2exPost.setContent(content);
        ArrayList<Reply> Replys = new ArrayList<>();
        Elements replyEles = detailBody.getElementsByAttributeValueContaining("id", "r_");
        for(Element replyEle : replyEles)
        {
            String replyAuthor = replyEle.getElementsByTag("a").get(0).text();
            String replyTime = replyEle.getElementsByTag("span").get(1).text().split("前")[0] + "前";
            String replyContent = replyEle.getElementsByClass("reply_content").get(0).text();
            byte[] replyAvatar = getImageBytes(replyEle.getElementsByTag("img").get(0).attr("src"));
            V2EXPost v2EXPost = new V2EXPost(replyAuthor, replyContent, replyTime, replyAvatar, null, null, null, null);
            Replys.add(v2EXPost);
        }
        if(Replys.size() > 0)
        {
            v2exPost.setReplies(Replys);
        }else if (content == null)
        {
            postModelCallback.onFail(new Exception("啥都没扒到"));
            return;
        }
        postModelCallback.onSuccess(v2exPost);
    }

    private byte[] getImageBytes(String imageUrl)
    {
        try
        {
            URL url = new URL("https:" + imageUrl);
            InputStream inputStream = url.openStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int tem;
            while ((tem = inputStream.read()) != -1)
            {
                byteArrayOutputStream.write(tem);
            }
            byte[] image = byteArrayOutputStream.toByteArray();
            inputStream.close();
            byteArrayOutputStream.close();
            if(image.length != 0)
            {
                return image;
            }

        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
