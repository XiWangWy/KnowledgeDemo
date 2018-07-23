package com.hitales.controller;

import com.alibaba.fastjson.JSONObject;
import com.hitales.Repository.DiseaseRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.Disease;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhubo on 2018/7/23.
 */
public class AchieveDisease {

    @Autowired
    private DiseaseRepository diseaseRepository;


    private ArrayList<ArrayList<JSONObject>> findAll(){

        ArrayList<ArrayList<JSONObject>> datas =  new ArrayList<>();

        List<Disease> origins = diseaseRepository.findAll();

        ArrayList<JSONObject> tempDatas = new ArrayList<>();
        for(Disease object: origins){
            JSONObject object1 =  new JSONObject();
            String name = object.getName();
            ArrayList<String> elements = object.getElements();
            String condition = object.getCondition();
            object1.put("name",name);
            object1.put("elements",elements);
            object1.put("condition",condition);
            tempDatas.add(object1);
        }
        return  datas;
    }

    private ArrayList<String> findAllTreatMents(){

        ArrayList<String> treatMents = new ArrayList<>();

        List<Disease> origins = diseaseRepository.findAll();
        for(Disease object: origins){
            String name = object.getName();
            treatMents.add(name);
        }
        return  treatMents;
    }

    //上传病因&诱因表
    public String writeDiseaseExcel(){

        return WriteExcel.writeExcelMany(findAll());
    }

    //下载处置表
    public String writeTreatMent(){
        return  WriteExcel.writeExcelTreatMentOrigin(findAllTreatMents());
    }
}
