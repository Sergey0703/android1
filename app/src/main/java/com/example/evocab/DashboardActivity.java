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
              System.out.println("Ok");
              takeWord();
            }
        });



    }


    public void takeWord(){


        WordRequest wordRequest=new WordRequest();
        System.out.println("Ok2");
        //Call<WordResponse> wordResponseCall=ApiClient.getApiService().getWord(wordRequest);
        Call<WordResponse> wordResponseCall=ApiClient.getApiService().getWord();
        System.out.println("Ok3");
        wordResponseCall.enqueue(new Callback<WordResponse>() {
            @Override
            public void onResponse(Call<WordResponse> call, Response<WordResponse> response) {
                if (response.isSuccessful()){
                    System.out.println("Successful");
                    Toast.makeText(DashboardActivity.this, " Successful",Toast.LENGTH_LONG).show();
                    //  token=response.body().getToken();
                    WordResponse wordResponse= response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("Response=");
                            //System.out.println("Response="+loginResponse.getEmail());
                          //  startActivity(new Intent(DashboardActivity.this,DashboardActivity.class).putExtra("data",loginResponse.getEmail()));
                        }
                    },700);
                }else{
                    System.out.println("Failed....");
                    Toast.makeText(DashboardActivity.this, " Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WordResponse> call, Throwable t) {
                System.out.println("Trouble "+t.getLocalizedMessage());
                Toast.makeText(DashboardActivity.this, "Trouble "+t.getLocalizedMessage() ,Toast.LENGTH_LONG).show();
            }
        });
    }
}