package com.hitales.controller;

import com.alibaba.fastjson.JSONObject;
import com.hitales.Repository.DiseaseRepository;
import com.hitales.Repository.TreatMentRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.Disease;
import com.hitales.entity.TreatMent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhubo on 2018/7/23.
 */
public class AchieveTreatMent {

    @Autowired
    private TreatMentRepository treatMentRepository;

    @Autowired
    private DiseaseRepository diseaseRepository;



    private ArrayList<ArrayList<JSONObject>> findAll(){

        ArrayList<ArrayList<JSONObject>> datas =  new ArrayList<>();


        try {
            List<TreatMent> origins = treatMentRepository.findAll();

            ArrayList<JSONObject> tempDatas = new ArrayList<>();
            for(TreatMent object: origins){
                JSONObject object1 =  new JSONObject();
                String name = object.getName();
                ArrayList<String> elements = object.getElements();
                String diease = object.getDiease();
                object1.put("name",name);
                object1.put("elements",elements);
                object1.put("diease",diease);
                tempDatas.add(object1);
            }

        }catch (Exception e){
            return  new ArrayList<>();
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

    public String writeExcelTreatMent(){

        if(findAll().isEmpty()){
            return  WriteExcel.writeExcelTreatMentOrigin(findAllTreatMents());

        }else {
            return WriteExcel.writeExcelTreatMent(findAll());
        }

    }

}
