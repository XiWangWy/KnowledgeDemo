package com.hitales.Utils;

import com.hitales.Repository.GaiNian1TOManyRepository;
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

        try{
            List<GaiNian1TOManyEntity> origins = gaiNian1TOManyRepository.findAll();

            for(GaiNian1TOManyEntity object: origins){

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
            return achieveOrigin.uploadOrigin("概念1对多相关表");
        }else {
            return WriteExcel.writeExcel(findAll(),"概念1对多相关表");
        }
    }
}
