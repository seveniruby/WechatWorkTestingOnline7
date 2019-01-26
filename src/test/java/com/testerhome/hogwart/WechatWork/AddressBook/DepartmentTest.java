package com.testerhome.hogwart.WechatWork.AddressBook;

import com.testerhome.hogwart.WechatWork.BaseTestCase;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest extends BaseDepartmentTestCase {

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

    @ParameterizedTest
    @ValueSource(strings = {
            "霍格沃兹测试学院线上第七期",
            "霍格沃兹测试学院线上第八期",
            "霍格沃兹测试学院线上第九期",
            "a",
            "1",
            "@"
    })
    void create(String name) {
        departmentData.name=name;
        department.create(departmentData);

        Response response=department.list("");
        response.then().statusCode(200);
        response.then().body("errcode", equalTo(0));
        response.then().body("department.size()", greaterThanOrEqualTo(0));
        response.then().body("department.find{ it.name == '"+ departmentData.name + "'}.name", equalTo(departmentData.name));

    }

    @ParameterizedTest
    @CsvFileSource(resources="/data/departmentID.csv")
    void list(String id, Integer size) {
        System.out.println("id="+id);
        System.out.println("size="+size);
        Response response=department.list(id);
        response.then().statusCode(200);
        response.then().body("errcode", equalTo(0));
        response.then().body("department.size()", greaterThanOrEqualTo(size));

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