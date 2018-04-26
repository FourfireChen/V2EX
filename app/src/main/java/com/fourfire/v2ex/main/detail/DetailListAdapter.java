package com.fourfire.v2ex.main.detail;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fourfire.v2ex.R;
import com.fourfire.v2ex.data.bean.Reply;
import com.fourfire.v2ex.data.bean.V2EXPost;
import com.fourfire.v2ex.util.RichTextImageGetter;

import java.util.ArrayList;

/**
 * Created by 45089 on 2018/4/18.
 */

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailViewHolder> {
    private V2EXPost v2EXPost;
    private ArrayList<Reply> Replies;
    private Context context;

    public DetailListAdapter() {}

    public DetailListAdapter(Context context) {
        this.context = context;
    }

    public DetailListAdapter(V2EXPost v2EXPost) {
        this.v2EXPost = v2EXPost;
        Replies = (ArrayList<Reply>) v2EXPost.getReplies();
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailViewHolder holder, int position) {
        int id = 0;
        switch (position) {
            case 0:
                holder.title.setText(v2EXPost.getTitle());
                final String contentHtmlString = v2EXPost.getContent();
                holder.avatar.setImageBitmap(BitmapFactory.decodeByteArray(v2EXPost.getAnthorAvatar(), 0, v2EXPost.getAnthorAvatar().length));
                if (v2EXPost.getTime() != null)
                    holder.time.setText(v2EXPost.getTime());
                if (v2EXPost.getContent() != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Spanned spanned = Html.fromHtml(contentHtmlString, Html.FROM_HTML_MODE_LEGACY, new RichTextImageGetter(), null);
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    holder.content.setText(spanned);
                                }
                            });
                        }
                    }).start();
                }
                holder.author.setText(v2EXPost.getAuthor());
                break;
            default:
                holder.title.setVisibility(View.GONE);
                holder.replyBar.setVisibility(View.GONE);
                id = position - 1;
                holder.content.setText(Replies.get(id).getContent());
                holder.avatar.setImageBitmap(BitmapFactory.decodeByteArray(Replies.get(id).getAnthorAvatar(), 0, Replies.get(id).getAnthorAvatar().length));
                holder.time.setText(Replies.get(id).getTime());
                holder.author.setText(Replies.get(id).getAuthor());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return (v2EXPost == null ? 0 : v2EXPost.getReplies().size() + 1);
    }

    public void setV2EXPost(V2EXPost v2EXPost) {
        this.v2EXPost = v2EXPost;
        this.Replies = (ArrayList<Reply>) v2EXPost.getReplies();
    }

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView author, time, title, content;
        private CardView replyBar;
        private ImageView avatar;

        public DetailViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.detail_author);
            time = itemView.findViewById(R.id.detail_time);
            title = itemView.findViewById(R.id.detail_title);
            content = itemView.findViewById(R.id.detail_content);
            avatar = itemView.findViewById(R.id.detail_avatar);
            replyBar = itemView.findViewById(R.id.reply_bar);
        }
    }

}
