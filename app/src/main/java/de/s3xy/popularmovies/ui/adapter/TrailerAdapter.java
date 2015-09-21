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
import de.s3xy.popularmovies.api.models.Trailer;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 05.08.15.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailerList;
    private TrailerViewHolder.ITrailerInteractions mListener;

    /**
     * Injectable constructor allows adapter injection
     */
    @Inject
    public TrailerAdapter() {
        trailerList = new ArrayList<>();
    }


    public static String getYoutubeThumbnailPath(String videoKey) {
        return String.format("http://img.youtube.com/vi/%s/hqdefault.jpg", videoKey);
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer trailer = trailerList.get(position);

        holder.mTrailerTitle.setText(trailer.getName());

        Glide.with(holder.view.getContext())
                .load(getYoutubeThumbnailPath(trailer.getKey()))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.mTrailerThumbnail);

    }

    public void registerInteractionListener(TrailerViewHolder.ITrailerInteractions listener) {
        mListener = listener;
    }


    public Trailer getTrailer(int position) {
        return trailerList.get(position);
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void addTrailers(List<Trailer> trailers) {
        trailerList.addAll(trailers);
        notifyDataSetChanged();
    }


    public void setTrailers(List<Trailer> trailers) {
        trailerList.clear();
        addTrailers(trailers);
    }



    public static class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ITrailerInteractions mListener;

        View view;

        @Bind(R.id.trailer_thumbnail)
        ImageView mTrailerThumbnail;

        @Bind(R.id.trailer_title)
        TextView mTrailerTitle;


        public TrailerViewHolder(View itemView, ITrailerInteractions listener) {
            super(itemView);
            this.view = itemView;
            mListener = listener;

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onTrailerClicked(getAdapterPosition());
            }
        }

        public interface ITrailerInteractions {
            void onTrailerClicked(int position);
        }
    }

}
