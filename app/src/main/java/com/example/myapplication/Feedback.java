package com.example.myapplication;

public class Feedback {
    private String fid;
    private String feedback;
    private String name;

    public Feedback() {
    }

    public Feedback(String fid, String feedback, String name) {
        this.fid = fid;
        this.feedback = feedback;
        this.name = name;
    }


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
