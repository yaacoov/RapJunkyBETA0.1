package com.codeoasis.testwaves.Networking;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by USER on 8/21/2016.:)
 */
public interface RapJunkyService {
    @GET("RapJunky/{name}")
    Call<File> getFiles(@Path("name") String name);
}