package Wikidata;

import FileHandle.HandleFiles;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;

import java.io.*;

/**
 * Created by SmallApple on 2017/3/21.
 */
public class HandleJSONFiles {
    /*static TFactory factory = null;
    public static void setUp() throws Exception {
        factory = new TranslatorFactory();
    }*/

    //public static void infoExtByStringBuffer (String filePath, String encode, String txtPath, String country) throws Exception {
    public static void tripleExt(String filePath, String encode, String CSVFilePath, String country) throws Exception {
        //setUp();
        FileOutputStream fos = new FileOutputStream(CSVFilePath, true);
        File file = new File(filePath);
        int all = 0;
        int zh = 0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file), 10 * 1024 * 1024);
            String stringLine = null;
            String newstr = null;
            while ((stringLine = reader.readLine()) != null) {
                all++; //all记录下wikidata中所有的实体数目
                newstr = new String(stringLine.getBytes(encode), encode).trim();
                int len = newstr.length();
                int i = newstr.indexOf("id");
                if (i < 0) {
                    continue;
                }
                //String buf = null;
                String buf = "";
                i += 5;
                while (i < len && newstr.charAt(i) != '\"') {
                    buf = buf + newstr.charAt(i); //buf记录下这个entity的id
                    i++;
                }
                i = newstr.indexOf(country + "\":{\"language", i);
                if(i > 0) {
                    zh++; //zh记录下wikidata中的中文实体数目
                    System.out.print("ID " + buf + "\t");
                    byte[] bufBytes = (buf+",").getBytes();
                    fos.write(bufBytes);
                    //HandleFiles.WirteTxt(txtPath, "ID\r\t" + buf);
                    i += 26 + 2*country.length();
                    String buf2 = "";
                    while (i < len && newstr.charAt(i) != '\"') {
                        buf2 = buf2 + newstr.charAt(i); //buf2记录下这个entity的name
                        i++;
                    }
                    System.out.println("\tName " + buf2);
                    byte[] bufBytes2 = (buf2+"\r\n").getBytes();
                    fos.write(bufBytes2);
                    //HandleFiles.WirteTxt(txtPath, "\r\tName\r\t" + buf2 + "\r\n");
                }
                else {
                    continue;
                }
            }
            reader.close();
            System.out.println("all\t" + all + "zh" + zh);
            HandleFiles.WirteTxt("F:/Wikiquantity.txt", country + "\r\n" + all + "\r\t" + zh + "\r\n");
            }
        catch(FileNotFoundException e){
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        //String filePath = "F:\\Data\\Wikidata\\first_100_lines.json";
        String filePath = "F:\\Data\\Wikidata\\wikidata-20170102-all.json";
        String encode = HandleFiles.getFileEncode(filePath);
        //String encode = "UTF-8";
        /*// 这是读取json的数据文件操作
        try {
            readFilesByStringBuffer(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 这是对json的数据文件进行处理的输出结果
        //String txtPath = "F:\\WikidataTest.txt";
        String CSVPath1 = "F:\\Wikidata_China.csv";
        String CSVPath2 = "F:\\Wikidata_Taiwan.csv";
        try {
            tripleExt(filePath, encode, CSVPath1, "zh-cn");
            tripleExt(filePath, encode, CSVPath2, "zh-tw");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
