package com.hitales.controller;

import com.hitales.Repository.OrignRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.Origin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
