package com.bo.chatbot.api.test;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;

/**
 * @author bovane
 * @description 单元测试
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
public class ApiTest {

    @Test
    public void queryQuestion() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/88885181481252/topics?scope=by_owner&count=20");
        get.addHeader("cookie","zsxq_access_token=9750B5EF-E8FF-7F48-E71B-2D9DBABAEA6C_8E14A873E2EF9FAE; zsxqsessionid=80472eabccfc60befb92ac89af68293f; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415881842415518%22%2C%22first_id%22%3A%2218ba7a8fa3f1061-096f99bd8f349b-26031151-921600-18ba7a8fa4010f5%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThiYTdhOGZhM2YxMDYxLTA5NmY5OWJkOGYzNDliLTI2MDMxMTUxLTkyMTYwMC0xOGJhN2E4ZmE0MDEwZjUiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI0MTU4ODE4NDI0MTU1MTgifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415881842415518%22%7D%2C%22%24device_id%22%3A%2218ba7a8fa3f1061-096f99bd8f349b-26031151-921600-18ba7a8fa4010f5%22%7D");
        get.addHeader("Content-Type","application/json; charset=UTF-8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/588541818448524/comments");
        post.addHeader("cookie","zsxq_access_token=9750B5EF-E8FF-7F48-E71B-2D9DBABAEA6C_8E14A873E2EF9FAE; zsxqsessionid=80472eabccfc60befb92ac89af68293f; abtest_env=product; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%22415881842415518%22%2C%22first_id%22%3A%2218ba7a8fa3f1061-096f99bd8f349b-26031151-921600-18ba7a8fa4010f5%22%2C%22props%22%3A%7B%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThiYTdhOGZhM2YxMDYxLTA5NmY5OWJkOGYzNDliLTI2MDMxMTUxLTkyMTYwMC0xOGJhN2E4ZmE0MDEwZjUiLCIkaWRlbnRpdHlfbG9naW5faWQiOiI0MTU4ODE4NDI0MTU1MTgifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%24identity_login_id%22%2C%22value%22%3A%22415881842415518%22%7D%2C%22%24device_id%22%3A%2218ba7a8fa3f1061-096f99bd8f349b-26031151-921600-18ba7a8fa4010f5%22%7D");
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我不会啊\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";

        //设置请求带的实体对象，req_data
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
