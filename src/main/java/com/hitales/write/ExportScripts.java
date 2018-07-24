package com.hitales.write;


import com.hitales.Repository.*;
import com.hitales.Utils.FileHelper;
import com.hitales.entity.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ExportScripts {
    private DiseaseRepository diseaseRepository;

    private TreatMentRepository treatMentRepository;

    private GaiNianRepository gaiNianRepository;




    public ExportScripts(DiseaseRepository diseaseRepository,TreatMentRepository treatMentRepository,GaiNianRepository gaiNianRepository){
        this.diseaseRepository = diseaseRepository;
        this.treatMentRepository = treatMentRepository;
        this.gaiNianRepository= gaiNianRepository;
    }


    public String exportScripts(){
        getOrigin();
        return "";
    }


    public String getOrigin(){
        int xuhao = 0;

        List<String> gainianList = new ArrayList<>();

        //(`3` :`概念0` {type:"概念0"}) ,
        List<StringBuilder> listGaiNian = new ArrayList<>();
        //(`0`)-[:`属于` ]->(`1`),
        List<StringBuilder> relation = new ArrayList<>();

        List<GaiNian> list = gaiNianRepository.findAll();

        for (GaiNian gaiNian : list){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(`").append(xuhao).append("`:`").append(gaiNian.getConcept())
                    .append("`{type:\"").append(gaiNian.getConcept()).append("\"}),");
            listGaiNian.add(stringBuilder);
            gainianList.add(gaiNian.getConcept());
            xuhao ++;
        }

        for (GaiNian gaiNian : list){
            if (gaiNian.getBelongs() != null){
                relation.addAll(dou(gaiNian.getBelongs(),"属于",gainianList,gaiNian.getConcept()));
            }
            if (gaiNian.getTy() != null){
                relation.addAll(dou(gaiNian.getTy(),"别名",gainianList,gaiNian.getConcept()));
            }
            if (gaiNian.getOneToOne()!= null){
                relation.addAll(dou(gaiNian.getOneToOne(),"相关",gainianList,gaiNian.getConcept()));
            }
            if (gaiNian.getOneToMany() != null){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("(`").append(xuhao).append("` : Node) ,");
                listGaiNian.add(stringBuilder);
                List<StringBuilder> many= douOneToMany(gaiNian.getOneToMany(),gainianList,gaiNian.getConcept(),xuhao);
                relation.addAll(many);
                gainianList.add("");
                xuhao ++;
            }
        }


        List<Disease> diseaseList = diseaseRepository.findAll();
        for (Disease disease : diseaseList){
            StringBuilder stringBuilder = new StringBuilder();
            if (disease.getCondition() != null){
                stringBuilder.append("(`").append(xuhao).append("` : `判断条件:").append(disease.getCondition()).append("` {type:\"判断条件:")
                        .append(disease.getCondition()).append("\"}) ,");
                listGaiNian.add(stringBuilder);
                gainianList.add("");
                xuhao ++;
            }

            if (disease.getName()!= null){
                gainianList.add(disease.getName());
                stringBuilder = new StringBuilder();
                stringBuilder.append("(`").append(xuhao).append("` : `").append(disease.getName()).append("` {type:\":")
                        .append(disease.getName()).append("\"}) ,");
                listGaiNian.add(stringBuilder);
                gainianList.add(disease.getName());
            }
            if (disease.getElements() != null){
                int xunhaojian = xuhao -1;
                relation.addAll(douOneToMany(disease.getElements(),gainianList,disease.getName(),xunhaojian));
                xuhao++;
            }
        }

        List<TreatMent>  treatMents= treatMentRepository.findAll();
        for(TreatMent treatMent : treatMents){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(`").append(xuhao).append("` : `").append(treatMent.getName()).append("` {type:\"")
                    .append(treatMent.getName()).append("\"}) ,");
            listGaiNian.add(stringBuilder);
            gainianList.add(treatMent.getName());
            xuhao++;
            stringBuilder = new StringBuilder();
            stringBuilder.append("(`").append(xuhao).append("` : `组合依据` {type:\"组合依据\"}) ,");
            listGaiNian.add(stringBuilder);
            if (treatMent.getElements() != null){
                relation.addAll(douOneToMany(treatMent.getElements(),gainianList,treatMent.getName(),xuhao));
                xuhao++;
            }

        }



        listGaiNian.addAll(relation);
        StringBuilder text = new StringBuilder();

        for (StringBuilder stringBuilder : listGaiNian){
            text.append(stringBuilder).append("\n");
        }
        text.deleteCharAt(text.lastIndexOf(","));

        String path = "./OriginExcel/脚本.txt";

        FileHelper.WriteStrng2File(true,new String(text),path);

        return path;
    }

    //斗一对多
    private List<StringBuilder> douOneToMany(List<String> list,List<String> gainianlist,String concept,int xuhao) {
        List<StringBuilder> returnlist = new ArrayList();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(`").append(gainianlist.indexOf(concept)).append("`)-[:`组合相关` ]->(`").append(xuhao).append("`),");
        returnlist.add(stringBuilder);
        list.forEach(s -> {
            StringBuilder stringBuilder1 = new StringBuilder();
            if (s.startsWith("！")){
                stringBuilder1.append("(`").append(xuhao).append("`)-[:`组合判断-非` ]->(`")
                        .append(gainianlist.indexOf(s.replace("！",""))).append("`),");
            }else {
                stringBuilder1.append("(`").append(xuhao).append("`)-[:`组合判断-且` ]->(`")
                        .append(gainianlist.indexOf(s)).append("`),");
            }
            returnlist.add(stringBuilder1);
        });

        return returnlist;
    }
    //斗数据
    private List<StringBuilder> dou(List<String> list,String typename,List<String> gainianlist,String concept){
        List<StringBuilder> returnlist = new ArrayList();
        list.forEach(s -> {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(`").append(gainianlist.indexOf(concept)).append("`)-[:`").append(typename).append("` ]->(`")
                    .append(gainianlist.indexOf(s)).append("`),");
            returnlist.add(stringBuilder);
        });

        return returnlist;
    }


//    private List<StringBuilder> getBelong(){
//        //(`3` :`概念0` {type:"概念0"}) ,
//        List<StringBuilder> listGaiNian = new ArrayList<>();
//        //(`0`)-[:`属于` ]->(`1`),
//        List<StringBuilder> relation = new ArrayList<>();
//
//        List<String> concept= new ArrayList<>();
//        List<GaiNianBeloneEntity> gaiNianBeloneEntityList = achieveGaiNianBeloneRepository.findAll();
//        //去重
//
//        for (GaiNianBeloneEntity gaiNianBeloneEntity:gaiNianBeloneEntityList){
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("(`").append(xuhao).append("`:`").append(gaiNianBeloneEntity.getConcept())
//                    .append("`{type:\"").append(gaiNianBeloneEntity.getConcept()).append("\"}),");
//            listGaiNian.add(stringBuilder);
//            concept.add(gaiNianBeloneEntity.getConcept());
//            xuhao++;
//        }
//
//        for (GaiNianBeloneEntity gaiNianBeloneEntity:gaiNianBeloneEntityList){
//            String conceptstr = gaiNianBeloneEntity.getConcept();
//            List<String> belong = gaiNianBeloneEntity.getBelongs();
//            StringBuilder stringBuilder = new StringBuilder();
//            if (belong != null){
//                for (String s : belong){
//                    stringBuilder.append("(`").append(concept.indexOf(conceptstr)).append("`)-[:`属于` ]->(`")
//                            .append(concept.indexOf(s)).append("`),");
//                }
//            }
//            relation.add(stringBuilder);
//        }
//        listGaiNian.addAll(relation);
//        return listGaiNian;
//    }
//
//    private List<StringBuilder> getTY(){
//        //(`3` :`概念0` {type:"概念0"}) ,
//        List<StringBuilder> listGaiNian = new ArrayList<>();
//        //(`0`)-[:`属于` ]->(`1`),
//        List<StringBuilder> relation = new ArrayList<>();
//
//        List<String> concept= new ArrayList<>();
//        List<GaiNianTYEntity> gaiNianBeloneEntityList = gaiNianTYRepository.findAll();
//        //去重
//
//        for (GaiNianTYEntity gaiNianBeloneEntity:gaiNianBeloneEntityList){
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("(`").append(xuhao).append("`:`").append(gaiNianBeloneEntity.getConcept())
//                    .append("`{type:\"").append(gaiNianBeloneEntity.getConcept()).append("\"}),");
//            listGaiNian.add(stringBuilder);
//            concept.add(gaiNianBeloneEntity.getConcept());
//            xuhao++;
//        }
//
//        for (GaiNianTYEntity gaiNianBeloneEntity:gaiNianBeloneEntityList){
//            String conceptstr = gaiNianBeloneEntity.getConcept();
//            List<String> belong = gaiNianBeloneEntity.getBelongs();
//            StringBuilder stringBuilder = new StringBuilder();
//            if (belong != null){
//                for (String s : belong){
//                    stringBuilder.append("(`").append(concept.indexOf(conceptstr)).append("`)-[:`别名` ]->(`")
//                            .append(concept.indexOf(s)).append("`),");
//                }
//            }
//            relation.add(stringBuilder);
//        }
//        listGaiNian.addAll(relation);
//        return listGaiNian;
//    }


}
