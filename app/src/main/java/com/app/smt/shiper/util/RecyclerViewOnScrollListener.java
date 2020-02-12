package com.app.smt.shiper.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

    private String TAG = RecyclerViewOnScrollListener.class.getSimpleName();

    private int previousTotal = 0; // The total number of items in the dataset after the last load

    private boolean canNextPage = true;

    private boolean loading = true;
    // True if we are still waiting for the last set of data to load.

    private int visibleThreshold = 4;
    // The minimum amount of items to have below your current scroll position before loading more.

    int firstVisibleItem, visibleItemCount, totalItemCount;

    public int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public RecyclerViewOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    //called when scrolled to the bottom
    public abstract void onLoadMore(int current_page);

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        //Log.i(TAG,current_page + "loading:" + loading + "-" + totalItemCount + "-" + visibleItemCount + "-" + firstVisibleItem + "-" + visibleThreshold + "-" + previousTotal);
        if (canNextPage && !loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold)) {
            // End has been reached
            // Do something
            current_page++;

            loading = true;

            onLoadMore(current_page);

        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    public void reset() {
        previousTotal = 0;
        loading = true;
    }

    public void setCanNextPage(boolean canNextPage) {
        this.canNextPage = canNextPage;
    }
}
