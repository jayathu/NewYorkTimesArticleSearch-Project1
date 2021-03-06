package project.codepath.nytimessearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import project.codepath.nytimessearch.DividerItemDecoration;
import project.codepath.nytimessearch.EndlessRecyclerViewScrollListener;
import project.codepath.nytimessearch.ItemClickSupport;
import project.codepath.nytimessearch.R;
import project.codepath.nytimessearch.adapters.ArticleRecyclerAdapter;
import project.codepath.nytimessearch.dialogs.QueryFiltersDialog;
import project.codepath.nytimessearch.models.Article;
import project.codepath.nytimessearch.models.ArticleFactory;
import project.codepath.nytimessearch.models.QueryFilters;

public class SearchActivity extends AppCompatActivity {

    private String searchString;
    private QueryFilters filters;


    private String filtersString;

    @Bind(R.id.rvResults)RecyclerView rvResults;
    ArrayList<Article> articles;
    ArticleRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*getSupportActionBar().setLogo(R.drawable.news);
        getSupportActionBar().setTitle("My new title");
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/

        ButterKnife.bind(this);

        filters = new QueryFilters();
        filters.setBeginDate(1, 1, 2016);
        filters.setNews_desk(false, false, false);
        filters.setSortOrder(false);

        articles = new ArrayList<>();
        adapter = new ArticleRecyclerAdapter(articles);
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        rvResults.addItemDecoration(itemDecoration);

        rvResults.setAdapter(adapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvResults.setLayoutManager(linearLayoutManager);


        rvResults.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                fetchArticles(searchString, page);
                int curSize = adapter.getItemCount();
                adapter.notifyItemRangeInserted(curSize, articles.size() - 1);
                Log.d("DEBUG", searchString);
            }
        });

        ItemClickSupport.addTo(rvResults).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Article article = articles.get(position);
                        Intent intent = new Intent(getApplicationContext(), ArticleDetails.class);
                        intent.putExtra("url", article.getWebUrl());
                        startActivity(intent);
                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();

                searchString = query;
                articles.clear();
                adapter.notifyDataSetChanged();
                fetchArticles(query, 0);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        MenuItem miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2)
        {
            filters = (QueryFilters)Parcels.unwrap(data.getParcelableExtra("query_filters"));

            //Toast.makeText(this, date_param+sort_param+news_desk_param, Toast.LENGTH_LONG).show();
        }
    }

    public void showEditDialog() {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        QueryFiltersDialog dialog = QueryFiltersDialog.newInstance("", "", "");
        dialog.show(fragmentManager, "update_filter_settings");
    }

    public void onSettingsAction(MenuItem menuItem) {

        Intent intent = new Intent(SearchActivity.this, Settings.class);

        intent.putExtra("query_filters", Parcels.wrap(filters));
        startActivityForResult(intent, 2);

    }


    public void fetchArticles(String query, int page) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();
        params.put("api-key", "45a7603076c2721c61f5253eea5052b4:5:60985033");
        params.put("page", page);
        params.put("q", query);

        String news_desk_params = filters.getNews_desk();
        if(news_desk_params != "") {
            params.put("fq", "news_desk:" + filters.getNews_desk());
        }
        params.put("sort", filters.getSortOrder());
        params.put("begin_date", filters.getBeginDateString());
        Log.d("DEBUG", params.toString());

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                JSONArray articleJsonResults = null;
                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");

                    articles.addAll(ArticleFactory.fromJSONArray(articleJsonResults));
                    adapter.notifyDataSetChanged();

                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("FAILURE ", filtersString);
                Log.d("FAILURE_ERROR ", errorResponse.toString());
            }
        });

    }
}
