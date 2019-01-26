package com.testerhome.hogwart.WechatWork.AddressBook;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
        Department department=new Department();
        DepartmentData departmentData=new DepartmentData();
        departmentData.name="霍格沃兹测试学院线上第七期";
        department.create(departmentData);

        Response response=department.list("");
        response.then().statusCode(200);
        response.then().body("errcode", equalTo(0));
        response.then().body("department.size()", greaterThanOrEqualTo(0));
        response.then().body("department.find{ it.name == '霍格沃兹测试学院线上第七期'}.name", equalTo(departmentData.name));

    }

    @Test
    void list() {
        Department department=new Department();
        Response response=department.list("");
        response.then().statusCode(200);
        response.then().body("errcode", equalTo(0));
        response.then().body("department.size()", greaterThanOrEqualTo(0));

    }
}