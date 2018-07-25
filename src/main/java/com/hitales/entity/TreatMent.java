package com.hitales.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

/**
 * Created by zhubo on 2018/7/23.
 */
@Data
public class TreatMent {

    @Id
    private String id;

    private String name;

    private String diease;

    private ArrayList<String> elements;

}
