package com.bo.chatbot.api.application.job;

import com.bo.chatbot.api.domain.ai.IOpenAI;
import com.bo.chatbot.api.domain.zsxq.IZsxqApi;
import com.bo.chatbot.api.domain.zsxq.aggregates.QuestionAggregates;
import com.bo.chatbot.api.domain.zsxq.vo.Favorites;
import com.bo.chatbot.api.domain.zsxq.vo.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @author bovane
 * @description 问题任务job
 * @github <a href="https://github.com/Bo-Vane/Chatbot-api">...</a>
 * @Copyright 2023
 */
@EnableScheduling
@Configuration
public class ChatbotSchedule {
    private Logger logger = LoggerFactory.getLogger(ChatbotSchedule.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Resource
    private IOpenAI openAI;



    //一分钟轮询一次，表达式网站：cron.qqe2.com
    @Scheduled(cron = "0 0/1 * * * * ? ")
    public void run(){
        //防止规律轮询
        try{
            if (new Random().nextBoolean()){
                logger.info("随机打烊中...");
                return;
            }

            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
            if (hour > 22 || hour < 7){
                logger.info("打烊时间不工作");
                return;
            }

            //首先检索问题：
            QuestionAggregates questionAggregates = zsxqApi.queryQuestion(groupId, cookie);
            String topicId = null;
            String question = null;
            Topic topic;
            for (Favorites favorites : questionAggregates.getResp_data().getFavorites()) {
                //找到topic
                topic = favorites.getTopic();
                topicId = topic.getTopic_id();
                question = topic.getTask().getText();
                //输出问题，测试
                logger.info("测试结果，topicId:{} question:{}", topicId, question);
            }
            if (question == null){
                logger.info("本次检索未查询到待回答问题");
                return;
            }

            //回答问题
            String finalAnswer  = openAI.doChatGLM(question);

            //问题回复到知识星球
            boolean status = zsxqApi.answer(groupId, cookie, topicId, finalAnswer);
            logger.info("编号:{} 问题:{} 状态:{} 回复:{}", topicId, question, status, finalAnswer);

        }catch (Exception e){
            logger.info("自动回答问题异常",e);

        }
    }
}
