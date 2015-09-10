package de.s3xy.popularmovies.api;


import de.s3xy.popularmovies.BuildConfig;
import retrofit.RequestInterceptor;


/**
 * +        o     o       +        o
 * -_-_-_-_-_-_-_,------,      o
 * _-_-_-_-_-_-_-|   /\_/\
 * -_-_-_-_-_-_-~|__( ^ .^)  +     +
 * _-_-_-_-_-_-_-"  ""
 * +      o         o   +       o
 * Created by rue0003a on 06.09.15..
 */
public class ApiResponseInterceptor implements RequestInterceptor {
    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam("api_key", BuildConfig.API_KEY);
    }
}
