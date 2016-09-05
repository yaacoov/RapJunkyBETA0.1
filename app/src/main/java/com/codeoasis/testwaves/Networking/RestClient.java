package com.codeoasis.testwaves.Networking;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by USER on 8/21/2016. :)
 */

public class RestClient {
        private static RestClient restClient;
        private RapJunkyService rapJunkyService;
        private RestClient(){

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.readTimeout(120, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.backendless.com/78E983A5-BBF1-3B4D-FF1B-420A24AD8E00/v1/files/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
            restClient = retrofit.create(RestClient.class);

    }

    public static RestClient getRestClient() {
        if (restClient == null)
            restClient = new RestClient();
        return restClient;
    }

        public Call<File> getFiles(String name){
        return rapJunkyService.getFiles(name);
    }

}
