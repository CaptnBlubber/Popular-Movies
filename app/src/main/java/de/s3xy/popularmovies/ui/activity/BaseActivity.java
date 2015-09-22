package de.s3xy.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import de.s3xy.popularmovies.PopularMoviesApplication;
import de.s3xy.popularmovies.R;
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
public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    @Bind(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    private MVPComponent mvpComponent;

    private boolean isDetailActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        return ((PopularMoviesApplication) getApplication()).getApplicationComponent();
    }

    protected abstract void injectDependencies();

    protected void initToolbar() {
        initToolbar(false);
    }

    protected void initToolbar(boolean childActivity) {
        isDetailActivity = childActivity;

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (!isDetailActivity) {
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            }

            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (isDetailActivity) {
                    onBackPressed();
                } else {
                    if (drawerLayout != null) {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public abstract void setSupportActionBar(Toolbar toolbar, boolean detailFragment);

}
