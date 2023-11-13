package com.bo.chatbot.api.domain.ai.model.aggregates;

import com.bo.chatbot.api.domain.ai.model.vo.Data;

/**
 * @author bovane
 * @description chatGLM接口回答问题的返回实体
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
public class AIAnswer {
    private int code;

    private String msg;

    private Data data;

    private boolean success;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return this.msg;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
}
