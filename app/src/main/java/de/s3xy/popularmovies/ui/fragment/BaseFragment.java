package de.s3xy.popularmovies.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import de.s3xy.popularmovies.PopularMoviesApplication;
import de.s3xy.popularmovies.di.components.ApplicationComponent;
import de.s3xy.popularmovies.di.components.MVPComponent;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/08/15.
 */
public abstract class BaseFragment extends Fragment {
    private MVPComponent mvpComponent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeInjector();
        injectDependencies();

    }

    private void initializeInjector() {
        mvpComponent = getApplicationComponent().plus();
    }

    public MVPComponent getMVPComponent() {
        return mvpComponent;
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((PopularMoviesApplication) getActivity().getApplication()).getApplicationComponent();
    }

    protected abstract void injectDependencies();

}
