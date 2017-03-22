package test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import FileHandle.CSVUtils;
/**
 * Created by SmallApple on 2017/3/22.
 */
public class CSVTest {
    /**
     * CSV导出
     *
     * @throws Exception
     */
    @Test
    public void exportCsv() {
        List<String> dataList = new ArrayList<String>();
        dataList.add("1,张三,男");
        dataList.add("2,李四,男");
        dataList.add("3,小红,女");
        boolean isSuccess = CSVUtils.exportCsv(new File("F:/test/test.csv"), dataList);
        System.out.println(isSuccess);
    }

    /**
     * CSV导出
     *
     * @throws Exception
     */
    @Test
    public void importCsv()  {
        List<String> dataList = CSVUtils.importCsv(new File("F:/test/test.csv"));
        if(dataList != null && !dataList.isEmpty()){
            for(String data : dataList){
                System.out.println(data);
            }
        }
    }
}
