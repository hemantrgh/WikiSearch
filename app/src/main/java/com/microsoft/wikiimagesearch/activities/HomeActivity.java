package com.microsoft.wikiimagesearch.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.microsoft.wikiimagesearch.Models.SearchModel;
import com.microsoft.wikiimagesearch.R;
import com.microsoft.wikiimagesearch.activities.adapters.SearchResultAdapter;
import com.microsoft.wikiimagesearch.services.SearchService;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    EditText edtSearchText;
    ImageView imgSearch;
    ProgressDialog loadingProgress;
    ListView lstSearchResult;
    SearchResultAdapter searchResultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtSearchText = (EditText) findViewById(R.id.edtSearch);
        edtSearchText.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                String text = edtSearchText.getText().toString().toLowerCase(Locale.getDefault());
                if(text.length() <= 0)
                {
                    createAdapter();
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edtSearchText.getText().toString().toLowerCase(Locale.getDefault());
                getSearchResult(text);
            }
        });

        lstSearchResult = (ListView) findViewById(R.id.lstSearchResult);
        createAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createAdapter()
    {
        searchResultAdapter = new SearchResultAdapter(this, new ArrayList<SearchModel>());
        lstSearchResult.setAdapter(searchResultAdapter);
    }
    public void getSearchResult(String searchText)
    {
        showProgress();
        SearchService searchService = new SearchService(searchText, new SearchService.SearchServiceListener() {
            @Override
            public void onSearchServiceSuccess(ArrayList<SearchModel> tasks) {
                hideProgress();
                searchResultAdapter.setSearchModels(tasks);
                searchResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onSearchServiceFailure(String message) {
                hideProgress();
            }
        });
        searchService.execute();
    }

    public void showProgress()
    {
        loadingProgress = new ProgressDialog(this);
        loadingProgress.setMessage(getString(R.string.loading_search));
        loadingProgress.show();
    }

    public void hideProgress()
    {
        if(loadingProgress != null && loadingProgress.isShowing())
        {
            loadingProgress.dismiss();
            loadingProgress = null;
        }
    }
}
