package com.testerhome.hogwart.WechatWork.AddressBook;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    Department department=new Department();
    DepartmentData departmentData=new DepartmentData();

    @BeforeEach
    void setUp() {
        List<String> array=new ArrayList<String>();
        array.add("霍格沃兹测试学院线上第八期-实战演练");
        array.add("霍格沃兹测试学院线上第八期");

        for(String name : array) {
            departmentData.name =name;
            Integer id = department.list("").path("department.find{it.name=='" + departmentData.name + "'}.id");
            if (id != null) {
                department.delete(id.toString()).then().body("errcode", equalTo(0));
            }
        }
    }

    @Test
    void create() {
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
        Response response=department.list("");
        response.then().statusCode(200);
        response.then().body("errcode", equalTo(0));
        response.then().body("department.size()", greaterThanOrEqualTo(0));

    }


    @Test
    void  delete(){
        DepartmentData departmentData=new DepartmentData();
        departmentData.name="霍格沃兹测试学院线上第八期";
        Integer id=department.list("").path("department.find{it.name=='"+ departmentData.name +"'}.id");
        if(id==null) {
            id = department.create(departmentData).then().extract().path("id");
            department.list("").then().body("department.findAll{it.name=='" + departmentData.name + "'}.size()", equalTo(1));
        }
        department.delete(id.toString()).then().body("errcode", equalTo(0));
        department.list("").then().body("department.findAll{it.name=='"+ departmentData.name + "'}.size()", equalTo(0));
    }

    @Test
    void  deleteWithChild(){
        departmentData.name="霍格沃兹测试学院线上第八期";
        Integer id=department.list("").path("department.find{it.name=='"+ departmentData.name +"'}.id");
        if(id==null) {
            id = department.create(departmentData).then().extract().path("id");
            departmentData.name="霍格沃兹测试学院线上第八期-实战演练";
            departmentData.parentid=id;
            department.create(departmentData);
            department.list("").then().body("department.findAll{it.name=='" + departmentData.name + "'}.size()", equalTo(1));
        }
        department.delete(id.toString()).then().body("errcode", equalTo(60006));
        department.list("").then().body("department.findAll{it.name=='"+ departmentData.name + "'}.size()", equalTo(1));
    }
}