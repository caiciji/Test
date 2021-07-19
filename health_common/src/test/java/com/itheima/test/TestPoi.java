package com.itheima.test;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class TestPoi {

    //读取excel表格的内容
    @Test
    public void testRead01() throws IOException {

        //1.创建工作傅
        XSSFWorkbook wb=new XSSFWorkbook("C:\\itheima\\java114_SEPlus\\预习day51_传智健康\\04_资料\\read.xlsx");

        //2.得到工作表 得到第一个工作表
        XSSFSheet sheet=wb.getSheetAt(0);

        //3.遍历工作表得到行
        for (Row row : sheet) {

            //4.遍历行得到格子，可以取数据了
            for (Cell cell : row) {
                String value = cell.getStringCellValue();
                System.out.println("value = " + value);
            }
        }

        //关闭工作薄
        wb.close();
    }

    @Test
    public void testRead02() throws IOException {

        //1.创建工作薄
        XSSFWorkbook wb=new XSSFWorkbook("C:\\itheima\\java114_SEPlus\\预习day51_传智健康\\04_资料\\read.xlsx");

        //得到工作表，得到第一个工作表。
        XSSFSheet sheet=wb.getSheetAt(0);

        //3.得到最后一行的下标
        int lastRowNum = sheet.getLastRowNum();//1

        //行号从0开始.
        for (int i = 0; i <= lastRowNum; i++) {
            //取出来每一行
            XSSFRow row = sheet.getRow(i);

            //获取最后一个格子的下标
            short lastCellNum = row.getLastCellNum();

            for(int j=0;j<lastCellNum;j++){
                System.out.println(row.getCell(j).getStringCellValue());
            }
        }

        wb.close();
    }

    //生成Excel表格
    @Test
    public void testWrite() throws IOException {

        //1.定义工作薄
        XSSFWorkbook wb=new XSSFWorkbook();

        //2.创建工作表
        XSSFSheet sheet=wb.createSheet("传智播客");

        //3.构建第一行,填充格子的内容
        XSSFRow row=sheet.createRow(0);
        row.createCell(0).setCellValue("编号");
        row.createCell(1).setCellValue("姓名");
        row.createCell(2).setCellValue("年龄");

        //第二行
        XSSFRow row1=sheet.createRow(1);
        row1.createCell(0).setCellValue(1);
        row1.createCell(1).setCellValue("张三");
        row1.createCell(2).setCellValue(18);


        //第三行

        XSSFRow row2=sheet.createRow(2);
        row2.createCell(0).setCellValue(2);
        row2.createCell(1).setCellValue("李四");
        row2.createCell(2).setCellValue(28);

        //4.把工作薄保存到硬盘上的某格文件去
        FileOutputStream fos=new FileOutputStream("C:\\itheima\\java114_SEPlus\\预习day51_传智健康\\04_资料\\aa.xlsx");
        wb.write(fos);


        //5.收尾
        fos.flush();
        fos.close();
        wb.close();
    }
}
