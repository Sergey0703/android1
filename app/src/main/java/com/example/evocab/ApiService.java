package com.example.evocab;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

  //  @POST("api/token/auth/")
    @POST("api/token/auth/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @PATCH("api/words/")
    Call<WordResponse> sendWord(@Body WordRequest wordRequest);
    @GET("api/words/")
    Call<WordResponse> getWord(@Query("nav") String nav, @Query("_id") String id);

}
