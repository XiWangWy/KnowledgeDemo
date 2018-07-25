package com.hitales.Utils;

import com.alibaba.fastjson.JSONObject;
import com.hitales.entity.Disease;
import com.hitales.entity.GaiNian;
import com.hitales.entity.TreatMent;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhubo on 2018/7/23.
 */
public class WriteExcel {

    /**
     * 生成概念相关表
     * @param gaiNians 概念相关表集合
     * @param type 表类型
     * @return 路径
     */
    public static String writeExcelGaiNianExcel(List<GaiNian> gaiNians, String type) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");

        //概念表不为空，计算相应表头的长度
        int count1 = 0;
        //存储相应的实体
        List<ArrayList<String>> entityData = new ArrayList();

        for (int i = 0; i < gaiNians.size(); i++) {
            if (type.equals("概念属于表")) {
                ArrayList<String> belongs = gaiNians.get(i).getBelongs();
                if(belongs==null){
                    entityData.add(new ArrayList<>());
                }else {
                    entityData.add(belongs);
                    if (belongs.size() > count1) {
                        count1 = belongs.size();
                    }
                }

            } else if (type.equals("概念同义表")) {
                ArrayList<String> ty = gaiNians.get(i).getTy();
                if(ty==null){
                    entityData.add(new ArrayList<>());
                }else {
                    entityData.add(ty);
                    if (ty.size() > count1) {
                        count1 = ty.size();
                    }
                }
            } else if (type.equals("概念1对1相关表")) {
                ArrayList<String> OneToOne = gaiNians.get(i).getOneToOne();
                if(OneToOne==null){
                    entityData.add(new ArrayList<>());
                }else {
                    entityData.add(OneToOne);
                    if (OneToOne.size() > count1) {
                        count1 = OneToOne.size();
                    }
                }
            } else if (type.equals("概念1对多相关表")) {
                ArrayList<String> OneToOneToMany = gaiNians.get(i).getOneToMany();
                if(OneToOneToMany==null){
                    entityData.add(new ArrayList<>());
                }else {
                    entityData.add(OneToOneToMany);
                    if (OneToOneToMany.size()> count1) {
                        count1 = OneToOneToMany.size();
                    }
                }
            }
        }
        //1.数据库还不存在概念表时返回size为0
        //2.除概念表的概念以外都为空默认4个属性
        int indexHead;
        if (gaiNians.size() == 0 || count1==0) {
            indexHead = 4;
        }else {
            indexHead=count1;
        }
        for (int i = 0; i < indexHead; i++) {
            if (type.equals("概念属于表")) {
                row.createCell(i+1).setCellValue("属于" + (i+1));
            } else if (type.equals("概念同义表")) {
                row.createCell(i+1).setCellValue("别名" + (i+1));
            } else if (type.equals("概念1对1相关表")) {
                row.createCell(i+1).setCellValue("相关" + (i+1));

            } else if (type.equals("概念1对多相关表")) {
                row.createCell(i+1).setCellValue("且相关" + (i+1));
            }
        }

        for (int i = 0; i < gaiNians.size(); i++) {
            String name = gaiNians.get(i).getConcept();
            Row row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(name);

            List<String> data = entityData.get(i);

            for (int j = 0; j < data.size(); j++) {
                row1.createCell(j + 1).setCellValue(data.get(j));
            }
        }

        try {
            FileOutputStream fos = null;
            if (type.equals("概念属于表")) {
                path = "./OriginExcel/概念属于表.xlsx";
                fos = new FileOutputStream(path);
            } else if (type.equals("概念同义表")) {
                path = "./OriginExcel/概念同义表.xlsx";
                fos = new FileOutputStream(path);
            } else if (type.equals("概念1对1相关表")) {
                path = "./OriginExcel/概念1对1相关表.xlsx";
                fos = new FileOutputStream(path);
            } else if (type.equals("概念1对多相关表")) {
                path = "./OriginExcel/概念1对多相关表.xlsx";
                fos = new FileOutputStream(path);
            }
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 生成病因&诱因表
     *
     * @param diseases 病因&诱因集合
     * @return 文件路径
     */
    public static String writeExcelDiease(List<Disease> diseases) {
        String path = "";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("判断条件");
        int count = 0;
        //获取最长因素，生成表头使用
        for (int i = 0; i < diseases.size(); i++) {
            ArrayList<String> elements = diseases.get(i).getElements();
            if (elements.size() > count) {
                count = elements.size();
            }
        }
        //空表默认给4个表头
        if (count == 0) {
            row.createCell(2).setCellValue("因素1");
            row.createCell(3).setCellValue("因素2");
            row.createCell(4).setCellValue("因素3");
            row.createCell(5).setCellValue("因素4");
        } else {
            for (int i = 0; i < count; i++) {
                row.createCell(i + 2).setCellValue("因素" + (i + 1));
            }
        }
        //生成表内容
        for (int i = 0; i < diseases.size(); i++) {
            int cellIndex = 0;
            Row row1 = sheet.createRow(i + 1);
            Disease disease = diseases.get(i);
            String name = disease.getName();
            String condition = disease.getCondition();
            row1.createCell(cellIndex).setCellValue(name);
            cellIndex++;
            row1.createCell(cellIndex).setCellValue(condition);
            cellIndex++;
            ArrayList<String> elements = disease.getElements();
            for (String element : elements) {
                row1.createCell(cellIndex).setCellValue(element);
                cellIndex++;
            }
        }

        try {
            path = "./OriginExcel/病因&诱因表.xlsx";
            FileOutputStream fos = new FileOutputStream(path);
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    /**
     * 生成处置表
     *
     * @param treatMents 处置表集合
     * @return 文件路径
     */

    public static String writeExcelTreatMents(List<TreatMent> treatMents) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("病因&诱因");

        int count = 0;
        for (int i = 0; i < treatMents.size(); i++) {
            ArrayList<String> elements = treatMents.get(i).getElements();
            if (elements.size() > count) {
                count = elements.size();
            }
        }
        //设置表头
        if (count == 0) {
            row.createCell(2).setCellValue("依据1");
            row.createCell(3).setCellValue("依据2");
            row.createCell(4).setCellValue("依据3");

        } else {
            for (int i = 0; i < count; i++) {
                row.createCell(i + 2).setCellValue("依据" + (i + 1));
            }
        }
        //写表内容
        for (int i = 0; i < treatMents.size(); i++) {
            int cellIndex = 0;
            Row row1 = sheet.createRow(i + 1);
            TreatMent treatMent = treatMents.get(i);
            String name = treatMent.getName();
            String diease = treatMent.getDiease();

            row1.createCell(cellIndex).setCellValue(name);
            cellIndex++;
            row1.createCell(cellIndex).setCellValue(diease);
            cellIndex++;
            ArrayList<String> elements = treatMent.getElements();
            for (String element : elements) {
                row1.createCell(cellIndex).setCellValue(element);
                cellIndex++;
            }

        }
        try {
            path = "./OriginExcel/处置表.xlsx";
            FileOutputStream fos = new FileOutputStream("./OriginExcel/处置表.xlsx");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
