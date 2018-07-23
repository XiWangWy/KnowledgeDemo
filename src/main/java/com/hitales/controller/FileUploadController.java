package com.hitales.controller;

import com.hitales.Repository.*;
import com.hitales.Utils.FileHelper;
import com.hitales.Utils.SetEntity;
import com.hitales.entity.*;
import com.hitales.write.ExportScripts;
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
import java.net.URLEncoder;
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

    @Autowired
    private TreatMentRepository treatMentRepository;

    @Autowired
    private GaiNianRepository gaiNianRepository;


    private AchieveGaiNianBelone achieveGaiNianBelone = new AchieveGaiNianBelone();

    private AchieveGaiNian1TOMany achieveGaiNian1TOMany = new AchieveGaiNian1TOMany();

    private AchieveGaiNian1TO1 achieveGaiNian1TO1 = new AchieveGaiNian1TO1();

    private AchieveDisease achieveDisease = new AchieveDisease();

    private AchieveGaiNianTY achieveGaiNianTY = new AchieveGaiNianTY();

    private AchieveTreatMent achieveTreatMent = new AchieveTreatMent();

    private GaiNianUtils gaiNianUtils = new GaiNianUtils();

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
                        if(row == null)
                            continue;
                        String Id;
                        if ("Origin".equals(type)){
                            Origin origin = new Origin();
                            SetEntity.setEntityVoid(origin,row);
                            String name = origin.getTYConcept();
                            if (gaiNianRepository.findByConcept(name) == null){
                                GaiNian gaiNian = new GaiNian();
                                gaiNian.setConcept(name);
                                gaiNianRepository.save(gaiNian);
                            }
                            orignRepository.save(origin);
                        }else if ("Disease".equals(type)){
                            if (row.getLastCellNum() > 2){
                                String name = row.getCell(0) == null?"":row.getCell(0).toString();
                                Disease disease = diseaseRepository.findByName(name);
                                disease = disease==null?new Disease():disease;

                                String condition = row.getCell(1) == null?"":row.getCell(1).toString();
                                ArrayList<String> list = new ArrayList<>();
                                for (int j = 2; j < row.getLastCellNum(); j++) {
                                    Cell cell = row.getCell(j);
                                    if (cell == null)break;
                                    list.add(cell.toString());
                                }
                                disease.setName(name);
                                disease.setCondition(condition);
                                disease.setElements(list);
                                for (String s : list){
                                    GaiNian gaiNian = gaiNianRepository.findByConcept(s);
                                    if (gaiNian == null){
                                        gaiNian = new GaiNian();
                                        gaiNian.setConcept(s);
                                        gaiNianRepository.save(gaiNian);
                                    }
                                }
                                diseaseRepository.save(disease);
                            }
                        }else if ("TreatMent".equals(type)){
                            if (row.getLastCellNum() > 2){
                                String name = row.getCell(0) == null?"":row.getCell(0).toString();
                                TreatMent treatMent =  treatMentRepository.findByName(name);
                                treatMent = treatMent == null?new TreatMent():treatMent;

                                String condition = row.getCell(1) == null?"":row.getCell(1).toString();
                                ArrayList<String> list = new ArrayList<>();
                                for (int j = 2; j < row.getLastCellNum(); j++) {
                                    Cell cell = row.getCell(j);
                                    if (cell == null)break;
                                    list.add(cell.toString());
                                }
                                treatMent.setName(name);
                                treatMent.setDiease(condition);
                                treatMent.setElements(list);
                                for (String s : list){
                                    GaiNian gaiNian = gaiNianRepository.findByConcept(s);
                                    if (gaiNian == null){
                                        gaiNian = new GaiNian();
                                        gaiNian.setConcept(s);
                                        gaiNianRepository.save(gaiNian);
                                    }
                                }
                                treatMentRepository.save(treatMent);
                            }
                        }else{
                            if (row.getLastCellNum() > 1 && row.getCell(0) != null){
                                ArrayList<String> list = new ArrayList<>();
                                String name = row.getCell(0).toString();
                                GaiNian gainian = gaiNianRepository.findByConcept(name);

                                gainian = gainian == null?new GaiNian():gainian;
                                for (int j = 1; j < row.getLastCellNum(); j++) {
                                    Cell cell = row.getCell(j);
                                    if (cell == null)break;
                                    list.add(cell.toString());
                                    if (gaiNianRepository.findByConcept(cell.toString())==null){
                                        GaiNian gaiNianChildren = new GaiNian();
                                        gaiNianChildren.setConcept(cell.toString());
                                        gaiNianRepository.save(gaiNianChildren);
                                    }
                                }

                                if (type.equals("GaiNian1TO1")){
                                    gainian.setOneToOne(list);
                                }else if (type.equals("GaiNian1TOMany")){
                                    gainian.setOneToMany(list);
                                } else if (type.equals("GaiNianBelone")) {
                                    gainian.setBelongs(list);
                                }else if (type.equals("GaiNianTY")){
                                    gainian.setTy(list);
                                }
                                gaiNianRepository.save(gainian);
                            }

                        }


