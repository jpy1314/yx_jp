package cn.baizhi.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.entity.User;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class EasyPoi {
    public  static void EasyPoi(List<User> list){
        //参数：标题，表名，实体类类对象，导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("学生信息","学生表"),User.class,list);
try {
    workbook.write(new FileOutputStream(new File("F:/easypoi.xls")));
    workbook.close();
}catch (Exception e)
{e.printStackTrace();}

    }
}
