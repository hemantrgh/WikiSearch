package com.microsoft.wikiimagesearch.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hemant_ghodke on 5/4/2016.
 */
public class SearchModel {

    interface JSON_KEYS
    {
        final String KEY_QUERY          = "query";
        final String KEY_PAGES          = "pages";
        final String KEY_PAGEID         = "pageid";
        final String KEY_TITLE          = "title";
        final String KEY_THUMBNAIL      = "thumbnail";
        final String KEY_SOURCE         = "source";
    }
    String title;
    String imgURL;

    public SearchModel()
    {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public static ArrayList<SearchModel> parseSearchResult(JSONObject searchResult) throws JSONException {
        ArrayList<SearchModel> searchModels = new ArrayList<SearchModel>();
        SearchModel searchModel;
        if(searchResult.has(JSON_KEYS.KEY_QUERY))
        {
            JSONObject queryObject = searchResult.getJSONObject(JSON_KEYS.KEY_QUERY);

            if(queryObject.has(JSON_KEYS.KEY_PAGES))
            {
                JSONObject pagesObject = queryObject.getJSONObject(JSON_KEYS.KEY_PAGES);
                    Iterator iterator = pagesObject.keys();
                    while(iterator.hasNext())
                    {
                        searchModel = new SearchModel();
                        String key = (String)iterator.next();
                        JSONObject pageObject = pagesObject.getJSONObject(key);

                        if(pageObject.has(JSON_KEYS.KEY_TITLE))
                        {
                            searchModel.setTitle(pageObject.getString(JSON_KEYS.KEY_TITLE));
                        }
                        if(pageObject.has(JSON_KEYS.KEY_THUMBNAIL))
                        {
                            JSONObject thumbnailObject = pageObject.getJSONObject(JSON_KEYS.KEY_THUMBNAIL);
                            if(thumbnailObject.has(JSON_KEYS.KEY_SOURCE))
                            {
                                searchModel.setImgURL(thumbnailObject.getString(JSON_KEYS.KEY_SOURCE));
                                searchModels.add(searchModel);
                            }
                        }
                    }
            }
        }
        return searchModels;
    }
}
