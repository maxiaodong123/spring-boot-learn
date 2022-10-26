package com.mxd.mongodb.core.service.document.impl;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mxd.mongodb.core.entity.Forms;
import com.mxd.mongodb.core.service.document.FormService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author mxd
 * @Date 2022/10/25 13:35
 * @Version 1.0
 * @Description
 */
@Service
@Slf4j
public class FormServiceImpl implements FormService {

    @Resource
    MongoTemplate mongoTemplate;

    @Override
    public void insert() {

//        String classStr = "{\n" +
//                "\t\"classId\": \"123123123\",\n" +
//                "\t\"applicationId\": \"c27dbb31-20e9-40a2-94d9-e6a2df661604\",\n" +
//                "\t\"userId\": \"123\",\n" +
//                "\t\"form\": {\n" +
//                "\t\t\"subElementNum\": 1,\n" +
//                "\t\t\"resourceTask\": {\n" +
//                "\t\t\t\"task\": \"DEPLOY\",\n" +
//                "\t\t\t\"taskResult\": \"DEPLOY_TIMEOUT\"\n" +
//                "\t\t},\n" +
//                "\t\t\"name\": \"shawn-mysql-1\",\n" +
//                "\t\t\"totalElementNum\": 1,\n" +
//                "\t\t\"elementInstanceBOs\": [{\n" +
//                "\t\t\t\"imageVersion\": \"5.6\",\n" +
//                "\t\t\t\"imageName\": \"172.16.71.199/common/mysql\",\n" +
//                "\t\t\t\"name\": \"mysql\",\n" +
//                "\t\t\t\"namespace\": \"shawn\",\n" +
//                "\t\t\t\"intranetAccessURL\": [{\n" +
//                "\t\t\t\t\"proxyPort\": 3306,\n" +
//                "\t\t\t\t\"address\": \"shawn-mysql-1-mysql.shawn\",\n" +
//                "\t\t\t\t\"_id\": null,\n" +
//                "\t\t\t\t\"targetPort\": 3306\n" +
//                "\t\t\t}],\n" +
//                "\t\t\t\"_id\": \"1347580a-02a1-41a6-9d29-c78850f948b5\",\n" +
//                "\t\t\t\"totalCellNum\": 1,\n" +
//                "\t\t\t\"runningCellNum\": 0,\n" +
//                "\t\t\t\"internetAccessURL\": [{\n" +
//                "\t\t\t\t\"address\": \"172.16.71.200\",\n" +
//                "\t\t\t\t\"_id\": null,\n" +
//                "\t\t\t\t\"targetPort\": 3306\n" +
//                "\t\t\t}],\n" +
//                "\t\t\t\"slug\": \"shawn-shawn-mysql-1-mysql\",\n" +
//                "\t\t\t\"status\": \"STARTING\"\n" +
//                "\t\t}],\n" +
//                "\t\t\"totalCellNum\": 1,\n" +
//                "\t\t\"version\": \"1\",\n" +
//                "\t\t\"status\": \"UNHEALTHY\"\n" +
//                "\t}\n" +
//                "}";
//        JSONObject parseObject = JSONUtil.parseObj(classStr);
//        System.out.println(parseObject);
//
//        mongoTemplate.insert(parseObject,"form");


        String classStr = "{\n" +
                "\t\"subElementNum\": 1,\n" +
                "\t\"resourceTask\": {\n" +
                "\t\t\"task\": \"DEPLOY\",\n" +
                "\t\t\"taskResult\": \"DEPLOY_TIMEOUT\"\n" +
                "\t},\n" +
                "\t\"name\": \"shawn-mysql-1\",\n" +
                "\t\"totalElementNum\": 1,\n" +
                "\t\"elementInstanceBOs\": [{\n" +
                "\t\t\"imageVersion\": \"5.6\",\n" +
                "\t\t\"imageName\": \"172.16.71.199/common/mysql\",\n" +
                "\t\t\"name\": \"mysql\",\n" +
                "\t\t\"namespace\": \"shawn\",\n" +
                "\t\t\"intranetAccessURL\": [{\n" +
                "\t\t\t\"proxyPort\": 3306,\n" +
                "\t\t\t\"address\": \"shawn-mysql-1-mysql.shawn\",\n" +
                "\t\t\t\"_id\": null,\n" +
                "\t\t\t\"targetPort\": 3306\n" +
                "\t\t}],\n" +
                "\t\t\"_id\": \"1347580a-02a1-41a6-9d29-c78850f948b5\",\n" +
                "\t\t\"totalCellNum\": 1,\n" +
                "\t\t\"runningCellNum\": 0,\n" +
                "\t\t\"internetAccessURL\": [{\n" +
                "\t\t\t\"address\": \"172.16.71.200\",\n" +
                "\t\t\t\"_id\": null,\n" +
                "\t\t\t\"targetPort\": 3306\n" +
                "\t\t}],\n" +
                "\t\t\"slug\": \"shawn-shawn-mysql-1-mysql\",\n" +
                "\t\t\"status\": \"STARTING\"\n" +
                "\t}],\n" +
                "\t\"totalCellNum\": 1,\n" +
                "\t\"version\": \"1\",\n" +
                "\t\"status\": \"UNHEALTHY\"\n" +
                "}";
        JSONObject parseObject = JSONUtil.parseObj(classStr);
        System.out.println(parseObject);
        Forms form = new Forms();
        form.setForm(parseObject);
        form.setApplicationId("123");
        form.setUserId("111111111");
        form.setClassId("9999999");
        Forms insert = mongoTemplate.insert(form);
        log.info("存储的信息为：{}", insert);
//        Form save = mongoTemplate.save(form);
//        log.info("存储的信息为：{}", save);

//        Criteria criteria = Criteria.where("id").is("123");
//        // 创建查询对象，然后将条件对象添加到其中
//        Query query = new Query(criteria);
//        Update update = new Update().set("classId", "123").set("form",parseObject).set("applicationId","111").set("userId","123");
//        UpdateResult result = mongoTemplate.upsert(query, update, "form");
//        log.info("存储的信息为：{}", result);
    }

