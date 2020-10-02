package com.example.myapplication;

public class RegisterDB {

    private String email;
    private String fname;
    private  String lname;
    private String pass;
    private String compass;


    public RegisterDB() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {

        return  fname;
    }

    public void setFname (String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCompass() {
        return compass;
    }

    public void setCompass(String compass) {
        this.compass = compass;
    }


    public RegisterDB(String email, String fname, String lname, String pass, String compass) {

        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.pass = pass;
        this.compass = compass;
    }





}
