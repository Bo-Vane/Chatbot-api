package com.bo.chatbot.api.domain.zsxq;

import com.bo.chatbot.api.domain.zsxq.aggregates.QuestionAggregates;

import java.io.IOException;

public interface IZsxqApi {

    QuestionAggregates queryQuestion(String groupId, String cookie) throws IOException;

    boolean answer(String groupId,String cookie,String topicId,String text) throws IOException;
}
