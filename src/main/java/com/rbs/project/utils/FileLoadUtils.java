package com.rbs.project.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: WinstonDeng
 * @Description: OOAD_Course_ManageSystem
 * @Date: Created in 13:10 2018/12/12
 * @Modified by:
 */

public class FileLoadUtils {


    /**
     * Description: 单文件上传
     * @Author: WinstonDeng
     * @Date: 13:11 2018/12/12
     */
    public static String upload(String filePath,MultipartFile file){

        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();

            System.out.println("上传的文件名为：" + fileName);
            //logger.info("上传的文件名为：" + fileName); //日志记录
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            //logger.info("文件的后缀名为：" + suffixName); //日志记录
            System.out.println("文件的后缀名为：" + suffixName);

            //fileName中有后缀，所以没必要再加suffixName
            System.out.println(filePath);
            String path = filePath + fileName ;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            System.out.println(dest.getName());
            // 文件写入
            if(!dest.exists()){
                file.transferTo(dest);
            }
            return fileName;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Description: 多文件上传    ！！！暂时不用
     * @Author: WinstonDeng
     * @Date: 13:13 2018/12/12
     */
    public static String handleFileUpload(List<MultipartFile> files, String filePath){

        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);

            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            //设置文件路径及名字
                            new File(filePath + file.getOriginalFilename())));
                    // 写入
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "第 " + i + " 个文件上传失败  ==> "
                            + e.getMessage();
                }
            } else {
                return "第 " + i
                        + " 个文件上传失败因为文件为空";
            }
        }
        return "上传成功";
    }
    /**
     * Description: 文件下载
     * @Author: WinstonDeng
     * @Date: 13:16 2018/12/12
     */
    public static String downloadFile(HttpServletRequest request,HttpServletResponse response, String realPath, String fileName) throws UnsupportedEncodingException {
        System.out.println(realPath);
        System.out.println(fileName);
        if (fileName != null) {
            File file = new File(realPath , fileName);
            if (file.exists()) {
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("success");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            else {
                System.out.println("未找到");
            }
        }
        return "下载错误";
    }

}
