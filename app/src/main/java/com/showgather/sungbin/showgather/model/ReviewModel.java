package com.showgather.sungbin.showgather.model;

public class ReviewModel {
    private String title;
    private String content;
    private String time;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setContext(String content) {
        this.content = content;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTitle(){return title;}
    public String getContent(){return content;}
    public String getTIme(){return time;}
}
