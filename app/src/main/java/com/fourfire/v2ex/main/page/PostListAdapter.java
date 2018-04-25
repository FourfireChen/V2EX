package com.fourfire.v2ex.main.page;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourfire.v2ex.R;
import com.fourfire.v2ex.data.bean.Reply;
import com.fourfire.v2ex.data.bean.V2EXPost;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/17.
 */

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder>
{
    private ArrayList<V2EXPost> v2EXPosts;
    private OnItemClickListener itemClickListener;

    public PostListAdapter()
    {}

    public PostListAdapter(ArrayList<V2EXPost> v2EXPosts)
    {
        this.v2EXPosts = v2EXPosts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_listitem_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, final int position)
    {
        V2EXPost thisV2EXPost = v2EXPosts.get(position);
        if(itemClickListener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    itemClickListener.onClick(position);
                }
            });
        }
        holder.author.setText(thisV2EXPost.getAuthor());
        holder.title.setText(thisV2EXPost.getTitle());
        if(thisV2EXPost.getTime() != null)
            holder.time.setText("发布于" + thisV2EXPost.getTime());
        if(thisV2EXPost.getLastReply() != null)
            holder.lastReply.setText(thisV2EXPost.getLastReply());
        if(thisV2EXPost.getAnthorAvatar() != null)
            holder.avatar.setImageBitmap(BitmapFactory.decodeByteArray(thisV2EXPost.getAnthorAvatar(), 0, thisV2EXPost.getAnthorAvatar().length));
    }

    @Override
    public int getItemCount()
    {
        return v2EXPosts == null ? 0 : v2EXPosts.size();
    }

    public void setV2EXPosts(ArrayList<V2EXPost> v2EXPosts)
    {
        this.v2EXPosts = v2EXPosts;
    }

    class PostViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView avatar;
        private TextView title, author, time, lastReply;
        public PostViewHolder(View itemView)
        {
            super(itemView);
            avatar = itemView.findViewById(R.id.post_avatar);
            title = itemView.findViewById(R.id.post_title);
            author = itemView.findViewById(R.id.post_author);
            time = itemView.findViewById(R.id.post_time);
            lastReply = itemView.findViewById(R.id.post_lastreply);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    interface OnItemClickListener
    {
        void onClick(int position);
    }
}
