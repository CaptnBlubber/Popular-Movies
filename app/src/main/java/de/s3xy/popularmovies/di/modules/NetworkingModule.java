package de.s3xy.popularmovies.di.modules;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.s3xy.popularmovies.api.ApiResponseInterceptor;
import de.s3xy.popularmovies.api.TheMovieDBApiAdapter;
import de.s3xy.popularmovies.api.TheMovieDBService;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by Angelo Rüggeberg on 06/08/15.
 */
@Module
public class NetworkingModule {

    @Provides
    @Singleton
    TheMovieDBService provideTheMovieDBService(RestAdapter restAdapter) {
        return restAdapter.create(TheMovieDBService.class);
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(OkClient client, RequestInterceptor interceptor) {
        return TheMovieDBApiAdapter.getInstance(client, interceptor);
    }

    @Provides
    @Singleton
    public OkClient provideOkClient() {
        OkHttpClient httpClient = new OkHttpClient();
        return new OkClient(httpClient);
    }


    @Provides
    @Singleton
    public RequestInterceptor provideRequestInterceptor() {
        return new ApiResponseInterceptor();
    }

}
