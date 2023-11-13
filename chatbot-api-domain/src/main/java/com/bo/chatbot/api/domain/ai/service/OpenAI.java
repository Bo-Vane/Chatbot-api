package com.bo.chatbot.api.domain.ai.service;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bo.chatbot.api.domain.ai.IOpenAI;
import com.bo.chatbot.api.domain.ai.model.aggregates.AIAnswer;
import com.bo.chatbot.api.domain.ai.model.vo.Choices;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bovane
 * @description chatGLM接口回答问题的服务实现
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
@Service
public class OpenAI implements IOpenAI {
    @Value("${chatGLM-api.api_key}")
    private String api_key;

    @Value("${chatGLM-api.secret}")
    private String secret;

    @Override
    public String doChatGLM(String question) throws IOException {
        Logger logger = LoggerFactory.getLogger(OpenAI.class);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://open.bigmodel.cn/api/paas/v3/model-api/chatglm_turbo/invoke");

        //jwt鉴权
        //设置过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MILLISECOND,200000);

        Map headers = new HashMap();
        headers.put("alg","HS256");
        headers.put("sign_type","SIGN");

        Map payload = new HashMap();
        payload.put("api_key", api_key);
        payload.put("exp",instance.getTime());
        payload.put("timestamp", System.currentTimeMillis());

        String token = JWT.create()
                .withHeader(headers)
                .withPayload(payload)
                .sign(Algorithm.HMAC256(secret));

        //根据making requests中给出的头部信息进行添加，这个Authorization的value的API key需要申请创建
        post.addHeader("Authorization",token);
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        //请求实体json信息
        String paramJson = "{\n" +
                "     \"model\": \"chatglm_turbo\",\n" +
                "     \"prompt\": [{\"role\": \"user\", \"content\": \"" + question + "\"}]\n" +
//                "     \"temperature\": 0.95,\n" +
//                "     \"tcp_p\": 0.7,\n" +
//                "     \"incremental\": true\n" +
                "   }";


        AIAnswer answer = new AIAnswer();
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题返回的整体：{}",jsonStr);
            answer = JSON.parseObject(jsonStr,AIAnswer.class);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
            System.out.println(response.getStatusLine().getReasonPhrase());
            throw new RuntimeException("Err code:" + response.getStatusLine().getStatusCode()
                    + " " + response.getStatusLine().getReasonPhrase());
        }

        StringBuilder finalAnswer = new StringBuilder();

        for (Choices choice : answer.getData().getChoices()) {
            finalAnswer.append(choice.getContent());
            logger.info("chatGLM回答：{}",finalAnswer.toString());
        }

        return finalAnswer.toString();
    }
}
