/**
 * Created by SmallApple on 2017/3/19.
 */

import java.io.*;
import jxl.*;
import jxl.write.*;

public class HandleXLS {
    public static void CreateXLS(String xlsFilePath, String sheetName, int sheetNum, String item, int col, int row) {
        try {
            // 打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File(xlsFilePath));
            // 生成名为sheetName的工作表，参数item表示这是第几页,例如参数0表示这是第1页
            WritableSheet sheet=book.createSheet(sheetName, sheetNum);
            // 在Label对象的构造子中指名单元格位置是第row+1列第col+1行(col,row),例如第一列第一行(0,0). 以及单元格内容为item
            Label label = new Label(col, row, item);
            // 将定义好的单元格添加到工作表中
            sheet.addCell(label);
            // 将数字写入单元格，例如下面的语句就是将789.123写入第2列第1行
            // 生成一个保存数字的单元格，必须使用Number的完整包路径，否则有语法歧义
            //jxl.write.Number number = new jxl.write.Number(1,0,789.123);
            //sheet.addCell(number);
            //写入数据并关闭文件
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // 编译执行后，会在当前位置产生一个Excel文件。

    // 读取文件, 读取Excel的类
    public static void ReadXLS(String xlsFilePath, int sheetNum, int col, int row) {
        try {
            Workbook book = Workbook.getWorkbook(new File(xlsFilePath));
            //获得第sheetNum+1个工作表对象
            Sheet sheet = book.getSheet(sheetNum);
            //得到第col+1列第row+1行的单元格
            Cell cell1 = sheet.getCell(col, row);
            String result = cell1.getContents();
            System.out.println(result);
            book.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    // 修改文件
    // 利用jExcelAPI可以修改已有的Excel文件，修改Excel文件的时候，除了打开文件的方式不同之外，其他操作和创建Excel是一样的。
    // 下面的例子是在已经生成的Excel文件中添加一个工作表：修改Excel的类，添加一个工作表
    public static void UpdateXLS(String xlsFilePath, String sheetName, int sheetNum, String item, int col, int row) {
        try {
            //Excel获得文件
            Workbook wb = Workbook.getWorkbook(new File(xlsFilePath));
            //打开一个文件的副本，并且指定数据写回到原文件
            WritableWorkbook book = Workbook.createWorkbook(new File(xlsFilePath), wb);
            //添加一个工作表
            WritableSheet sheet = book.createSheet(sheetName, sheetNum);
            sheet.addCell(new Label(col, row, item));
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    /*public static void main(String args[]) {
        System.out.println("hah");
    }*/
}
