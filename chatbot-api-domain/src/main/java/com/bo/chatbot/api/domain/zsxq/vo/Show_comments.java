package com.bo.chatbot.api.domain.zsxq.vo;

public class Show_comments
{
    private String  comment_id;

    private String create_time;

    private Owner owner;

    private String text;

    private int likes_count;

    private int rewards_count;

    private boolean sticky;

    public void setComment_id(String comment_id){
        this.comment_id = comment_id;
    }
    public String getComment_id(){
        return this.comment_id;
    }
    public void setCreate_time(String create_time){
        this.create_time = create_time;
    }
    public String getCreate_time(){
        return this.create_time;
    }
    public void setOwner(Owner owner){
        this.owner = owner;
    }
    public Owner getOwner(){
        return this.owner;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }
    public void setLikes_count(int likes_count){
        this.likes_count = likes_count;
    }
    public int getLikes_count(){
        return this.likes_count;
    }
    public void setRewards_count(int rewards_count){
        this.rewards_count = rewards_count;
    }
    public int getRewards_count(){
        return this.rewards_count;
    }
    public void setSticky(boolean sticky){
        this.sticky = sticky;
    }
    public boolean getSticky(){
        return this.sticky;
    }
}
