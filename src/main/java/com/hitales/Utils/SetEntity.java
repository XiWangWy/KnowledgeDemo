package com.hitales.Utils;

import com.hitales.entity.Origin;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetEntity {
    String [] method = new String[]{"",""};



    public static void setEntityVoid(Object entity, Row row){
        Field [] fields =entity.getClass().getDeclaredFields();
        List<String> methodName = new ArrayList<>();
        Arrays.stream(fields).forEach(field -> methodName.add("set"+toUpperCaseFirstOne(field.getName())));
        try {
            for (int i = 0; i < row.getLastCellNum(); i++) {
                Cell cell = row.getCell(i);
                String value = cell == null?"":cell.toString();
                Method  method = entity.getClass().getMethod(methodName.get(i), String.class);
                method.invoke(entity, value);
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


//        Method [] methods = origin.getClass().getMethods();
//        for (int j = 0; j < row.getLastCellNum(); j++) {
//            Cell cell = row.getCell(j);
//            switch (j){
//                case 0:
//                    origin.setRID(cell == null?"":cell.toString());
//                    break;
//                case 1:
//                    origin.setEID(cell == null?"":cell.toString());
//                    break;
//                case 2:
//                    origin.setEntityType(cell == null?"":cell.toString());
//                    break;
//                case 3:
//                    origin.setFullInfo(cell == null?"":cell.toString());
//                    break;
//                case 4:
//                    origin.setField(cell == null?"":cell.toString());
//                    break;
//                case 5:
//                    origin.setFieldContent(cell == null?"":cell.toString());
//                    break;
//                case 6:
//                    origin.setTYConcept(cell == null?"":cell.toString());
//                    break;
//                default:
//                    break;
//            }
//
//        }
    }

    //首字母转大写
    public static String toUpperCaseFirstOne(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
