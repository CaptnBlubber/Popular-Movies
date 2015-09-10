package de.s3xy.popularmovies.mvp.presenter;


public interface Presenter<T> {
    void onDestroy();
    void bindView(T view);
    void unbindView();
}