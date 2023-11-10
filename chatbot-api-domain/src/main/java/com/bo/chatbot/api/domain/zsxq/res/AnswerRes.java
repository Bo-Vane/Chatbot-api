package com.bo.chatbot.api.domain.zsxq.res;

/**
 * @author bovane
 * @description 回答问题的结果信息
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
public class AnswerRes {
    //知道是否成功就行，返回的结果里有一个succeed
    private boolean succeed;

    public AnswerRes(boolean succeed) {
        this.succeed = succeed;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }
}
