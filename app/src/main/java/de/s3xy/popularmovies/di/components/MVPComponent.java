package de.s3xy.popularmovies.di.components;

import dagger.Subcomponent;
import de.s3xy.popularmovies.di.modules.InteractorModule;
import de.s3xy.popularmovies.di.modules.PresenterModule;
import de.s3xy.popularmovies.di.scopes.ApplicationScope;
import de.s3xy.popularmovies.ui.activity.MovieCollectionFragmentActivity;
import de.s3xy.popularmovies.ui.activity.MovieDetailFragmentActivity;
import de.s3xy.popularmovies.ui.fragment.FavoriteMoviesCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.MovieCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.MovieDetailFragment;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/07/15..
 */
@ApplicationScope
@Subcomponent(modules = {PresenterModule.class, InteractorModule.class})
public interface MVPComponent {

    void inject(MovieDetailFragment movieDetailFragment);

    void inject(MovieDetailFragmentActivity movieDetailFragmentActivity);

    void inject(MovieCollectionFragment movieCollectionFragment);

    void inject(FavoriteMoviesCollectionFragment movieCollectionFragment);

    void inject(MovieCollectionFragmentActivity movieCollectionFragmentActivity);
}
