package OSM;

import FileHandle.HandleFiles;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by SmallApple on 2017/3/21.
 */
public class HandleOSMFiles {
    /*static TFactory factory = null;
    public static void setUp() throws Exception {
        factory = new TranslatorFactory();
    }*/
    public static void infoExtByStringBuffer(String filePath, String encode, String CSVFilePath, String country) throws Exception {
        //setUp();
        FileOutputStream fos = new FileOutputStream(CSVFilePath, true);
        File file = new File(filePath);
        BufferedReader reader = null;
        String[] feature = {"node", "way", "relation"};
        try {
            reader = new BufferedReader(new FileReader(file), 10 * 1024 * 1024);
            String stringLine = null;
            String newstr = null;
            while ((stringLine = reader.readLine()) != null) {
                newstr = new String(stringLine.getBytes(encode), encode).trim();
                for(int r=0; r<3; r++) {
                    if(newstr.indexOf(feature[r] + " id") > 0) {
                        int j = 0;
                        ArrayList strGroup = new ArrayList();
                        while(newstr.indexOf("tag k=\"name\" v=\"") < 0 && newstr.indexOf("</" + feature[r] + ">") < 0) {
                            if(newstr.indexOf(feature[r] + " id") > 0) {
                                int i = feature[r].length() + 6;
                                String buf = "";
                                while (newstr.charAt(i) != '\"') { // buf记录下node id/way id/relation id
                                    buf = buf + newstr.charAt(i);
                                    i++;
                                }
                                strGroup.add(buf); //将可能共有相同name的node id/way id/relation id全部记录在strGroup字符数组中
                                j++;
                            }
                            stringLine = reader.readLine();
                            newstr = new String(stringLine.getBytes(encode), encode).trim();
                        }
                        if(newstr.indexOf("tag k=\"name\" v=\"") > 0) { //如果这些node id/way id/relation id确实有name
                            int i = 17;
                            String buf = "";
                            while (newstr.charAt(i) != '\"') {//buf记录下node id的name
                                buf = buf + newstr.charAt(i);
                                i++;
                            }
                            for(int k=0; k<j; k++) {
                                System.out.println(feature[r] + "ID " + strGroup.get(k) + "\tName " + buf);
                                byte[] bufBytes = (strGroup.get(k) + "," + buf + "\r\n").getBytes();
                                fos.write(bufBytes);
                            }
                        }
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
        }
    }

    public static void main(String[] args) throws Exception {
        String filePathTaiwan = "F:\\Data\\OSM\\taiwan-latest.osm";
        String encodeT = HandleFiles.getFileEncode(filePathTaiwan);
        String filePathChina = "F:\\Data\\OSM\\china-latest.osm";
        String encodeC = HandleFiles.getFileEncode(filePathChina);
        /*// 这是读取osm的数据文件操作
        try {
            readFilesByStringBuffer(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 这是对osm的数据文件进行处理的输出结果
        //String xlsPath = "F:\\extractInfo\\GoogleTranslate.test.xls";
        //int sheetNum = 0;
        String txtPathTaiwan = "F:\\OSMTestTaiwan.txt";
        String txtPathChina = "F:\\OSMTestChina.txt";
        String csvPathTaiwan = "F:\\OSMTestTaiwan.csv";
        String csvPathChina = "F:\\OSMTestChina.csv";
        try {
            infoExtByStringBuffer(filePathTaiwan, encodeT, csvPathTaiwan, "zh-cn");
            infoExtByStringBuffer(filePathChina, encodeC, csvPathChina, "zh-tw");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
