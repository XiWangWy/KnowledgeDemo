package com.hitales.Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhubo on 2018/7/23.
 */
public class WriteExcel {

    public static void writeExcelOrigin(List<String> object ,String[] titles){

        XSSFWorkbook workbook =  new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        for(int i=0;i<titles.length;i++){
            row.createCell(i+1).setCellValue(titles[i]);
        }

        for(int i=0;i<object.size();i++){
            Row row1 = sheet.createRow(i+1);
            String gainian = object.get(i);
            row1.createCell(0).setCellValue(gainian);
        }
        try {
            FileOutputStream fos = null;
            if(titles[0].contains("属于")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念属于表.xlsx");
            }else if(titles[0].contains("别名")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念同义表.xlsx");
            }else if(titles[0].startsWith("相关")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念1对1相关表.xlsx");
            }else if(titles[0].contains("相关")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念1对多相关表.xlsx");
            }

            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeExcel(Map<String,ArrayList<String>> datas,String type){

        XSSFWorkbook workbook =  new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");


        int count = 0;

        for(Map.Entry<String,ArrayList<String>> entry : datas.entrySet()){
            int size = entry.getValue().size();
            if(size>count){
                count = size;//取最长
            }
        }
        for(int i=0;i<count;i++){
            if(type.equals("概念属于表")){
                row.createCell(i+1).setCellValue("属于"+(i+1));
            }else if(type.equals("概念同义表")){
                row.createCell(i+1).setCellValue("别名"+(i+1));
            }else if(type.equals("概念1对1相关表")){
                row.createCell(i+1).setCellValue("相关"+(i+1));
            }else if(type.equals("概念1对多相关表")){
                row.createCell(i+1).setCellValue("且相关"+(i+1));
            }
        }

        int index = 1;
        for(Map.Entry<String,ArrayList<String>> entry : datas.entrySet()){
            Row row1 = sheet.createRow(index);
            String key = entry.getKey();

            row1.createCell(0).setCellValue(key);
            ArrayList<String> values = entry.getValue();
            for(int i=0;i<values.size();i++){
                row1.createCell(i+1).setCellValue(values.get(i));
            }
            index++;
        }

        try {
            FileOutputStream fos = null;

            if(type.equals("概念属于表")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念属于表.xlsx");
            }else if(type.equals("概念同义表")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念同义表.xlsx");
            }else if(type.equals("概念1对1相关表")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念1对1相关表.xlsx");
            }else if(type.equals("概念1对多相关表")){
                fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/概念1对多相关表.xlsx");
            }
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeExcelMany(ArrayList<ArrayList<JSONObject>> datas){

        XSSFWorkbook workbook =  new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        int count=0;
        for(int i=0;i<datas.size();i++){
            ArrayList<JSONObject> objects = datas.get(i);
            for(int k=0;k<objects.size();k++){
                ArrayList<String> elements = (ArrayList)objects.get(i).get("elements");
                if(elements.size()>count){
                    count = elements.size();
                }
            }
        }
        for(int i=0;i<count;i++){
            row.createCell(i+1).setCellValue("因素"+(i+1));
        }
        row.createCell(count+1).setCellValue("判断条件");

        for(int i=0;i<datas.size();i++){
            ArrayList<JSONObject> objects = datas.get(i);
            Row row1 = sheet.createRow(i+1);

            int index = 0;
            for(int k=0;k<objects.size();k++){
                String name = objects.get(i).getString("name");

                row1.createCell(index).setCellValue(name);
                index++;
                ArrayList<String> elements = (ArrayList)objects.get(i).get("elements");
                for(int l=0;l<elements.size();l++){
                    row1.createCell(index).setCellValue(elements.get(l));
                    index++;
                }
                String condition = objects.get(i).getString("condition");
                row1.createCell(count+1).setCellValue(condition);
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/病因&诱因表.xlsx");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeExcelTreatMentOrigin(ArrayList<String> treatMents){

        XSSFWorkbook workbook =  new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("病因&诱因");
        row.createCell(2).setCellValue("依据1");
        row.createCell(3).setCellValue("依据2");
        row.createCell(4).setCellValue("依据3");

        for(int i=0;i<treatMents.size();i++){
            Row row1 = sheet.createRow(i+1);
            String diease = treatMents.get(i);
            row1.createCell(1).setCellValue(diease);
        }
        try {
            FileOutputStream fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/处置表.xlsx");

            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void writeExcelTreatMent(ArrayList<ArrayList<JSONObject>> datas){

        XSSFWorkbook workbook =  new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("病因&诱因");
        int count=0;
        for(int i=0;i<datas.size();i++){
            ArrayList<JSONObject> objects = datas.get(i);
            for(int k=0;k<objects.size();k++){
                ArrayList<String> elements = (ArrayList)objects.get(i).get("elements");
                if(elements.size()>count){
                    count = elements.size();
                }
            }
        }
        for(int i=0;i<count;i++){
            row.createCell(i+2).setCellValue("依据"+(i+1));
        }

        for(int i=0;i<datas.size();i++){
            ArrayList<JSONObject> objects = datas.get(i);
            Row row1 = sheet.createRow(i+1);

            int index = 0;
            for(int k=0;k<objects.size();k++){
                String name = objects.get(i).getString("name");
                row1.createCell(index).setCellValue(name);
                index++;
                String diease = objects.get(i).getString("diease");
                row1.createCell(index).setCellValue(diease);
                index++;
                ArrayList<String> elements = (ArrayList)objects.get(i).get("elements");
                for(int l=0;l<elements.size();l++){
                    row1.createCell(index).setCellValue(elements.get(l));
                    index++;
                }
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream("/KnowledgeDemo/OriginExcel/病因&诱因表.xlsx");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
