package com.hitales.controller;

import com.hitales.Repository.GaiNianTYRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.GaiNianTYEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */


public class AchieveGaiNianTY {

    @Autowired
    private GaiNianTYRepository gaiNianTYRepository;

    private AchieveOrigin achieveOrigin =  new AchieveOrigin();


    //生成属于表
    private Map<String,ArrayList<String>> findAll(){

        Map<String,ArrayList<String>> GaiNiansBelones =  new HashMap<>();


        try {
            List<GaiNianTYEntity> origins = gaiNianTYRepository.findAll();


            for(GaiNianTYEntity object: origins){

                GaiNiansBelones.put(object.getConcept(),object.getBelongs());
            }

        }catch (Exception e){
            return new HashMap<>();
        }

        return  GaiNiansBelones;
    }


    //上传概念属于表
    public String WriteBeloneExcel(){
        if(findAll().isEmpty()){
            return achieveOrigin.uploadOrigin("概念同义表");
        }else{
            return WriteExcel.writeExcel(findAll(),"概念同义表");
        }


    }
}
