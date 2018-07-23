package com.hitales.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;

@Data
public class GaiNian {
    @Id
    private String id;

    private String concept;

    private ArrayList<String> belongs;
    private ArrayList<String> ty;
    private ArrayList<String> oneToOne;
    private ArrayList<String> oneToMany;
}
