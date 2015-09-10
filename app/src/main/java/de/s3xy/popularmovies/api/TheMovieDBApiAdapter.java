package de.s3xy.popularmovies.api;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.JacksonConverter;

/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 06.09.15..
 */
public class TheMovieDBApiAdapter {

    public static RestAdapter getInstance(OkClient client, RequestInterceptor interceptor) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setConverter(new JacksonConverter())
                .setRequestInterceptor(interceptor)
                .setClient(client)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        return restAdapter;

    }
}