//
//                        switch (type){
//                            case "Origin":
//                                Origin origin = new Origin();
//                                SetEntity.setEntityVoid(origin,row);
//                                orignRepository.save(origin);
//                                break;
//                            case "Disease":
//                                Disease disease = new Disease();
//                                SetEntity.setEntityVoid(disease,row);
//                                diseaseRepository.save(disease);
//                                break;
//                            case "GaiNian1TO1":
//                                GaiNian1TO1Entity gaiNian1TO1Entity = new GaiNian1TO1Entity();
//                                SetEntity.setEntityVoid(gaiNian1TO1Entity,row);
//                                gaiNian1TO1Repository.save(gaiNian1TO1Entity);
//                                break;
//                            case "GaiNian1TOMany":
//                                GaiNian1TOManyEntity gaiNian1TOManyEntity = new GaiNian1TOManyEntity();
//                                SetEntity.setEntityVoid(gaiNian1TOManyEntity,row);
//                                gaiNian1TOManyRepository.save(gaiNian1TOManyEntity);
//                                break;
//                            case "GaiNianBelone":
//                                GaiNianBeloneEntity gaiNianBeloneEntity = new GaiNianBeloneEntity();
//                                SetEntity.setEntityVoid(gaiNianBeloneEntity,row);
//                                List<String> list = gaiNianBeloneEntity.getBelongs();
//                                gaiNianBeloneRepository.save(gaiNianBeloneEntity);
//                                for (String concept : list){
//                                    gaiNianBeloneEntity = new GaiNianBeloneEntity();
//                                    gaiNianBeloneEntity.setConcept(concept);
//                                    gaiNianBeloneRepository.save(gaiNianBeloneEntity);
//                                }
//                                break;
//                            case "GaiNianTY":
//                                GaiNianTYEntity gaiNianTYEntity = new GaiNianTYEntity();
//                                SetEntity.setEntityVoid(gaiNianTYEntity,row);
//                                GaiNianTYEntity gaiNianTYEntityBefore = gaiNianTYRepository.findByConcept(gaiNianTYEntity.getConcept());
//                                Id = gaiNianTYEntityBefore == null? null :gaiNianTYEntityBefore.getId();
//                                gaiNianTYEntity.setId(Id);
//                                gaiNianTYRepository.save(gaiNianTYEntity);
//                                break;
//                            case "TreatMent":
//                                TreatMent treatMent = new TreatMent();
//                                SetEntity.setEntityVoid(treatMent,row);
//                                treatMentRepository.save(treatMent);
//                                break;
//                            default:
//                                break;
//                        }

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
    public String Download(HttpServletResponse res, @PathVariable("name") String name) throws UnsupportedEncodingException {
        String path = "";
        switch (name){
            case "GaiNianBelone":
                path = gaiNianUtils.writeBelong(gaiNianRepository);
                break;
            case "GaiNianTY":
                path = gaiNianUtils.writeTY(gaiNianRepository);
                break;
            case "GaiNian1TO1":
                path = gaiNianUtils.writeOneToOne(gaiNianRepository);
                break;
            case "GaiNian1TOMany":
                path = gaiNianUtils.writeOneToMany(gaiNianRepository);
                break;
            case "Disease":
                path = achieveDisease.writeDiseaseExcel(diseaseRepository);
                break;
            case "TreatMent":
                path = achieveTreatMent.writeExcelTreatMent(treatMentRepository,diseaseRepository);
                break;
            case "ExportScripts":
                ExportScripts exportScripts = new ExportScripts(diseaseRepository,treatMentRepository,gaiNianRepository);
                path = exportScripts.getOrigin();
            default:
                break;
        }
        String fileName = path.replaceAll(".*/","");

        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

//        FileWriter fw = null;
//        BufferedWriter bufferedWriter =null;
//        try {
//            StringBuilder sb = new StringBuilder();
//            fw = new FileWriter("./OriginExcel/test.txt");
//            bufferedWriter= new BufferedWriter(fw);
//            bufferedWriter.write(path);
//        }catch (Exception e){
//           e.printStackTrace();
//        }finally {
//            try {
//                bufferedWriter.close();
//                fw.close();
//            }catch (Exception e){
//               e.printStackTrace();
//            }
//        }

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
        return path;
    }


}
