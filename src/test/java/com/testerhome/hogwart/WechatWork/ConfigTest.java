package com.testerhome.hogwart.WechatWork;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.*;

class ConfigTest {

    @Test
    void getToken() {
        System.out.println(Config.getToken());
        assertThat(Config.getToken(), not(equalTo("")));
    }

    @Test
    void load(){
        Config.load();
        System.out.println(Config.getInstance().username);

        assertThat(Config.getInstance().contactSecret, not(equalTo("")));
        assertThat(Config.getInstance().username, equalTo("seveniruby"));
    }

    @Test
    void export(){
        System.out.println(Config.export());
    }
}