package com.testerhome.hogwart.WechatWork;

import io.restassured.RestAssured;

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
            config=new Config();
        }
        return config;
    }

    public static String getToken(){
        if(getInstance().token==""){
            //todo: get token

            getInstance().token=given().queryParam("corpid", Config.getInstance().corpid)
                    .queryParam("corpsecret", Config.getInstance().contactSecret)
            .when().get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
            .then().statusCode(200)
            .extract().path("access_token");
        }
        return getInstance().token;
    }
}
