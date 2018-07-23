package com.hitales.Utils;

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
}
