package com.hitales.controller;

import com.hitales.Repository.OrignRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.Origin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by zhubo on 2018/7/23.
 */


public class AchieveOrigin {

    @Autowired
    private OrignRepository achieveOrignRepository;


    //生成原始表
    private List<String> findAll(){

        List<String> GaiNians =  new ArrayList<>();


        List<Origin> origins = achieveOrignRepository.findAll();
        if(origins==null){
            return new ArrayList<>();
        }
        Set<String> data = new HashSet<>();
        for(Origin o: origins){
            if(data.add(o.getTYConcept())){
                GaiNians.add(o.getTYConcept());
            }
        }
        return  GaiNians;
    }


    /**
     * 上传所有原始表
     */
    public String uploadOrigin(String type){
        String path = WriteExcel.writeExcelOrigin(findAll(),type);
        return  path;
    }
}
