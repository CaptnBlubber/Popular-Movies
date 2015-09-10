package de.s3xy.popularmovies.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.api.models.Movie;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 05.08.15.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.TweetViewHolder> {

    private List<Movie> movieList;
    private TweetViewHolder.IMovieInteractions mListener;

    /**
     * Injectable constructor allows adapter injection
     */
    @Inject
    public MovieAdapter() {
        movieList = new ArrayList<>();
    }

    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_movie, parent, false);
        return new TweetViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(TweetViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.mMovieTitle.setText(movie.getTitle());

        Glide.with(holder.view.getContext())
                .load(movie.getPosterPath())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mMoviePoster);

    }

    public void registerTweetInteractionListener(TweetViewHolder.IMovieInteractions listener) {
        mListener = listener;
    }


    public Movie getMovie(int position) {
        return movieList.get(position);
    }

    public void unregisterTweetInteractionListener() {
        mListener = null;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void addMovies(List<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }


    public void setMovies(List<Movie> movies) {
        movieList.clear();
        addMovies(movies);
    }

    public static class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        IMovieInteractions mListener;

        View view;

        @Bind(R.id.movie_poster)
        ImageView mMoviePoster;

        @Bind(R.id.movie_title)
        TextView mMovieTitle;


        public TweetViewHolder(View itemView, IMovieInteractions listener) {
            super(itemView);
            this.view = itemView;
            mListener = listener;

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onMovieClicked(getAdapterPosition());
            }
        }

        public interface IMovieInteractions {
            void onMovieClicked(int position);
        }
    }

}
