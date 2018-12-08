package com.cmps121.sam.androidfirebasedbrealtimenew;

public class Post {
    private String title;
    private String content;
    private int upVotes;

    public Post(){

    }

    public Post(String title, String content, int upVotes){
        this.title = title;
        this.content = content;
        this.upVotes = upVotes;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public int getUpVotes(){
        return upVotes;
    }
    public void setUpVotes(int upVotes){
        this.upVotes = upVotes;
    }

}
