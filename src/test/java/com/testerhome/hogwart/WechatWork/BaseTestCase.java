package com.testerhome.hogwart.WechatWork;

import org.junit.jupiter.api.BeforeAll;

public class BaseTestCase {
    @BeforeAll
    static void beforeAllBase(){
        System.out.println("user login");
    }
}
