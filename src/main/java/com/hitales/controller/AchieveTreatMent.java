package com.hitales.controller;

import com.alibaba.fastjson.JSONObject;
import com.hitales.Repository.TreatMentRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.TreatMent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhubo on 2018/7/23.
 */
public class AchieveTreatMent {

    @Autowired
    private TreatMentRepository treatMentRepository;



    private ArrayList<ArrayList<JSONObject>> findAll(){

        ArrayList<ArrayList<JSONObject>> datas =  new ArrayList<>();

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
        return  datas;
    }


    public void writeExcelTreatMent(){

        WriteExcel.writeExcelTreatMent(findAll());
    }

}
