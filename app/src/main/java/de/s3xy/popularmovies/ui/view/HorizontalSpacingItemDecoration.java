package de.s3xy.popularmovies.ui.view;

import android.graphics.Rect;
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
public class HorizontalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spacing;

    public HorizontalSpacingItemDecoration(float spacing) {
        this.spacing = (int) spacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //We need to avoid double spacing, but the first element needs spacing on left.
        if(parent.getChildAdapterPosition(view) > 0) {
            outRect.left = spacing /2;
        }

        if(parent.getChildAdapterPosition(view) < parent.getAdapter().getItemCount() -1) {
            outRect.right = spacing / 2;
        }

    }
}
