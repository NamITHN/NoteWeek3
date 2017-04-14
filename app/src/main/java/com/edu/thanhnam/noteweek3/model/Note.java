package com.edu.thanhnam.noteweek3.model;

import java.io.Serializable;

/**
 * Created by ThanhNam on 4/12/2017.
 */

public class Note implements Serializable{
    private int id;
    private String title;
    private String time;
    private String content;
    private boolean isSelected;

    public Note() {
    }

    public Note(String title, String time, String content) {

        this.title = title;
        this.time = time;
        this.content = content;
    }

    public Note(int id, String title, String time, String content) {
        this.id = id;
        this.title = title;
        this.time = time;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
