package com.application.charles.rssfeed.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.application.charles.rssfeed.R;
import com.application.charles.rssfeed.adapters.EndlessRecyclerViewScrollListener;
import com.application.charles.rssfeed.adapters.RssFeedAdapter;
import com.application.charles.rssfeed.models.Entry;
import com.application.charles.rssfeed.models.Feed;
import com.application.charles.rssfeed.presenters.RssFeedPresenter;

import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private ContentLoadingProgressBar mProgressBar;
    private RssFeedPresenter mPresenter;
    private LinearLayoutManager mLayoutManager;
    private RssFeedPresenter.onRssFeedLoadListener mLoadListener =
            new RssFeedPresenter.onRssFeedLoadListener() {
                @Override
                public void onLoadSuccess(Feed feed) {
                    enableProgressBar(false);
                    setUpRecyclerView(feed.getmEntries());
                }

                @Override
                public void onLoadError(String error) {
                    enableProgressBar(false);
                    Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRecyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
        mProgressBar = (ContentLoadingProgressBar) findViewById(R.id.main_activity_loading_bar);
        mPresenter = new RssFeedPresenter(this, mLoadListener);
        mLayoutManager = new LinearLayoutManager(this);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.main_activity_layout;
    }

    @Override
    protected boolean showBackButton() {
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        enableProgressBar(true);
        mPresenter.getTopSongs();
    }

    protected void setUpRecyclerView(List<Entry> entries) {
        RssFeedAdapter adapter = new RssFeedAdapter(entries, this);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                //TODO callback to present more data, because the given api
                //only returns 10 items, so there is no more data to load.
                Log.i("loadMore", "onLoadMore");
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void enableProgressBar(boolean enable) {
        mProgressBar.setVisibility(enable? View.VISIBLE : View.INVISIBLE);
    }
}
