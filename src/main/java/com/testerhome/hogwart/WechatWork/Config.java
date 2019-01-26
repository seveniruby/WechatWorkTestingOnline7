package com.testerhome.hogwart.WechatWork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Config {
    public String secret="8KEGhmql2Tj9stLV14mwCCR6POfphQpcOuqYQitrAMo";
    public String corpid="wwd6da61649bd66fea";
    public String agentid="1000004";
    public String username="";
    public String password="";
    public String contactSecret="C7uGOrNyxWWzwBsUyWEbLQdOqoWPz4hNvxj9RIFv-4U";
    public String token="";

    private static Config config;
    public static Config getInstance(){
        if(config==null){
            load();
        }
        return config;
    }

    public static String getToken(){
        if(getInstance().token.equals("")){
            //todo: get token
            getInstance().token=given().queryParam("corpid", Config.getInstance().corpid)
                    .queryParam("corpsecret", Config.getInstance().contactSecret)
            .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
            .then().statusCode(200)
            .extract().path("access_token");
        }
        return getInstance().token;
    }

    public static Config load(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        Config config = null;
        try {
            //config=  mapper.readValue(new File(), Config.class);
            config = mapper.readValue(Config.class.getResourceAsStream(path), Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    public static Config load(){
        config=load("/conf/wechat.yaml");
        return config;

    }

    public static String export(){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.writeValueAsString(getInstance());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
