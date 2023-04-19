package com.example.evocab;

import java.util.Date;

public class WordResponse {

    private String id;
    private String word;
    private String translate;

    private Date trainDate;

    private String sound;

    private Boolean train1;
    private String transcript;

    private String countWord;

    private String countWordBad;

    public String getCountWord() {
        return countWord;
    }

    public void setCountWord(String countWord) {
        this.countWord = countWord;
    }

    public String getCountWordBad() {
        return countWordBad;
    }

    public void setCountWordBad(String countWordBad) {
        this.countWordBad = countWordBad;
    }

    public Date getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(Date trainDate) {
        this.trainDate = trainDate;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Boolean getTrain1() {
        return train1;
    }

    public void setTrain1(Boolean train1) {
        this.train1 = train1;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }


    public String getId() {
        return id;
    }

    public void setId(String word_id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}

