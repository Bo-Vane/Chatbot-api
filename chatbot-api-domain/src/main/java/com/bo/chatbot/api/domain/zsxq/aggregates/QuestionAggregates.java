package com.bo.chatbot.api.domain.zsxq.aggregates;

import com.bo.chatbot.api.domain.zsxq.res.Resp_data;

/**
 * @author bovane
 * @description 未回答问题的聚合信息
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
public class QuestionAggregates {

    private boolean succeeded;
    private Resp_data resp_data;

    public QuestionAggregates(boolean succeeded, Resp_data resp_data) {
        this.succeeded = succeeded;
        this.resp_data = resp_data;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Resp_data getResp_data() {
        return resp_data;
    }

    public void setResp_data(Resp_data resp_data) {
        this.resp_data = resp_data;
    }
}
