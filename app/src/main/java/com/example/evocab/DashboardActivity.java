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
    Button btnWordStudy;

    Button btnWordTranslate;
    TextView wordsTodayCount;
    TextView wordsTodayBadCount;

    TextView username;
    TextView word;

    TextView transcript;
    TextView translate;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnWordOk=findViewById(R.id.btnWordOk);
        btnWordStudy=findViewById(R.id.btnWordStudy);
        btnWordTranslate=findViewById(R.id.btnWordTranslate);

       // username=findViewById(R.id.dashUserName);
        wordsTodayCount=findViewById(R.id.dashWordsTodayCount);
        wordsTodayBadCount=findViewById(R.id.dashWordsTodayBadCount);
        word=findViewById(R.id.dashWord);
        transcript=findViewById(R.id.dashTranscript);
        translate=findViewById(R.id.dashTranslate);
        translate.setVisibility(View.INVISIBLE);
        Intent intent =getIntent();
        if(intent.getExtras()!=null){
            System.out.println("Extra="+intent.getExtras());
            String passedUserName=intent.getStringExtra("data");
            //username.setText("Welcom "+passedUserName);
        }

        takeWord();
        btnWordTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Translate");
                //translate.setVisibility(View.VISIBLE);
                translate.setVisibility(translate.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE);

            }
        });
        btnWordOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              System.out.println("Ok");
              sendWord(true);
            }
        });

        btnWordStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Study");
                sendWord(false);
            }
        });

    }

    public void sendWord(Boolean status){

        WordRequest wordRequest=new WordRequest();
        wordRequest.set_id(id);
        //wordRequest.set_id("640fa746d552f3c5a1868767");
        wordRequest.setTrain1(status);

        System.out.println("Ok2");
        //Call<WordResponse> wordResponseCall=ApiClient.getApiService().getWord(wordRequest);
        Call<WordResponse> wordResponseCall=ApiClient.getApiService().sendWord(wordRequest);
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
                            System.out.println("Response="+wordResponse.getWord());
                            word.setText(wordResponse.getWord());
                            transcript.setText(wordResponse.getTranscript());
                            id=wordResponse.getId();
                            translate.setText(wordResponse.getId());
                            wordsTodayCount.setText(wordResponse.getCountWord());
                            wordsTodayBadCount.setText(wordResponse.getCountWordBad());

                            System.out.println("Id="+id);
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

    /////////////////////////////////////
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

                            System.out.println("Response="+wordResponse.getWord());
                            word.setText(wordResponse.getWord());
                            id=wordResponse.getId();
                            transcript.setText(wordResponse.getTranscript());
                            translate.setText(wordResponse.getId());
                            System.out.println("Id="+id);

                            wordsTodayCount.setText(wordResponse.getCountWord());
                            wordsTodayBadCount.setText(wordResponse.getCountWordBad());
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