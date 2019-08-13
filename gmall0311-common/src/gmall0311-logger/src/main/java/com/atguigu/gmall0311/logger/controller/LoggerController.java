package com.atguigu.gmall0311.logger.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggerController {

    @PostMapping("log")
    public String dolog(String logString){

        //补充时间戳
        JSONObject jsonObject = JSON.parseObject(logString);

        jsonObject.put("ts", System.currentTimeMillis());

        //写日志   用于离线采集
        String logJson = jsonObject.toJSONString();

        //


        return "success";
    }

}
