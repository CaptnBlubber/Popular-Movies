package de.s3xy.popularmovies.ui.view;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 05.08.15.
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public SpacingItemDecoration(float spacing) {
        this.spacing = (int) spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


        outRect.left = spacing / 2;
        outRect.right = spacing / 2;
        outRect.bottom = spacing;

        int topElements = 1;

        if( parent.getLayoutManager() instanceof GridLayoutManager) {
            topElements =((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
        }

        //We need to avoid double spacing, but the first element needs spacing on top.
        if(parent.getChildAdapterPosition(view) <= topElements - 1) {
            outRect.top = spacing;
        }
    }
}
