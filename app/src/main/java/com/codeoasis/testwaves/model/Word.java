package com.codeoasis.testwaves.model;

/**
 * Created by USER on 8/10/2016.) yan
 */
public class Word {

    private String value;
    private int difficulty;






    public Word(String value,Integer difficulty) {
        this.difficulty = difficulty;

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }




    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
