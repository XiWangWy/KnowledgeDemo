package com.hitales.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * Created by zhubo on 2018/7/23.
 */
@Data
public class Disease {

    private String name;

    private ArrayList<String> elements;

    private String condition;

}
