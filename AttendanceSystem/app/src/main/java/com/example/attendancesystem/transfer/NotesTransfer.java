package com.example.attendancesystem.transfer;

public class NotesTransfer {

    private String notes , title, creation_date, user_id;
    private Integer id;

    public NotesTransfer(String notes, String title, String creation_date, String user_id, Integer id) {
        this.notes = notes;
        this.title = title;
        this.creation_date = creation_date;
        this.user_id = user_id;
        this.id = id;
    }

    public NotesTransfer() {
    }

    public String getNotes() {
        return notes;
    }

    public String getTitle() {
        return title;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public Integer getId() {
        return id;
    }
}
