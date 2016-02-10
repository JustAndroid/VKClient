package com.test.vkclient.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.vkclient.R;
import com.test.vkclient.model.Post;
import com.test.vkclient.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Павел on 08.02.2016.
 * Адаптер для отображения постов на стене
 */
public class WallAdapter extends BaseAdapter {

    private ArrayList<Post> posts;
    private ArrayList<User> profiles;
    private LayoutInflater inflater;
    private Context context;


    public WallAdapter(Context context, ArrayList<Post> posts, ArrayList<User> profiles) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.posts = posts;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row=convertView;
        if (convertView == null) {

            row = inflater.inflate(R.layout.lv_wallpost,
                    parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.text.setText(posts.get(position).getText());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(posts.get(position).getDate() * 1000);
        holder.date.setText(String.valueOf(calendar.getTime()));
        Log.d("POSITION", String.valueOf(position));
        if ((posts.get(position).getAttachments()!=null) &&(posts.get(position).getAttachments().get(0).getType().equals("photo"))) {
            Picasso.with(context).load(posts.get(position).getAttachments().get(0).getPhoto().getPhoto()).into(holder.image);
        }

        for (User user : profiles) {
            if (user.getId() == posts.get(position).getFrom_id()) {
                holder.name.setText(user.getFirst_name() + " " + user.getLast_name());
                Picasso.with(context).load(user.getPhoto_100()).into(holder.avatar);
            }
        }

        return row;
    }

    static class ViewHolder  {
        @Bind(R.id.avatarImageView) ImageView avatar;
        @Bind(R.id.nameTextView) TextView name;
        @Bind(R.id.dateTextView) TextView date;
        @Bind(R.id.postTextView) TextView text;
        @Bind(R.id.postImageView) ImageView image;

        public ViewHolder (View view) {
            ButterKnife.bind(this, view);
        }
    }


}
