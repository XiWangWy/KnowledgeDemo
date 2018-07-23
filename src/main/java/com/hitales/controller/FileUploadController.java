package com.hitales.controller;

import com.hitales.Repository.*;
import com.hitales.Utils.FileHelper;
import com.hitales.Utils.SetEntity;
import com.hitales.entity.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    private DiseaseRepository diseaseRepository;

    @Autowired
    private GaiNian1TO1Repository gaiNian1TO1Repository;

    @Autowired
    private GaiNian1TOManyRepository gaiNian1TOManyRepository;

    @Autowired
    private GaiNianBeloneRepository gaiNianBeloneRepository;

    @Autowired
    private OrignRepository orignRepository;

    @Autowired
    private GaiNianTYRepository gaiNianTYRepository;


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
    @RequestMapping(value = "upload/{type}",method = RequestMethod.POST)
    @ResponseBody
    public String uploadOrigin(HttpServletRequest request,@PathVariable("type") String type){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        try {
            for (MultipartFile file1 : files) {
                if (Pattern.compile(".*(xls|xlsx|xlsm)$").matcher(file1.getOriginalFilename()).matches()) {
                    InputStream inputStream =file1.getInputStream();
                    XSSFWorkbook hssfWorkbook = new XSSFWorkbook(inputStream);
                    XSSFSheet sheetexcel = hssfWorkbook.getSheetAt(0);
                    for (int i = 1; i <= sheetexcel.getLastRowNum(); i++) {
                        Row row = sheetexcel.getRow(i);
                        if(row.getLastCellNum() <= 0)
                            continue;
                        String Id;
                        switch (type){
                            case "Origin":
                                Origin origin = new Origin();
                                SetEntity.setEntityVoid(origin,row);
                                orignRepository.save(origin);
                                break;
                            case "Disease":
                                Disease disease = new Disease();
                                SetEntity.setEntityVoid(disease,row);
                                diseaseRepository.save(disease);
                                break;
                            case "GaiNian1TO1":
                                GaiNian1TO1Entity gaiNian1TO1Entity = new GaiNian1TO1Entity();
                                SetEntity.setEntityVoid(gaiNian1TO1Entity,row);
                                gaiNian1TO1Repository.save(gaiNian1TO1Entity);
                                break;
                            case "GaiNian1TOMany":
                                GaiNian1TOManyEntity gaiNian1TOManyEntity = new GaiNian1TOManyEntity();
                                SetEntity.setEntityVoid(gaiNian1TOManyEntity,row);
                                gaiNian1TOManyRepository.save(gaiNian1TOManyEntity);
                                break;
                            case "GaiNianBelone":
                                GaiNianBeloneEntity gaiNianBeloneEntity = new GaiNianBeloneEntity();
                                SetEntity.setEntityVoid(gaiNianBeloneEntity,row);
                                gaiNianBeloneRepository.save(gaiNianBeloneEntity);
                                break;
                            case "GaiNianTY":
                                GaiNianTYEntity gaiNianTYEntity = new GaiNianTYEntity();
                                SetEntity.setEntityVoid(gaiNianTYEntity,row);
                                GaiNianTYEntity gaiNianTYEntityBefore = gaiNianTYRepository.findByConcept(gaiNianTYEntity.getConcept());
                                Id = gaiNianTYEntityBefore == null? null :gaiNianTYEntityBefore.getId();
                                gaiNianTYEntity.setId(Id);
                                gaiNianTYRepository.save(gaiNianTYEntity);
                                break;
                            default:
                                break;
                        }

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
    @RequestMapping(value = "download/{name}", method = RequestMethod.GET)
    public void Download(HttpServletResponse res, @PathVariable("name") String name) {
        String path = "";
        switch (name){
            case "Origin":
                break;
            case "GaiNianBelone":
                break;
            case "GaiNianTY":
                break;
            case "GaiNian1TO1":
                break;
            case "GaiNian1TOMany":
                break;
            case "Disease":
                break;
            default:
                break;
        }
        String fileName = path.replaceAll(".*/","");

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os;
        File downLoadFile = new File(path);
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
