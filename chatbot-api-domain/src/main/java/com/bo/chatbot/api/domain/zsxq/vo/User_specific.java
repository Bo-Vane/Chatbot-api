package com.bo.chatbot.api.domain.zsxq.vo;

public class User_specific
{
    private boolean liked;

    private boolean subscribed;

    private boolean submitted;

    public void setLiked(boolean liked){
        this.liked = liked;
    }
    public boolean getLiked(){
        return this.liked;
    }
    public void setSubscribed(boolean subscribed){
        this.subscribed = subscribed;
    }
    public boolean getSubscribed(){
        return this.subscribed;
    }
    public void setSubmitted(boolean submitted){
        this.submitted = submitted;
    }
    public boolean getSubmitted(){
        return this.submitted;
    }
}
