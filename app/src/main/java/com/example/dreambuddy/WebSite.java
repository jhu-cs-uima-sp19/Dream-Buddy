package com.example.dreambuddy;

public class WebSite {
    private String url;

    private String acronym;

    private String full;

    public WebSite (String theUrl, String theAcc, String theFull){
        url = theUrl;
        acronym = theAcc;
        full = theFull;

    }

    public String getUrl() {
        return url;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getFull() {
        return full;
    }
}
