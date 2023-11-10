package com.bo.chatbot.api.test;

import com.alibaba.fastjson.JSON;
import com.bo.chatbot.api.domain.zsxq.IZsxqApi;
import com.bo.chatbot.api.domain.zsxq.aggregates.QuestionAggregates;
import com.bo.chatbot.api.domain.zsxq.vo.Favorites;
import com.bo.chatbot.api.domain.zsxq.vo.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Test
    public void test_zsxqApi() throws IOException {
        QuestionAggregates questionAggregates = zsxqApi.queryQuestion(groupId, cookie);
        /*第一次测试
        logger.info("测试结果：{}",JSON.toJSONString(questionAggregates));
        System.out.println(groupId);
        */
        for (Favorites favorites : questionAggregates.getResp_data().getFavorites()) {
            //找到topic
            Topic topic = favorites.getTopic();
            String topicId = topic.getTopic_id();
            String text = topic.getTask().getText();
            //输出问题，测试
            logger.info("测试结果，topicId:{} question:{}",topicId,text);

            //回答问题
            logger.info("测试结果：{}",zsxqApi.answer(groupId,cookie,topicId,text));
        }

    }
}
