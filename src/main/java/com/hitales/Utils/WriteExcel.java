package com.hitales.Utils;

import com.alibaba.fastjson.JSONObject;
import com.hitales.entity.GaiNian;
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


    public static String writeExcelGaiNianNull(List<GaiNian> gaiNians, String type) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");


        int count1 = 0;

        ArrayList<List<String>> DATA = new ArrayList<>();

        if (gaiNians.isEmpty()) {
            if (type.equals("概念属于表")) {
                row.createCell(1).setCellValue("属于1");
                row.createCell(2).setCellValue("属于2");
                row.createCell(3).setCellValue("属于3");
                row.createCell(4).setCellValue("属于4");
            } else if (type.equals("概念同义表")) {
                row.createCell(1).setCellValue("别名1");
                row.createCell(2).setCellValue("别名2");
                row.createCell(3).setCellValue("别名3");
                row.createCell(4).setCellValue("别名4");


            } else if (type.equals("概念1对1相关表")) {
                row.createCell(1).setCellValue("相关1");
                row.createCell(2).setCellValue("相关2");
                row.createCell(3).setCellValue("相关3");
                row.createCell(4).setCellValue("相关4");

            } else if (type.equals("概念1对多相关表")) {
                row.createCell(1).setCellValue("且相关1");
                row.createCell(2).setCellValue("且相关2");
                row.createCell(3).setCellValue("且相关3");
                row.createCell(4).setCellValue("且相关4");

            }
        }

        for (int i = 0; i < gaiNians.size(); i++) {
            List<String> data1 = new ArrayList<>();
            String gainian = gaiNians.get(i).getConcept();
            ArrayList<String> belongs = gaiNians.get(i).getBelongs();
            ArrayList<String> ty = gaiNians.get(i).getTy();
            ArrayList<String> OneToOne = gaiNians.get(i).getOneToOne();
            ArrayList<String> OneToOneToManyOne = gaiNians.get(i).getOneToMany();
            if (type.equals("概念属于表")) {
                if (belongs == null ||belongs.isEmpty()){
                    break;
                } else {
                    belongs.forEach(data1::add);
                    if (belongs.size() > count1) {
                        count1 = belongs.size();
                    }
                }
            } else if (type.equals("概念同义表")) {
                if (ty == null || ty.isEmpty()) {
//                    row.createCell(1).setCellValue("别名1");
//                    row.createCell(2).setCellValue("别名2");
//                    row.createCell(3).setCellValue("别名3");
//                    row.createCell(4).setCellValue("别名4");
//                    break;
                } else {
                    ty.forEach(data1::add);
                    if (ty.size() > count1) {
                        count1 = ty.size();
                    }
                }

            } else if (type.equals("概念1对1相关表")) {
                if (OneToOne == null || OneToOne.isEmpty()) {
//                    row.createCell(1).setCellValue("相关1");
//                    row.createCell(2).setCellValue("相关2");
//                    row.createCell(3).setCellValue("相关3");
//                    row.createCell(4).setCellValue("相关4");
//                    break;
                } else {
                    OneToOne.forEach(data1::add);
                    if (OneToOne.size() > count1) {
                        count1 = OneToOne.size();
                    }
                }
            } else if (type.equals("概念1对多相关表")) {

                if (OneToOneToManyOne == null || OneToOneToManyOne.isEmpty()) {
//                    row.createCell(1).setCellValue("且相关1");
//                    row.createCell(2).setCellValue("且相关2");
//                    row.createCell(3).setCellValue("且相关3");
//                    row.createCell(4).setCellValue("且相关4");
//                    break;
                } else {
                    OneToOneToManyOne.forEach(data1::add);
                    if (OneToOneToManyOne.size() > count1) {
                        count1 = OneToOneToManyOne.size();
                    }
                }
            }
            DATA.add(data1);
        }
        if (count1 > 0) {
            for (int i = 0; i < count1; i++) {
                if (type.equals("概念属于表")) {
                    row.createCell(i + 1).setCellValue("属于" + (i + 1));

                } else if (type.equals("概念同义表")) {
                    row.createCell(i + 1).setCellValue("别名" + (i + 1));

                } else if (type.equals("概念1对1相关表")) {
                    row.createCell(i + 1).setCellValue("相关" + (i + 1));
                } else if (type.equals("概念1对多相关表")) {
                    row.createCell(i + 1).setCellValue("且相关" + (i + 1));
                }
            }
        }else {
            for (int i = 0; i < 4; i++) {
                if (type.equals("概念属于表")) {
                    row.createCell(i + 1).setCellValue("属于" + (i + 1));

                } else if (type.equals("概念同义表")) {
                    row.createCell(i + 1).setCellValue("别名" + (i + 1));

                } else if (type.equals("概念1对1相关表")) {
                    row.createCell(i + 1).setCellValue("相关" + (i + 1));
                } else if (type.equals("概念1对多相关表")) {
                    row.createCell(i + 1).setCellValue("且相关" + (i + 1));
                }
            }
        }

        for (int i = 0; i < gaiNians.size(); i++) {
            String name = gaiNians.get(i).getConcept();
            Row row1 = sheet.createRow(i+1);
            row1.createCell(0).setCellValue(name);

            List<String> shuju = new ArrayList();
            switch (type){
                case "概念属于表":
                    shuju = gaiNians.get(i).getBelongs();
                    break;
                case "概念同义表":
                    shuju = gaiNians.get(i).getTy();
                    break;
                case "概念1对1相关表":
                    shuju = gaiNians.get(i).getOneToOne();
                    break;
                case "概念1对多相关表":
                    shuju = gaiNians.get(i).getOneToMany();
                    break;

            }

            if (shuju != null){
                for (int j = 0; j < shuju.size(); j++) {
                    row1.createCell(j + 1).setCellValue(shuju.get(j));
                }
            }

        }

        try {
            FileOutputStream fos = null;
            if (type.equals("概念属于表")) {
                fos = new FileOutputStream("./OriginExcel/概念属于表.xlsx");
                path = "./OriginExcel/概念属于表.xlsx";
            } else if (type.equals("概念同义表")) {
                fos = new FileOutputStream("./OriginExcel/概念同义表.xlsx");
                path = "./OriginExcel/概念同义表.xlsx";
            } else if (type.equals("概念1对1相关表")) {
                fos = new FileOutputStream("./OriginExcel/概念1对1相关表.xlsx");
                path = "./OriginExcel/概念1对1相关表.xlsx";
            } else if (type.equals("概念1对多相关表")) {
                fos = new FileOutputStream("./OriginExcel/概念1对多相关表.xlsx");
                path = "./OriginExcel/概念1对多相关表.xlsx";
            }

            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String writeExcelOrigin(List<String> object, String type) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");

        if (type.equals("概念属于表")) {
            row.createCell(1).setCellValue("属于1");
            row.createCell(2).setCellValue("属于1");
            row.createCell(3).setCellValue("属于1");
            row.createCell(4).setCellValue("属于1");

        } else if (type.equals("概念同义表")) {
            row.createCell(1).setCellValue("别名1");
            row.createCell(2).setCellValue("别名2");
            row.createCell(3).setCellValue("别名3");
            row.createCell(4).setCellValue("别名4");

        } else if (type.equals("概念1对1相关表")) {
            row.createCell(1).setCellValue("相关1");
            row.createCell(2).setCellValue("相关2");
            row.createCell(3).setCellValue("相关3");
            row.createCell(4).setCellValue("相关4");

        } else if (type.equals("概念1对多相关表")) {
            row.createCell(1).setCellValue("且相关1");
            row.createCell(2).setCellValue("且相关2");
            row.createCell(3).setCellValue("且相关3");
            row.createCell(4).setCellValue("且相关4");
        }

        for (int i = 0; i < object.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            String gainian = object.get(i);
            row1.createCell(0).setCellValue(gainian);
        }
        try {
            FileOutputStream fos = null;
            if (type.equals("概念属于表")) {
                fos = new FileOutputStream("./OriginExcel/概念属于表.xlsx");
                path = "./OriginExcel/概念属于表.xlsx";
            } else if (type.equals("概念同义表")) {
                fos = new FileOutputStream("./OriginExcel/概念同义表.xlsx");
                path = "./OriginExcel/概念同义表.xlsx";
            } else if (type.equals("概念1对1相关表")) {
                fos = new FileOutputStream("./OriginExcel/概念1对1相关表.xlsx");
                path = "./OriginExcel/概念1对1相关表.xlsx";
            } else if (type.equals("相概念1对多相关表关")) {
                fos = new FileOutputStream("./OriginExcel/概念1对多相关表.xlsx");
                path = "./OriginExcel/概念1对多相关表.xlsx";
            }

            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String writeExcel(Map<String, ArrayList<String>> datas, String type) {


        String path = "";
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");


        int count = 0;

        for (Map.Entry<String, ArrayList<String>> entry : datas.entrySet()) {
            int size = entry.getValue().size();
            if (size > count) {
                count = size;//取最长
            }
        }
        for (int i = 0; i < count; i++) {
            if (type.equals("概念属于表")) {
                row.createCell(i + 1).setCellValue("属于" + (i + 1));
            } else if (type.equals("概念同义表")) {
                row.createCell(i + 1).setCellValue("别名" + (i + 1));
            } else if (type.equals("概念1对1相关表")) {
                row.createCell(i + 1).setCellValue("相关" + (i + 1));
            } else if (type.equals("概念1对多相关表")) {
                row.createCell(i + 1).setCellValue("且相关" + (i + 1));
            }
        }

        int index = 1;
        for (Map.Entry<String, ArrayList<String>> entry : datas.entrySet()) {
            Row row1 = sheet.createRow(index);
            String key = entry.getKey();

            row1.createCell(0).setCellValue(key);
            ArrayList<String> values = entry.getValue();
            for (int i = 0; i < values.size(); i++) {
                row1.createCell(i + 1).setCellValue(values.get(i));
            }
            index++;
        }

        try {
            FileOutputStream fos = null;

            if (type.equals("概念属于表")) {
                fos = new FileOutputStream("./OriginExcel/概念属于表.xlsx");
                path = "./OriginExcel/概念属于表.xlsx";
            } else if (type.equals("概念同义表")) {
                fos = new FileOutputStream("./OriginExcel/概念同义表.xlsx");
                path = "./OriginExcel/概念同义表.xlsx";
            } else if (type.equals("概念1对1相关表")) {
                fos = new FileOutputStream("./OriginExcel/概念1对1相关表.xlsx");
                path = "./OriginExcel/概念1对1相关表.xlsx";
            } else if (type.equals("概念1对多相关表")) {
                fos = new FileOutputStream("./OriginExcel/概念1对多相关表.xlsx");
                path = "./OriginExcel/概念1对多相关表.xlsx";
            }
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public static String writeExcelMany(ArrayList<ArrayList<JSONObject>> datas) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("判断条件");
        int count = 0;
        for (int i = 0; i < datas.size(); i++) {
            ArrayList<JSONObject> objects = datas.get(i);
            for (int k = 0; k < objects.size(); k++) {
                ArrayList<String> elements = (ArrayList) objects.get(k).get("elements");
                if (elements.size() > count) {
                    count = elements.size();
                }
            }
        }
        for (int i = 0; i < count; i++) {
            row.createCell(i + 2).setCellValue("因素" + (i + 1));
        }

        for (int i = 0; i < datas.size(); i++) {
            ArrayList<JSONObject> objects = datas.get(i);
            Row row1 = sheet.createRow(i + 1);

            int index = 0;
            for (int k = 0; k < objects.size(); k++) {
                String name = objects.get(k).getString("name");

                row1.createCell(index).setCellValue(name);
                index++;
                String condition = objects.get(k).getString("condition");
                row1.createCell(index).setCellValue(condition);
                index++;
                ArrayList<String> elements = (ArrayList) objects.get(k).get("elements");
                for (int l = 0; l < elements.size(); l++) {
                    row1.createCell(index).setCellValue(elements.get(l));
                    index++;
                }
            }
        }

        try {
            path = "./OriginExcel/病因&诱因表.xlsx";
            FileOutputStream fos = new FileOutputStream("./OriginExcel/病因&诱因表.xlsx");
            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    public static String writeExcelTreatMentOrigin(ArrayList<String> treatMents) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("病因&诱因");
        row.createCell(2).setCellValue("依据1");
        row.createCell(3).setCellValue("依据2");
        row.createCell(4).setCellValue("依据3");

        for (int i = 0; i < treatMents.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            String diease = treatMents.get(i);
            row1.createCell(1).setCellValue(diease);
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

    public static String writeExcelDieaseOrigin(ArrayList<String> treatMents) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("判断条件");
        row.createCell(2).setCellValue("因素1");
        row.createCell(3).setCellValue("因素2");
        row.createCell(4).setCellValue("因素3");
        row.createCell(5).setCellValue("因素4");

        for (int i = 0; i < treatMents.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            String diease = treatMents.get(i);
            row1.createCell(1).setCellValue(diease);
        }
        try {
            path = "./OriginExcel/病因&诱因表.xlsx";
            FileOutputStream fos = new FileOutputStream("./OriginExcel/病因&诱因表.xlsx");

            workbook.write(fos);
            System.out.println("写入成功");
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }


    public static String writeExcelTreatMent(ArrayList<ArrayList<JSONObject>> datas) {

        String path = "";

        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        row.createCell(0).setCellValue("概念名称");
        row.createCell(1).setCellValue("病因&诱因");
        int count = 0;
        for (ArrayList<JSONObject> objects : datas) {
            for (JSONObject object : objects) {
                ArrayList<String> elements = (ArrayList) object.get("elements");
                if (elements.size() > count) {
                    count = elements.size();
                }
            }
        }
        for (int i = 0; i < count; i++) {
            row.createCell(i + 2).setCellValue("依据" + (i + 1));
        }

        for (int i = 0; i < datas.size(); i++) {
            ArrayList<JSONObject> objects = datas.get(i);
            Row row1 = sheet.createRow(i + 1);

            int index = 0;
            for (int k = 0; k < objects.size(); k++) {
                String name = objects.get(k).getString("name");
                row1.createCell(index).setCellValue(name);
                index++;
                String diease = objects.get(k).getString("diease");
                row1.createCell(index).setCellValue(diease);
                index++;
                ArrayList<String> elements = (ArrayList) objects.get(k).get("elements");
                for (int l = 0; l < elements.size(); l++) {
                    row1.createCell(index).setCellValue(elements.get(l));
                    index++;
                }
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
