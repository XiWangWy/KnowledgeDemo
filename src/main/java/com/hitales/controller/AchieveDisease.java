package com.hitales.controller;

import com.alibaba.fastjson.JSONObject;
import com.hitales.Repository.DiseaseRepository;
import com.hitales.entity.Disease;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */
public class AchieveDisease {

    @Autowired
    private DiseaseRepository diseaseRepository;

    private ArrayList<ArrayList<JSONObject>> findAll(){

        ArrayList<ArrayList<JSONObject>> datas =  new ArrayList<>();
        List<Disease> origins = diseaseRepository.findAll();

        for(Disease object: origins){
            JSONObject object1 =  new JSONObject();
            String name = object.getName();
            ArrayList<String> elements = object.getElements();
            String condition = object.getCondition();



        }
        return  GaiNiansBelones;
    }
}
