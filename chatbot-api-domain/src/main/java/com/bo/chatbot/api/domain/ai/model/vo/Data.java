package com.bo.chatbot.api.domain.ai.model.vo;

import java.util.List;

public class Data
{
    private String request_id;

    private String task_id;

    private String task_status;

    private List<Choices> choices;

    private Usage usage;

    public void setRequest_id(String request_id){
        this.request_id = request_id;
    }
    public String getRequest_id(){
        return this.request_id;
    }
    public void setTask_id(String task_id){
        this.task_id = task_id;
    }
    public String getTask_id(){
        return this.task_id;
    }
    public void setTask_status(String task_status){
        this.task_status = task_status;
    }
    public String getTask_status(){
        return this.task_status;
    }
    public void setChoices(List<Choices> choices){
        this.choices = choices;
    }
    public List<Choices> getChoices(){
        return this.choices;
    }
    public void setUsage(Usage usage){
        this.usage = usage;
    }
    public Usage getUsage(){
        return this.usage;
    }
}