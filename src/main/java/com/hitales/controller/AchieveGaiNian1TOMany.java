package com.hitales.controller;

import com.hitales.Repository.GaiNian1TO1Repository;
import com.hitales.Repository.GaiNian1TOManyRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.GaiNian1TO1Entity;
import com.hitales.entity.GaiNian1TOManyEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */


public class AchieveGaiNian1TOMany {

    @Autowired
    private GaiNian1TOManyRepository gaiNian1TOManyRepository;

    private AchieveOrigin achieveOrigin =  new AchieveOrigin();


    //生成属于表
    private Map<String,ArrayList<String>> findAll(){

        Map<String,ArrayList<String>> GaiNiansBelones =  new HashMap<>();

        ArrayList<String> values = new ArrayList<>();

        List<GaiNian1TOManyEntity> origins = gaiNian1TOManyRepository.findAll();
        if(origins==null){
            return new HashMap<>();
        }

        for(GaiNian1TOManyEntity object: origins){

            GaiNiansBelones.put(object.getConcept(),object.getBelongs());
        }
        return  GaiNiansBelones;
    }


    //上传概念属于表
    public String WriteBeloneExcel(){
        if(findAll().isEmpty()){
            return achieveOrigin.uploadOrigin("概念1对多相关表");
        }else {
            return WriteExcel.writeExcel(findAll(),"概念1对多相关表");
        }
    }
}
