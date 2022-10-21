package com.smartsimjgraphics.bccihub.forum;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.R;

public class ForumViewHolder extends RecyclerView.ViewHolder{

    public TextView forumID, forum_category, forum_desc, forum_date, forum_time,
            cust_email, cust_phone;
    public ImageView imageView;
    public LinearLayout layout_forum;

    public ForumViewHolder(@NonNull View itemView) {
        super(itemView);

        layout_forum = itemView.findViewById(R.id.id_layout_forum);
        forumID = itemView.findViewById(R.id.id_tv_forum_id);
        forum_category = itemView.findViewById(R.id.id_tv_forum_category);
        forum_date = itemView.findViewById(R.id.id_tv_forum_date);
        forum_time = itemView.findViewById(R.id.id_tv_forum_time);
        cust_email = itemView.findViewById(R.id.id_tv_forum_cust_email);
        cust_phone = itemView.findViewById(R.id.id_tv_forum_cust_phone);
        imageView = itemView.findViewById(R.id.imageView);

    }
}
