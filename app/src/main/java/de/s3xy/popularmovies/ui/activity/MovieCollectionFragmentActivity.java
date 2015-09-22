package de.s3xy.popularmovies.ui.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import de.s3xy.popularmovies.R;
import de.s3xy.popularmovies.ui.fragment.BestRatedMoviesCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.FavoriteMoviesCollectionFragment;
import de.s3xy.popularmovies.ui.fragment.PopularMoviesCollectionFragment;


public class MovieCollectionFragmentActivity extends BaseActivity {


    private static final String TAG_FRAGMENT_COLLECTION = "movie_collection_activity_collection_fragment";

    @Override
    protected void injectDependencies() {
        getMVPComponent().inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_collection_with_fragment);
        ButterKnife.bind(this);

        initToolbar();
        setupDrawerLayout();

        Fragment f = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_COLLECTION);

        if (f == null) {
            f = PopularMoviesCollectionFragment.getInstance();
        }

        loadFragment(f);
    }


    protected void setupDrawerLayout() {
        if (drawerLayout != null) {
            NavigationView view = (NavigationView) findViewById(R.id.navigation_view);
            view.setNavigationItemSelectedListener(menuItem -> {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.drawer_best_rated_movies:
                        loadFragment(BestRatedMoviesCollectionFragment.getInstance());
                        break;
                    case R.id.drawer_popular_movies:
                        loadFragment(PopularMoviesCollectionFragment.getInstance());
                        break;

                    case R.id.drawer_favorite_movies:
                        loadFragment(FavoriteMoviesCollectionFragment.getInstance());
                        break;
                }


                drawerLayout.closeDrawers();
                return true;
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.collection_fragment_container, fragment, TAG_FRAGMENT_COLLECTION);
        transaction.commit();
    }
}
