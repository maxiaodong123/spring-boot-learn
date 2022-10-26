package com.mxd.mongodb.core.entity;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author mydlq
 */
@Data
@ToString
@Accessors(chain = true)
public class Status {

    private Integer weight;
    private Integer height;

}
