package com.hitales.Utils;

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

    private TreatMentRepository treatMentRepository;

    private DiseaseRepository diseaseRepository;

    /**
     * 查询处置表(去重)
     * @return 处置集合
     */
    private List<TreatMent> findAllTreatMent(){

        List<TreatMent> treatMents = new ArrayList<>();

        Set<String> data = new HashSet<>();

        try {
            List<TreatMent> treatMentsTemp = treatMentRepository.findAll();//处置表

            List<Disease> diseases = diseaseRepository.findAll();//病因&诱因表

            if(treatMentsTemp.size()==0 && diseases.size()==0){
                return  new ArrayList<>();
            }else {
                for(TreatMent treatMent : treatMentsTemp){
                    //依据不为空的情况，可以重名
                    if(treatMent.getElements().size()!=0){
                        treatMents.add(treatMent);

                        data.add(treatMent.getDiease());
                    }else if(data.add(treatMent.getDiease())){
                        treatMents.add(treatMent);
                    }
                }
                //默认从病因&诱因表概念生成
                for(Disease disease : diseases){
                    if(data.add(disease.getName())){
                        TreatMent treatMent = new TreatMent();
                        treatMent.setName("");
                        treatMent.setDiease(disease.getName());
                        treatMent.setElements(new ArrayList<>());
                        treatMents.add(treatMent);
                    }
                }
            }
        }catch (Exception e){
            return new ArrayList<>();
        }
        return  treatMents;
    }

    public String writeExcelTreatMent(TreatMentRepository treatMentRepository, DiseaseRepository diseaseRepository){
        this.diseaseRepository = diseaseRepository;
        this.treatMentRepository = treatMentRepository;
        return WriteExcel.writeExcelTreatMents(findAllTreatMent());
    }
}
