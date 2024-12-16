package ru.myitschool.work;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface SAPI {
    @GET("api/{login}/auth")
    Call<Void> checkLogin(@Path("login") String login);

    @GET("/api/{login}/info")
    Call<User> getInfo(@Path("login") String login);

    @PATCH("/api/{login}/open")
    Call<Void> openDoor(@Path("login") String login, @Body RequestBodyModel body);
}
