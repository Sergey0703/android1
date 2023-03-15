package com.example.evocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    Button btnWordOk;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnWordOk=findViewById(R.id.btnWordOk);

        username=findViewById(R.id.dashUserName);
        Intent intent =getIntent();
        if(intent.getExtras()!=null){
            System.out.println("Extra="+intent.getExtras());
            String passedUserName=intent.getStringExtra("data");
            username.setText("Welcom "+passedUserName);
        }

        btnWordOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              takeWord();
            }
        });



    }


    public void takeWord(){
        LoginRequest loginRequest=new LoginRequest();

        Call<LoginResponse> loginResponseCall=ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(DashboardActivity.this, " Successful",Toast.LENGTH_LONG).show();
                    //  token=response.body().getToken();
                    LoginResponse loginResponse= response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Response=");
                            //System.out.println("Response="+loginResponse.getEmail());
                          //  startActivity(new Intent(DashboardActivity.this,DashboardActivity.class).putExtra("data",loginResponse.getEmail()));
                        }
                    },700);
                }else{
                    Toast.makeText(DashboardActivity.this, " Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(DashboardActivity.this, "Trouble "+t.getLocalizedMessage() ,Toast.LENGTH_LONG).show();
            }
        });
    }
}