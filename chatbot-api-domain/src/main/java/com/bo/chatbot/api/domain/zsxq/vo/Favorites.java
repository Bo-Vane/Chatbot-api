package com.bo.chatbot.api.domain.zsxq.vo;

public class Favorites
{
    private String favor_time;

    private Topic topic;

    public void setFavor_time(String favor_time){
        this.favor_time = favor_time;
    }
    public String getFavor_time(){
        return this.favor_time;
    }
    public void setTopic(Topic topic){
        this.topic = topic;
    }
    public Topic getTopic(){
        return this.topic;
    }
}