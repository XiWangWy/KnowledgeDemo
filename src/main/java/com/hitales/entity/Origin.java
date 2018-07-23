package com.hitales.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * Created by zhubo on 2018/7/23.
 */
@Data
public class Origin {
    @Id
    private String id;

    private String RID;

    private String EID;

    private String entityType;

    private String fullInfo;

    private String field;

    private String fieldContent;

    private String TYConcept;

}
