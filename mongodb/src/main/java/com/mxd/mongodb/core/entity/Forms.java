package com.mxd.mongodb.core.entity;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author mxd
 * @Date 2022/10/25 15:48
 * @Version 1.0
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Forms {

    private String id;
    private String userId;
    private String classId;
    private String applicationId;
    private JSONObject form;
}
