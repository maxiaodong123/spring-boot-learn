package com.mxd.mongodb.core.entity;

import lombok.Data;

/**
 * @Author mxd
 * @Date 2022/10/26 10:09
 * @Version 1.0
 * @Description
 */
@Data
public class FormsDto {

    private String id;
    private String userId;
    private String classId;
    private String applicationId;
    private String form;
}
