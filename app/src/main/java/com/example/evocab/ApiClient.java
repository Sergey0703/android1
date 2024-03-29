package com.example.evocab;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;
    //private static final String BASE_URL = "http://10.0.2.2:8000/";
    private static final String BASE_URL = "http://3.95.252.233:8000/";
    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        if(retrofit==null) {
                    retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static ApiService getApiService(){
         ApiService apiService=getRetrofit().create(ApiService.class);
         return apiService;
    }
//     public static WordService getWordService(){
//        WordService wordService=getRetrofit("http://10.0.2.2:8000/").create(WordService.class);
//        return wordService;
//    }
}
