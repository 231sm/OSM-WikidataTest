package OSM;

import FileHandle.HandleFiles;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;

import java.io.*;


/**
 * Created by SmallApple on 2017/3/21.
 */
public class HandleOSMFiles {
    /*static TFactory factory = null;
    public static void setUp() throws Exception {
        factory = new TranslatorFactory();
    }*/
    public static void infoExtByStringBuffer(String filePath, String encode, String CSVFilePath, String country) throws Exception {
    //public static void infoExtByStringBuffer(String filePath, String encode, String xlsPath, int sheetNum) throws IOException {
    //public static void infoExtByStringBuffer(String filePath, String encode, String txtPath) throws Exception {
        //setUp();
        FileOutputStream fos = new FileOutputStream(CSVFilePath, true);
        int k = 2; // 这是为了提取出有name的node、way、relation而设置的判断参数
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file), 10 * 1024 * 1024);
            String stringLine = null;
            String newstr = null;
            while ((stringLine = reader.readLine()) != null) {
                newstr = new String(stringLine.getBytes(encode), encode).trim();
                if (newstr.length() > 8 && newstr.substring(1, 8).equals("node id")) {
                    int i = 10;
                    //String buf = null;
                    String buf = "";
                    while (newstr.charAt(i) != '\"') {
                        buf = buf + newstr.charAt(i);
                        i++;
                    }
                    if(k == 2) {
                        System.out.print("NodeID " + buf + "\t");
                        byte[] bufBytes = (buf+",").getBytes();
                        fos.write(bufBytes);
                        //HandleXLS.CreateXLS(xlsPath, "OSM-ID_Name", sheetNum, buf, 0, j);
                        //HandleFiles.WirteTxt(txtPath,"NodeID\r\t" + buf + "\r\tName\r\t");
                    }
                    k = 1;
                }
                if (newstr.length() > 7 && newstr.substring(1, 7).equals("way id")) {
                    int i = 9;
                    //String buf = null;
                    String buf = "";
                    while (newstr.charAt(i) != '\"') {
                        buf = buf + newstr.charAt(i);
                        i++;
                    }
                    if(k == 2) {
                        System.out.print("WayID " + buf + "\t");
                        byte[] bufBytes = (buf+",").getBytes();
                        fos.write(bufBytes);
                        //HandleXLS.CreateXLS(xlsPath, "OSM-ID_Name", sheetNum, buf, 0, j);
                        //HandleFiles.WirteTxt(txtPath,"WayID\r\t" + buf + "\r\tName\r\t");
                    }
                    k = 1;
                }
                if (newstr.length() > 12 && newstr.substring(1, 12).equals("relation id")) {
                    int i = 14;
                    //String buf = null;
                    String buf = "";
                    while (newstr.charAt(i) != '\"') {
                        buf = buf + newstr.charAt(i);
                        i++;
                    }
                    if(k == 2) {
                        System.out.print("RelationID " + buf + "\t");
                        byte[] bufBytes = (buf+",").getBytes();
                        fos.write(bufBytes);
                        //HandleXLS.CreateXLS(xlsPath, "OSM-ID_Name", sheetNum, buf, 0, j);
                        //HandleFiles.WirteTxt(txtPath,"RelationID\r\t" + buf + "\r\tName\r\t");
                    }
                    k = 1;
                }
                if (newstr.length() > 17 && newstr.substring(1, 17).equals("tag k=\"name\" v=\"")) {
                    int i = 17;
                    k = 2;
                    //String buf = null;
                    String buf = "";
                    while (newstr.charAt(i) != '\"') {
                        buf = buf + newstr.charAt(i);
                        i++;
                    }
                    byte[] bufBytes = (buf+"\r\n").getBytes();
                    fos.write(bufBytes);
                    // 将name自动翻译成英文，因为语言很多，难以与Wikidata中的entity vaule进行有效匹配
                    //buf = factory.get("google").trans(LANG.auto, LANG.en, buf);
                    System.out.println("Name " + buf);
                    //HandleXLS.CreateXLS(xlsPath, "OSM-ID_Name", sheetNum, buf, 1, j++);
                    //HandleFiles.WirteTxt(txtPath, buf + "\r\n");
                    k = 2;
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
