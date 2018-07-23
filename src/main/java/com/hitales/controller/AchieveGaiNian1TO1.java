package com.hitales.controller;

import com.hitales.Repository.GaiNian1TO1Repository;
import com.hitales.Repository.GaiNianTYRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.GaiNian1TO1Entity;
import com.hitales.entity.GaiNianTYEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */


public class AchieveGaiNian1TO1 {

    @Autowired
    private GaiNian1TO1Repository gaiNian1TO1Repository;


    //生成属于表
    private Map<String,ArrayList<String>> findAll(){

        Map<String,ArrayList<String>> GaiNiansBelones =  new HashMap<>();

        ArrayList<String> values = new ArrayList<>();

        List<GaiNian1TO1Entity> origins = gaiNian1TO1Repository.findAll();

        for(GaiNian1TO1Entity object: origins){

            GaiNiansBelones.put(object.getConcept(),object.getBelongs());
        }
        return  GaiNiansBelones;
    }


    //上传概念属于表
    public String WriteBeloneExcel(){
        return WriteExcel.writeExcel(findAll(),"概念1对1相关表");
    }
}
