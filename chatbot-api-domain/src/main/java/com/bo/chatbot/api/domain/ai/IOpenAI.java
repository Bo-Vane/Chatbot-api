package com.bo.chatbot.api.domain.ai;

import java.io.IOException;

/**
 * @author bovane
 * @description chatGLM接口
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
public interface IOpenAI {
    String doChatGLM(String question) throws IOException;
}