package cn.baizhi.test;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
public class testPOI {
    @Test
    public void test() throws IOException {
        //创建一个Excel
        HSSFWorkbook workbook= new HSSFWorkbook();
        workbook.write(new FileOutputStream("F:\\poi.xls"));
        //创建一个工作表
        HSSFSheet sheet = workbook.createSheet("学生表");
        //创建一个单元格
        HSSFRow row = sheet.createRow(1);




    }
}
