package com.bo.chatbot.api.domain.zsxq.service;

import com.alibaba.fastjson.JSON;
import com.bo.chatbot.api.domain.zsxq.IZsxqApi;
import com.bo.chatbot.api.domain.zsxq.aggregates.QuestionAggregates;
import com.bo.chatbot.api.domain.zsxq.req.AnswerReq;
import com.bo.chatbot.api.domain.zsxq.req.Req_data;
import com.bo.chatbot.api.domain.zsxq.res.AnswerRes;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZsxqApi implements IZsxqApi {

    //日志信息
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);

    @Override
    public QuestionAggregates queryQuestion(String groupId, String cookie) throws IOException {

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/users/self/favorites?count=30");
        get.addHeader("cookie",cookie);
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("寻找星球问题的结果，groupId:{}  jsonStr:{}",groupId,jsonStr);
            return JSON.parseObject(jsonStr,QuestionAggregates.class);
        }else {
            throw new RuntimeException("Err code:"+ response.getStatusLine().getStatusCode());
        }

    }

    @Override
    public boolean answer(String groupId, String cookie, String topicId, String text) throws IOException{
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/"+topicId+"/comments");
        post.addHeader("cookie",cookie );
        post.addHeader("Content-Type","application/json; charset=UTF-8");
        //告诉他这些东西是从浏览器获取
        post.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36");

//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "    \"text\": \"我也不会啊\\n\",\n" +
//                "    \"image_ids\": [],\n" +
//                "    \"mentioned_user_ids\": []\n" +
//                "  }\n" +
//                "}";

        //设置请求带的实体对象，req_data
        Req_data req_data = new Req_data();
        req_data.setText(text);
        AnswerReq answerReq = new AnswerReq(req_data);
        String paramJson = JSONObject.fromObject(answerReq).toString();

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答星球问题的结果，groupId:{} topicId:{} jsonStr:{}",groupId,topicId,jsonStr);

            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            System.out.println(answerRes);
            return true;
        }else {
            throw new RuntimeException("Err code:"+ response.getStatusLine().getStatusCode());
        }
    }

}

