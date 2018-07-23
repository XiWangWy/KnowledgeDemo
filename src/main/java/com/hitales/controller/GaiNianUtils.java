package com.hitales.controller;

import com.hitales.Repository.GaiNianRepository;
import com.hitales.Utils.WriteExcel;
import com.hitales.entity.GaiNian;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */
public class GaiNianUtils {

    @Autowired
    private GaiNianRepository gaiNianRepository;

    public List<GaiNian> findAll(){

        List<GaiNian> datas =  new ArrayList<>();

        try {
           List<GaiNian> gaiNians = gaiNianRepository.findAll();
       }catch (Exception e){
            return  new ArrayList<>();
       }
       return datas;
    }

    public String writeBelong(){
        return  WriteExcel.writeExcelGaiNianNull(findAll(),"概念属于表");
    }

    public String writeTY(){
        return  WriteExcel.writeExcelGaiNianNull(findAll(),"概念同义表");
    }

    public String writeOneToOne(){
        return  WriteExcel.writeExcelGaiNianNull(findAll(),"概念1对1相关表");
    }

    public String writeOneToMany(){
        return  WriteExcel.writeExcelGaiNianNull(findAll(),"概念1对多相关表");
    }

}