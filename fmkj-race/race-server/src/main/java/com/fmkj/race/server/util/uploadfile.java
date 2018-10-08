package com.fmkj.race.server.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/*
 * @ Author     ：yangshengbin
 * @ Date       ：17:03 2018/8/30 0030
 * @ Description：文件上传
 */


public class uploadfile {


    public  String handleFileUpload(HttpServletRequest request){
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i =0; i< files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream =
                            new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));

                    UUID uuid = UUID.randomUUID();
                    String originalFilename =uuid.toString().replaceAll("-", "");

                    Long time = new Date().getTime();//获取当前时间为标识
                    String url = "D:\\soft\\apache-tomcat-8.5.24\\webapps\\activityImage";
                    String newFileName = time.toString()+originalFilename;//新的文件名
                    File targetFile = new File(url,newFileName); //封装上传文件位置的全路径

                    file.transferTo(targetFile);//把本地文件上传到 封装上传文件位置的全路径 下
                    stream.close();
                } catch (Exception e) {
                    stream =  null;
                    return "You failed to upload " + i + " =>" + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " becausethe file was empty.";
            }
        }
        return "upload successful";

    }


    public  String uploadImage(HttpServletRequest request){
        List<MultipartFile> files =((MultipartHttpServletRequest)request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i =0; i< files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream =
                            new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));

                    UUID uuid = UUID.randomUUID();
                    String originalFilename =uuid.toString().replaceAll("-", "");

                    Long time = new Date().getTime();//获取当前时间为标识
                    String url = "D:\\soft\\apache-tomcat-8.5.24\\webapps\\activityImage";
                    String newFileName = time.toString()+originalFilename;//新的文件名
                    File targetFile = new File(url,newFileName); //封装上传文件位置的全路径

                    file.transferTo(targetFile);//把本地文件上传到 封装上传文件位置的全路径 下
                    stream.close();
                } catch (Exception e) {
                    stream =  null;
                    return "You failed to upload " + i + " =>" + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " becausethe file was empty.";
            }
        }
        return "upload successful";

    }






}
