package com.hitales.Utils;

import com.hitales.Repository.DiseaseRepository;

import com.hitales.entity.Disease;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhubo on 2018/7/23.
 */
public class AchieveDisease {

    private DiseaseRepository diseaseRepository;
    /**
     * 查询病因诱因表(去重)
     * @return 病因&诱因集合
     */
    private List<Disease> findAllDisease(){

        List<Disease> diseases;
        Set<String> data = new HashSet<>();
        try {
            diseases = diseaseRepository.findAll();
            for(Disease disease : diseases){
                if(!data.add(disease.getName())){
                    diseases.remove(disease);
                }
            }
        }catch (Exception e){
            return new ArrayList<>();
        }
        return  diseases;
    }


    public String writeDiseaseExcel(DiseaseRepository diseaseRepository){
        this.diseaseRepository = diseaseRepository;
        return WriteExcel.writeExcelDiease(findAllDisease());
    }
}
