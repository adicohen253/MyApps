package com.example.notepad;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private String title;
    private String body;
    private String timeOfCreation;

    public Note(String title, String body, LocalDateTime timeOfCreation) {
        this.title = title;
        this.body = body;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm dd/MM/yyyy");
        this.timeOfCreation = dtf.format(timeOfCreation);
    }

    public Note(String title, String body, String timeOfCreation)
    {
        this.title = title.replace(String.valueOf(1), "'");
        this.body = body.replace(String.valueOf(1), "'");
        this.timeOfCreation = timeOfCreation;
    }

    public String getTitle() {  
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(String timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }
}
