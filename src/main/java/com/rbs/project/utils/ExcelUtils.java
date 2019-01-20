package com.rbs.project.utils;

import com.rbs.project.pojo.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: WinstonDeng
 * @Description: POI处理Excel 各类版本兼容（.xls / .xlsx）
 * @Date: Created in 9:19 2018/12/13
 * @Modified by:
 */
public class ExcelUtils {

    /**
     * Description: 处理读取的excel,返回用户set集合
     * @Author: WinstonDeng
     * @Date: 9:25 2018/12/13
     */
    public static Set<Student> transExcelToSet(String filePath){
        Set<Student> students=new HashSet<>();
        Student student=null;
        Workbook wb=null;
        Sheet sheet=null;
        Row row=null;
        try{
            wb=readExcel(filePath);
            if(wb!=null){
                //获取第一个sheet
                sheet = wb.getSheetAt(0);
                //获取最大行数
                int rowNum = sheet.getPhysicalNumberOfRows();
                //对应学生名单excel奇怪的格式
                for(int i=2;i<rowNum;i++){
                    row=sheet.getRow(i);
                    if(row!=null){
                        //学生名单表格中很奇怪的字符，看不到，但存在，要除去
                        String outChar=" ";
                        student=new Student();
                        //学号
                        String account=(String)getCellFormatValue(row.getCell(0));
                        //姓名
                        String name=(String)getCellFormatValue(row.getCell(1));
                        student.setUsername(account.replace(outChar,""));
                        student.setStudentName(name.replace(outChar,""));
                        students.add(student);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return students;
    }

    /**
     * Description: 读取excel，返回工作簿
     * @Author: WinstonDeng
     * @Date: 9:22 2018/12/13
     */
    public static Workbook readExcel(String filePath){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            String xls=".xls";
            String xlsx=".xlsx";
            is = new FileInputStream(filePath);
            if(xls.equals(extString)){
                return wb = new HSSFWorkbook(is);
            }else if(xlsx.equals(extString)){
                return wb = new XSSFWorkbook(is);
            }else{
                return wb = null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
    /**
     * Description: 获得单元格格式数据
     * @Author: WinstonDeng
     * @Date: 9:23 2018/12/13
     */
    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            //判断cell类型

            switch(cell.getCellType()){
                case Cell.CELL_TYPE_NUMERIC:{
                    cellValue = String.valueOf(cell.getNumericCellValue());
                    break;
                }
                case Cell.CELL_TYPE_FORMULA:{
                    //判断cell是否为日期格式
                    if(DateUtil.isCellDateFormatted(cell)){
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    }else{
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case Cell.CELL_TYPE_STRING:{
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }

}
