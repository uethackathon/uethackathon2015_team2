package com.hackathon.fries.myclass.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by TooNies1810 on 11/20/15.
 */
public class ItemTimeLine {
    private static final String TAG = "ItemTimeLine";
    private String title;

    //name of author post
    private String name;
    private String ava;
    private String content;
    private int like;
    private boolean isConfirmByTeacher;
    private ArrayList<ItemComment> itemComments = new ArrayList<>();

    public ItemTimeLine(String title, String name, String ava, String content, int like, boolean isConfirmByTeacher) {
        this.name = name;
        this.title = title;
        this.ava = ava;
        this.content = content;
        this.like = like;
        this.isConfirmByTeacher = isConfirmByTeacher;

        Log.i(TAG, "name: " + name);
        Log.i(TAG, "title: " + title);
        Log.i(TAG, "ava: " + ava);
        Log.i(TAG, "content: " + content);
        Log.i(TAG, "like: " + like);
        Log.i(TAG, "is confirmed: " + isConfirmByTeacher);
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

    public String getTitle() {
        return title;
    }
}
