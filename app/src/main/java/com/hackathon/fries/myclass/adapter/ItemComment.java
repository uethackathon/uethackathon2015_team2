package com.hackathon.fries.myclass.adapter;

/**
 * Created by TooNies1810 on 11/21/15.
 */
public class ItemComment {
    private String name;
    private String avaUrl;
    private String content;
    private boolean isVote;

    public ItemComment(String name, String avaUrl, String content, boolean isVote) {
        this.name = name;
        this.avaUrl = avaUrl;
        this.content = content;
        this.isVote = isVote;
    }

    public String getName() {
        return name;
    }

    public String getAvaUrl() {
        return avaUrl;
    }

    public String getContent() {
        return content;
    }

    public boolean isVote() {
        return isVote;
    }
}