package com.atguigu.gmall0311.logger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall0311.common.constant.GmallConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoggerController {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    @PostMapping("log")
    public String dolog(@RequestParam("logString") String logString){



        //  补充时间戳 123
        JSONObject jsonObject = JSON.parseObject(logString);

        jsonObject.put("ts",System.currentTimeMillis());

        //写日志 用于离线采集
        String logJSON = jsonObject.toJSONString();
        log.info(logJSON);

        //  发送kafka
        if("startup".equals(jsonObject.getString("type"))){
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_STARTUP,logJSON);
        }else {
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_EVENT,logJSON);
        }


        return "success";
    }
}
