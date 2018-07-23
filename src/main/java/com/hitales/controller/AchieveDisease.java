package com.hitales.controller;

import com.alibaba.fastjson.JSONObject;
import com.hitales.Repository.DiseaseRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.Disease;
import com.hitales.entity.Origin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhubo on 2018/7/23.
 */
public class AchieveDisease {

    private DiseaseRepository diseaseRepository;


    private ArrayList<ArrayList<JSONObject>> findAll(){

        ArrayList<ArrayList<JSONObject>> datas =  new ArrayList<>();

        List<Disease> origins = diseaseRepository.findAll();

        if(origins==null){
            return new ArrayList<>();
        }

        for(Disease object: origins){
            ArrayList<JSONObject> tempDatas = new ArrayList<>();
            JSONObject object1 =  new JSONObject();
            String name = object.getName();
            ArrayList<String> elements = object.getElements();
            String condition = object.getCondition();
            object1.put("name",name);
            object1.put("elements",elements);
            object1.put("condition",condition);
            tempDatas.add(object1);
            datas.add(tempDatas);
        }
        return  datas;
    }

    private ArrayList<String> findAllTreatMents(){

        ArrayList<String> treatMents = new ArrayList<>();

        Set<String> data = new HashSet<>();

        try {
            List<Disease> origins = diseaseRepository.findAll();
            for(Disease object: origins){
                String name = object.getName();
                if(data.add(name)){
                    treatMents.add(name);
                }
            }
        }catch (Exception e){
            return new ArrayList<>();
        }

        return  treatMents;
    }

    //上传病因&诱因表
    public String writeDiseaseExcel(DiseaseRepository diseaseRepository){
        this.diseaseRepository = diseaseRepository;
        if(findAll().isEmpty()){
            return  WriteExcel.writeExcelDieaseOrigin(findAllTreatMents());
        }else {
            return WriteExcel.writeExcelMany(findAll());
        }

    }
}
