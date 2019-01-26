package com.testerhome.hogwart.WechatWork.AddressBook;

import com.testerhome.hogwart.WechatWork.BaseTestCase;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class BaseDepartmentTestCase extends BaseTestCase {
    static Department department=new Department();
    static DepartmentData departmentData=new DepartmentData();


    @BeforeAll
    static void beforeAllBaseDepartmentTestCase(){
        System.out.println("department data clean");
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
}
