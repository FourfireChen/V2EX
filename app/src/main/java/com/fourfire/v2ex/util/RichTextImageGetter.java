package com.fourfire.v2ex.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 45089 on 2018/4/24.
 */

public class RichTextImageGetter implements Html.ImageGetter
{
    @Override
    public Drawable getDrawable(String source)
    {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        Drawable drawable = null;
        try
        {
            URL url = new URL(source);
            inputStream = url.openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }finally
        {
            if(inputStream != null)
                try
                {
                    inputStream.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
        if(bitmap != null)
        {
            drawable = new BitmapDrawable(null, bitmap);
            drawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        return drawable;
    }
}
