package com.example.evocab;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {

  //  @POST("api/token/auth/")
    @POST("api/token/auth/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

}
