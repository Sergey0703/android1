package com.example.evocab;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

  //  @POST("api/token/auth/")
    @POST("api/token/auth/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("api/words/")
    Call<WordResponse> getWord();

}
