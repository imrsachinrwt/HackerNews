package com.example.sachin.hackernews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter {
    private static final String TAG = "MyAdapter";

    private final int resource;
    private final LayoutInflater layoutInflater;
    private List<HackerFeed> hackerFeedList;

    public MyAdapter(@NonNull Context context, int resource, List<HackerFeed> hackerFeedsList) {
        super(context, resource);
        this.layoutInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.hackerFeedList = hackerFeedsList;
    }


    @Override
    public int getCount() {
        return hackerFeedList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(resource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HackerFeed feed = hackerFeedList.get(position);

        viewHolder.title.setText(feed.getTitle());
        viewHolder.exUrl.setText(feed.getExternal_url());
        viewHolder.datePub.setText(feed.getDate_pub());
        viewHolder.author.setText(feed.getAuthor());


        return convertView;


    }

    class ViewHolder {
        TextView title;
        TextView exUrl;
        TextView datePub;
        TextView author;

        ViewHolder(View v) {
            title = v.findViewById(R.id.title);
            exUrl = v.findViewById(R.id.exUrl);
            datePub = v.findViewById(R.id.datePub);
            author = v.findViewById(R.id.author);

        }


    }


}