    @Override
    public void update() {
        // 创建条件对象
        Criteria criteria = Criteria.where("_id").is("6357ae2b923d927054e159c1");
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        // 创建更新对象,并设置更新的内容
//        JSONObject parseObject = JSONUtil.parseObj("[{'studentId':'3','name':'zhangsan'}]");

        String classStr = "{\n" +
                "\t\"subElementNum\": 1,\n" +
                "\t\"resourceTask\": {\n" +
                "\t\t\"task\": \"DEPLOY\",\n" +
                "\t\t\"taskResult\": \"DEPLOY_TIMEOUT\"\n" +
                "\t},\n" +
                "\t\"name\": \"shawn-mysql-1\",\n" +
                "\t\"totalElementNum\": 1,\n" +
                "\t\"elementInstanceBOs\": [{\n" +
                "\t\t\"imageVersion\": \"5.6\",\n" +
                "\t\t\"imageName\": \"172.16.71.199/common/mysql\",\n" +
                "\t\t\"name\": \"mysql\",\n" +
                "\t\t\"namespace\": \"shawn\",\n" +
                "\t\t\"intranetAccessURL\": [{\n" +
                "\t\t\t\"proxyPort\": 3306,\n" +
                "\t\t\t\"address\": \"shawn-mysql-1-mysql.shawn\",\n" +
                "\t\t\t\"_id\": null,\n" +
                "\t\t\t\"targetPort\": 3306\n" +
                "\t\t}],\n" +
                "\t\t\"_id\": \"1347580a-02a1-41a6-9d29-c78850f948b5\",\n" +
                "\t\t\"totalCellNum\": 1,\n" +
                "\t\t\"runningCellNum\": 0,\n" +
                "\t\t\"internetAccessURL\": [{\n" +
                "\t\t\t\"address\": \"172.16.71.200\",\n" +
                "\t\t\t\"_id\": null,\n" +
                "\t\t\t\"targetPort\": 3306\n" +
                "\t\t}],\n" +
                "\t\t\"slug\": \"shawn-shawn-mysql-1-mysql\",\n" +
                "\t\t\"status\": \"STARTING\"\n" +
                "\t}],\n" +
                "\t\"totalCellNum\": 1,\n" +
                "\t\"version\": \"1\",\n" +
                "\t\"status\": \"UNHEALTHY\"\n" +
                "}";
        JSONObject parseObject = JSONUtil.parseObj(classStr);
//
        Update update = new Update().set("classId", "123").set("form",parseObject).set("applicationId","111").set("userId","123");
////        DBObject update1 = new BasicDBObject("$set", JSON.parse(classStr));
//        // 执行更新，如果没有找到匹配查询的文档，则创建并插入一个新文档
//
        UpdateResult result = mongoTemplate.upsert(query, update, "form");

//        boolean b = this.updateById("63578aff474b4b4878b7d23f", parseObject);
        // 输出结果信息
        String resultInfo = "匹配到" + result.getMatchedCount() + "条数据,对第一条数据进行了更改";
        System.out.println(resultInfo);
    }

    @Override
    public void selectOne() {
        String id = "63578aff474b4b4878b7d23f";
        Forms form = mongoTemplate.findById(id, Forms.class);

        log.info("form信息：{}", form);
    }

    @Override
    public void deleteById() {
        // 创建条件对象
        Criteria criteria = Criteria.where("_id").is("6357ae2b923d927054e159c1");
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(criteria);
        DeleteResult form = mongoTemplate.remove(query, "form");
        log.info("form信息：{}", form);
    }
}
