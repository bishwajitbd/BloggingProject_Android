package com.branton.bishwajit.finalproject_blog.data;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.branton.bishwajit.finalproject_blog.Blog;
import com.branton.bishwajit.finalproject_blog.BlogSingleActivity;
import com.branton.bishwajit.finalproject_blog.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class BlogRecyclerAdapter extends RecyclerView.Adapter<BlogRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogRecyclerAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_rows_blog, viewGroup, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Blog blog= blogList.get(position);
        String imageUrl= null;
        viewHolder.title.setText(blog.getTitle());
        viewHolder.description.setText(blog.getDescription());

        java.text.DateFormat dataFromat= java.text.DateFormat.getDateInstance();
        String formattedDate= dataFromat.format(new Date(Long.valueOf(blog.getTimestamp())).getTime());
        viewHolder.timestamp.setText(formattedDate);
        imageUrl= blog.getImage();

        //TODO: use picaso liabrary to load image
        Picasso.with(context).load(imageUrl).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView timestamp;
        public ImageView image;
        String userid;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context= ctx;
            title= (TextView) itemView.findViewById(R.id.postTitleList);
            description= (TextView) itemView.findViewById(R.id.postTextList);
            image= (ImageView) itemView.findViewById(R.id.postImageList);
            timestamp= (TextView) itemView.findViewById(R.id.postingtime);
            userid= null;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Display detial blog
                    String titleData= (String) title.getText();
                    String DescriptionData= (String) description.getText();
                    Intent intent = new Intent (v.getContext(), BlogSingleActivity.class);
                    intent.putExtra("blog_title", titleData);
                    intent.putExtra("blog_Description", DescriptionData);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
