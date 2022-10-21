package com.smartsimjgraphics.bccihub.forum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsimjgraphics.bccihub.R;

import java.util.ArrayList;

public class AdapterForum extends RecyclerView.Adapter<ForumViewHolder> {

    ArrayList<Forum_Model> forums;
    Context context;

    public AdapterForum() {
        forums = new ArrayList<Forum_Model>();
    }

    public void setData(ArrayList<Forum_Model> forums){
        this.forums = forums;
    }

    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View forumView = layoutInflater.inflate(R.layout.layout_forum_list_display,parent,false);
        return new ForumViewHolder(forumView);

    }

    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {

        Forum_Model forum_model = forums.get(position);
        holder.forumID.setText(forum_model.getForumID());
        holder.forum_category.setText(forum_model.getForum_category());
        String str_forum_description = forum_model.getForum_desc();
        holder.forum_date.setText(forum_model.getForum_date());
        holder.forum_time.setText(forum_model.getForum_time());
        holder.cust_email.setText(forum_model.getCust_email());
        holder.cust_phone.setText(forum_model.getCust_phone());

/*        Picasso.get().load(listItem.getImage_url())
                .into(holder.imageView);*/

        holder.layout_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "You Clicked: " + str_forum_description, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return forums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView forumID, forum_category, forum_desc, forum_date, forum_time,
                cust_email, cust_phone;
        public ImageView imageView;
        public LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.id_layout_forum);
            forumID = itemView.findViewById(R.id.id_tv_forum_id);
            forum_category = itemView.findViewById(R.id.id_tv_forum_category);
            forum_date = itemView.findViewById(R.id.id_tv_forum_date);
            forum_time = itemView.findViewById(R.id.id_tv_forum_time);
            cust_email = itemView.findViewById(R.id.id_tv_forum_cust_email);
            cust_phone = itemView.findViewById(R.id.id_tv_forum_cust_phone);
            imageView = itemView.findViewById(R.id.imageView);

        }

    }

}
