package com.hitales.controller;

import com.hitales.Repository.OrignRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.Origin;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

        for(Origin o: origins){
            GaiNians.add(o.getTYConcept());
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
