package com.hackathon.fries.myclass.adapter;

import java.util.ArrayList;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class ItemTimeLine {
    private String name;
    private String ava;
    private String content;
    private int like;
    private boolean isConfirmByTeacher;
    private ArrayList<ItemComment> itemComments = new ArrayList<>();

    public ItemTimeLine(String name, String ava, String content, int like, boolean isConfirmByTeacher) {
        this.name = name;
        this.ava = ava;
        this.content = content;
        this.like = like;
        this.isConfirmByTeacher = isConfirmByTeacher;
    }

    public String getName() {
        return name;
    }

    public String getAva() {
        return ava;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public boolean isConfirmByTeacher() {
        return isConfirmByTeacher;
    }

    public void setIsConfirmByTeacher(boolean isConfirmByTeacher) {
        this.isConfirmByTeacher = isConfirmByTeacher;
    }

    public ArrayList<ItemComment> getItemComments() {
        return itemComments;
    }

    public void setItemComments(ArrayList<ItemComment> itemComments) {
        this.itemComments = itemComments;
    }
}
