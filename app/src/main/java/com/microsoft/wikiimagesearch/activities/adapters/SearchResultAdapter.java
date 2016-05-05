package com.microsoft.wikiimagesearch.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.NetworkImageView;
import com.microsoft.wikiimagesearch.Models.SearchModel;
import com.microsoft.wikiimagesearch.R;
import com.microsoft.wikiimagesearch.WikiSearchApp;

import java.util.ArrayList;

/**
 * Created by hemant_ghodke on 5/4/2016.
 */
public class SearchResultAdapter extends BaseAdapter{

    ArrayList<SearchModel> searchModels;
    Context context;
    LayoutInflater mInflater;

    public SearchResultAdapter(Context context, ArrayList<SearchModel> searchModels) {
        this.searchModels = searchModels;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return searchModels.size();
    }

    @Override
    public SearchModel getItem(int position) {
        return searchModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = mInflater.inflate(R.layout.search_item, null);
            holder = new ViewHolder();
            holder.searchImage = (NetworkImageView) convertView.findViewById(R.id.searchImage);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.searchImage.setImageUrl(getItem(position).getImgURL(), WikiSearchApp.getInstance().getImageLoader());

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        animation.setStartOffset(position*500);
        convertView.startAnimation(animation);

        return convertView;
    }

    public void setSearchModels(ArrayList<SearchModel> searchModels) {
        this.searchModels = searchModels;
    }

    static class ViewHolder {
        NetworkImageView searchImage;
    }
}
