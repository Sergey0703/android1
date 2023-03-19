package com.example.evocab;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WordService {

  //  @POST("api/token/auth/")
    @GET("api/words/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

}
