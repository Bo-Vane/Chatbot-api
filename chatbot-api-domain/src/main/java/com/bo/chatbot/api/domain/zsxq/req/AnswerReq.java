package com.bo.chatbot.api.domain.zsxq.req;

/**
 * @author bovane
 * @description 回答问题的请求实体
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
public class AnswerReq {
    private Req_data req_data;

    public AnswerReq(Req_data req_data) {
        this.req_data = req_data;
    }

    public Req_data getReq_data() {
        return req_data;
    }

    public void setReq_data(Req_data req_data) {
        this.req_data = req_data;
    }
}
