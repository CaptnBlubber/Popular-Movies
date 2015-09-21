package de.s3xy.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.Review;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 05.08.15.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    /**
     * Injectable constructor allows adapter injection
     */
    @Inject
    public ReviewAdapter() {
        reviewList = new ArrayList<>();
    }


    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);

        holder.mReviewAuthor.setText(review.getAuthor());
        holder.mReviewContent.setText(review.getContent());

    }


    public Review getReview(int position) {
        return reviewList.get(position);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void addReviews(List<Review> reviews) {
        reviewList.addAll(reviews);
        notifyDataSetChanged();
    }


    public void setReviews(List<Review> reviews) {
        reviewList.clear();
        addReviews(reviews);
    }



    public static class ReviewViewHolder extends RecyclerView.ViewHolder  {
        View view;

        @Bind(R.id.review_author)
        TextView mReviewAuthor;

        @Bind(R.id.review_content)
        TextView mReviewContent;


        public ReviewViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;

            ButterKnife.bind(this, itemView);
        }


    }

}
