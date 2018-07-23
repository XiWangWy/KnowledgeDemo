package com.hitales.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * Created by zhubo on 2018/7/23.
 */
@Data
public class Disease {

    @Id
    private String id;

    private String name;

    private ArrayList<String> elements;

    private String condition;

}
