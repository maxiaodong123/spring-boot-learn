package com.mxd.mongodb;

import com.mxd.mongodb.core.service.document.FormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @Author mxd
 * @Date 2022/10/26 10:57
 * @Version 1.0
 * @Description
 */
@SpringBootTest
public class FormsTest {

    @Autowired
    FormService formService;

    //添加
    @Test
    void insert() {
        formService.insert();
    }

    @Test
    void update() {
        formService.update();
    }

    @Test
    void selectOne() {
        formService.selectOne();
    }

    @Test
    void deleteById() {
        formService.deleteById();
    }


}
