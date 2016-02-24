package com.sshex.openweather.network;

import com.sshex.openweather.models.WeatherModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


public interface ApiInterface {

    @GET("/weather")
    public void getWeather(@Query("q") String city, @Query("APPID") String key,@Query("units") String units, Callback<WeatherModel> response);

}
/*
public interface gitapi {
    @GET("/users/{user}")      //here is the other url part.best way is to start using /
    public void getFeed(@Path("user") String user, Callback<gitmodel> response);     //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO
}*/
