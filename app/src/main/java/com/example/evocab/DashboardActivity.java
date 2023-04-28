package com.example.evocab;
import android.speech.tts.TextToSpeech;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {
    String dateWithoutTime;
    String soundURL;
    MediaPlayer mediaPlayer;
    Button btnWordOk;
    Button btnWordStudy;
    Button btnWordTranslate;
    ImageButton btnSound;
    Button btnSpeech;
    Button btnPrev;
    Button btnNext;

    TextView wordsTodayCount;
    TextView wordsTodayBadCount;

    TextView username;
    TextView word;

    TextView transcript;
    TextView translate;

    TextView trainDate;
    String id;
    TextToSpeech textToSpeech;

    LinearProgressIndicator lpi;
    CircularProgressIndicator cpi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnWordOk=findViewById(R.id.btnWordOk);
        btnWordStudy=findViewById(R.id.btnWordStudy);
        btnWordTranslate=findViewById(R.id.btnWordTranslate);
        btnSound=findViewById(R.id.btnSound);
        btnSpeech=findViewById(R.id.btnSpeech);
        btnPrev=findViewById(R.id.btnPrev);
        btnNext=findViewById(R.id.btnNext);

       // username=findViewById(R.id.dashUserName);
        wordsTodayCount=findViewById(R.id.dashWordsTodayCount);
        wordsTodayBadCount=findViewById(R.id.dashWordsTodayBadCount);
        word=findViewById(R.id.dashWord);
        transcript=findViewById(R.id.dashTranscript);
        translate=findViewById(R.id.dashTranslate);
        translate.setVisibility(View.INVISIBLE);
        trainDate=findViewById(R.id.dashTrainDate);
        lpi=findViewById(R.id.progressLineInd);
        cpi=findViewById(R.id.progressCircleInd);
        //lpi.setIndeterminate(true);
        lpi.setVisibility(View.GONE);
        //cpi.setIndeterminate(true);

        Intent intent =getIntent();

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        if(intent.getExtras()!=null){
            String passedUserName=intent.getStringExtra("data");
            //username.setText("Welcom "+passedUserName);
        }

        takeWord(false, false, null);
        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("testLogs","Speech");
                playSpeech();

            }
        });
        btnWordTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("testLog","Translate");
                //translate.setVisibility(View.VISIBLE);
                translate.setVisibility(translate.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE);
                btnWordTranslate.setText(translate.getVisibility()==View.VISIBLE ? "HIDE TRANSLATE" : "SHOW TRANSLATE");
            }
        });
        btnWordOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 takeWord(true, true, null);
            }
        });

        btnWordStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 takeWord(true,false, null);
            }
        });

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  playSound();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  takeWord(false,false, "prev");
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  takeWord(false,false, "next");
            }
        });
    }
    public void playSpeech(){
            textToSpeech.speak((String) word.getText(),TextToSpeech.QUEUE_FLUSH,null);
    }
    public void playSound() {
        if(soundURL==null) return;
        if(mediaPlayer==null) {
            try {
                Log.d("testLog","Try play sound: "+soundURL);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(soundURL.replaceAll(" ", "%20"));
                //mediaPlayer.prepare();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                    @Override
                    public void onPrepared(MediaPlayer arg0){
                        arg0.start();//Запускаем на воспроизведение
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        stopPlayer();
                    }
                });
                mediaPlayer.prepareAsync();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private void stopPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
    public void takeWord( Boolean action, Boolean status, String nav){
        cpi.setIndeterminate(true);
        cpi.setVisibility(View.VISIBLE);
        //lpi.setIndeterminate(true);
        Call<WordResponse> wordResponseCall =null;
        if(action) {
            WordRequest wordRequest = new WordRequest();
            wordRequest.set_id(id);
            //wordRequest.set_id("640fa746d552f3c5a1868767");
            wordRequest.setTrain1(status);

            Log.d("testLogs","Ok2");
            //Call<WordResponse> wordResponseCall=ApiClient.getApiService().getWord(wordRequest);
             wordResponseCall = ApiClient.getApiService().sendWord(wordRequest);
        }else{

             wordResponseCall=ApiClient.getApiService().getWord(nav,id);
        }
        Log.d("testLogs","Ok3");
        wordResponseCall.enqueue(new Callback<WordResponse>() {
            @Override
            public void onResponse(Call<WordResponse> call, Response<WordResponse> response) {
                if (response.isSuccessful()){
                    Log.d("testLogs","Successful");
                    //Toast.makeText(DashboardActivity.this, " Successful",Toast.LENGTH_LONG).show();
                    //  token=response.body().getToken();
                    WordResponse wordResponse= response.body();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //lpi.setIndeterminate(false);
                            cpi.setIndeterminate(false);
                            cpi.setVisibility(View.INVISIBLE);
                            translate.setVisibility(View.GONE);
                            btnWordTranslate.setText("SHOW TRANSLATE");
                            Log.d("testLog","Response="+wordResponse.getWord());
                            word.setText(wordResponse.getWord());
                            if(wordResponse.getTrain1()==true) {
                                word.setCompoundDrawablesWithIntrinsicBounds(R.drawable.green_circle, 0, 0, 0);
                            }else{
                                word.setCompoundDrawablesWithIntrinsicBounds(R.drawable.red_circle, 0, 0, 0);
                            }
                            transcript.setText("["+wordResponse.getTranscript()+"] ");
                            id=wordResponse.getId();
                            translate.setText(wordResponse.getTranslate());

                            Log.d("testLogs","trainDate="+wordResponse.getTrainDate());
                            //SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                            dateWithoutTime = sdf.format(wordResponse.getTrainDate());
//                            try {
//                                Date todayWithZeroTime = sdf.parse(sdf.format(wordResponse.getTrainDate()));
//                                System.out.println("fff="+todayWithZeroTime);
//                            } catch (ParseException e) {
//                                throw new RuntimeException(e);
//                            }
                            Log.d("testLogs","trainDate2="+dateWithoutTime);
                            trainDate.setText(dateWithoutTime);
                            wordsTodayCount.setText(wordResponse.getCountWord());
                            wordsTodayBadCount.setText(wordResponse.getCountWordBad());
                            soundURL=wordResponse.getSound().substring(7);
                            soundURL=soundURL.substring(0,soundURL.length()-1);

                            //  startActivity(new Intent(DashboardActivity.this,DashboardActivity.class).putExtra("data",loginResponse.getEmail()));
                        }
                    },700);
                }else{
                    Log.d("testLogs","Failed....");
                    Toast.makeText(DashboardActivity.this, " Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<WordResponse> call, Throwable t) {
                Log.d("testLogs","Trouble "+t.getLocalizedMessage());
                Toast.makeText(DashboardActivity.this, "Trouble "+t.getLocalizedMessage() ,Toast.LENGTH_LONG).show();
            }
        });
    }


}