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
    public void uploadOrigin(){
        String[] titles={"属于1","属于2","属于3","属于4"};
        WriteExcel.writeExcelOrigin(findAll(),titles);

        String[] titles1={"别名1","别名2","别名3","别名4"};
        WriteExcel.writeExcelOrigin(findAll(),titles1);

        String[] titles2={"相关1","相关2","相关3","相关4"};
        WriteExcel.writeExcelOrigin(findAll(),titles2);

        String[]  titles3={"且相关1","且相关2","且相关3","且相关4"};
        WriteExcel.writeExcelOrigin(findAll(),titles3);
    }

}
