package com.microsoft.wikiimagesearch.services;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.microsoft.wikiimagesearch.Models.SearchModel;
import com.microsoft.wikiimagesearch.R;
import com.microsoft.wikiimagesearch.WikiSearchApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hemant_ghodke on 5/4/2016.
 */
public class SearchService {

    static final String TAG = SearchService.class.toString();
    String tag_json_arry = "SearchService";
    public interface SearchServiceListener
    {
        public void onSearchServiceSuccess(ArrayList<SearchModel> tasks);
        public void onSearchServiceFailure(String message);
    }

    String searchText;
    SearchServiceListener listener;

    public SearchService(String searchText, SearchServiceListener listener) {
        this.searchText = searchText;
        this.listener = listener;
    }

    public void execute()
    {
        String url = WikiSearchApp.getInstance().getString(R.string.search_url, searchText);

        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject obj) {
                Log.e(TAG, obj.toString());
                if(obj != null) {
                    try {
                        listener.onSearchServiceSuccess(SearchModel.parseSearchResult(obj));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        listener.onSearchServiceFailure(WikiSearchApp.getInstance().getString(R.string.search_error));
                    }
                }
                else
                {
                    listener.onSearchServiceFailure(WikiSearchApp.getInstance().getString(R.string.search_error));
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                Log.e(TAG, arg0.toString());
                listener.onSearchServiceFailure(WikiSearchApp.getInstance().getString(R.string.search_error));
            }
        });
        WikiSearchApp.getInstance().addToRequestQueue(searchRequest);
    }
}
