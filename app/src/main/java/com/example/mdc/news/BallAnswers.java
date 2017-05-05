package com.example.mdc.news;

/**
 * Created by likhitha on 5/2/2017.
 */

public class BallAnswers {
    private String Answer1;
    private String Answer2;
    private String Answer3;
    private String Answer4;
    private String Ballid;

    public BallAnswers(){
        //this constructor is required
    }

    public BallAnswers(String ballid, String answer1, String answer2, String answer3, String answer4) {
        this.Ballid = ballid;
        this.Answer1 = answer1;
        this.Answer2 = answer2;
        this.Answer3 = answer3;
        this.Answer4 = answer4;

    }

    public String getBallid() {
        return Ballid;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }
    public String getAnswer3() {
        return Answer3;
    }
    public String getAnswer4() {
        return Answer4;
    }
}
