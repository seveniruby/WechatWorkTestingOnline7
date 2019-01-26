package com.testerhome.hogwart.WechatWork.AddressBook;

import com.testerhome.hogwart.WechatWork.Config;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Department {

    public Response create(DepartmentData departmentData){
        return given().contentType(ContentType.JSON)
                .queryParam("access_token", Config.getToken())
                .body(departmentData)
        .when().log().all().post("https://qyapi.weixin.qq.com/cgi-bin/department/create")
        .then().log().all().extract().response();

    }
    public Response list(String id){
        return given()
                .queryParam("access_token", Config.getInstance().getToken())
                .queryParam("id", id)
        .when()
                .log().all().get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
        .then().log().all().extract().response();

    }

    public Response delete(String id){
        return given()
                .queryParam("access_token", Config.getToken())
                .queryParam("id", id)
        .when().log().all().get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
        .then().log().all().extract().response();
    }
}
