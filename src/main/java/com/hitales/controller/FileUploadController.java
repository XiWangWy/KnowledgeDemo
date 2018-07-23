package com.hitales.controller;

import com.hitales.Repository.NotionMongoRepository;
import com.hitales.Utils.FileHelper;
import com.hitales.entity.Origin;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by wangxi on 18/7/3.
 */
@Controller
public class FileUploadController {

    @Autowired
    private NotionMongoRepository notionMongoRepository;

    /**
     * 上传单个文件
     * @return
     */
    @RequestMapping(value = "/upload",method = {RequestMethod.GET})
    public ModelAndView upload() {




        return new ModelAndView("uploading");
    }

    @RequestMapping(value = "/downloadhtml",method = RequestMethod.GET)
    public ModelAndView download(){

        return new ModelAndView("downloading");
    }

    /**
     * 上传单个文件
     * @return
     */
//    @RequestMapping(value = "/simpleupload",method = {RequestMethod.GET})
//    public ModelAndView multupload() {
//        return new ModelAndView("multiUploading");
//    }

//    @RequestMapping(value = "/simpleupload",method = RequestMethod.POST)
//    public @ResponseBody String simpleUpload(@RequestParam("file") MultipartFile file){
//        String targetPath = "./uploadFiles/";
//        String msg = FileHelper.writeClientDataToPath(file,targetPath);
//        return msg;
//    }

    /**
     * 上传文件即可以单个也可以多个同时上传
     * @param request
     * @return
     */
    @RequestMapping(value = "uploadOrigin",method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        try {
            for (MultipartFile file1 : files) {
                if (Pattern.compile(".*(xls|xlsx|xlsm)$").matcher(file1.getOriginalFilename()).matches()) {
                    InputStream inputStream =file1.getInputStream();
                    XSSFWorkbook hssfWorkbook = new XSSFWorkbook(inputStream);
                    XSSFSheet sheetexcel = hssfWorkbook.getSheetAt(0);
                    for (int i = 1; i <= sheetexcel.getLastRowNum(); i++) {
                        Row row = sheetexcel.getRow(i);
                        Origin origin = new Origin();
                        for (int j = 0; j < row.getLastCellNum(); j++) {
                            Cell cell = row.getCell(j);
                            switch (j){
                                case 0:
                                    origin.setRID(cell == null?"":cell.toString());
                                    break;
                                case 1:
                                    origin.setEID(cell == null?"":cell.toString());
                                    break;
                                case 2:
                                    origin.setEntityType(cell == null?"":cell.toString());
                                    break;
                                case 3:
                                    origin.setFullInfo(cell == null?"":cell.toString());
                                    break;
                                case 4:
                                    origin.setField(cell == null?"":cell.toString());
                                    break;
                                case 5:
                                    origin.setFieldContent(cell == null?"":cell.toString());
                                    break;
                                case 6:
                                    origin.setTYConcept(cell == null?"":cell.toString());
                                    break;
                                default:
                                    break;
                            }
                        }
                        if(row.getLastCellNum() <= 0)
                            continue;
                        notionMongoRepository.save(origin);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


//            String targetPath = "./uploadFiles/";
//            FileHelper.writeClientDataToPath(file,targetPath);

        return "upload successful";
    }

    /**
     * 下载文件
     * @param res
     */
    @RequestMapping(value = "download", method = RequestMethod.GET)
    public void Download(HttpServletResponse res) {
        String fileName = "time.png";
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os;
//        String targetPath = "";
        File downLoadFile = new File(fileName);
        if (!downLoadFile.exists()){
            try {
                downLoadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(downLoadFile));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }


}
