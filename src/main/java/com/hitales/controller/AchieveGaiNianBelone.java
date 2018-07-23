package com.hitales.controller;

import com.hitales.Repository.GaiNianBeloneRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.GaiNianBeloneEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */


public class AchieveGaiNianBelone {

    @Autowired
    private GaiNianBeloneRepository achieveGaiNianBeloneRepository;


    //生成属于表
    private Map<String,ArrayList<String>> findAll(){

        Map<String,ArrayList<String>> GaiNiansBelones =  new HashMap<>();

        ArrayList<String> values = new ArrayList<>();

        List<GaiNianBeloneEntity> origins = achieveGaiNianBeloneRepository.findAll();

        for(GaiNianBeloneEntity object: origins){

            GaiNiansBelones.put(object.getConcept(),object.getBelongs());
        }
        return  GaiNiansBelones;
    }


    //上传概念属于表
    public void WriteBeloneExcel(){
        WriteExcel.writeExcel(findAll(),"概念属于表");
    }
}