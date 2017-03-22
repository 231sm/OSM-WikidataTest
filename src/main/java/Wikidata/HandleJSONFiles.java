package Wikidata;

import com.lsj.trans.LANG;
import com.lsj.trans.Translator;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;
import org.junit.Before;

import java.io.*;

/**
 * Created by SmallApple on 2017/3/21.
 */
public class HandleJSONFiles {
    static TFactory factory = null;
    public static void setUp() throws Exception {
        factory = new TranslatorFactory();
    }
    //public static void infoExtByStringBuffer(String filePath, String encode, String xlsPath, int sheetNum) throws IOException {
    public static void infoExtByStringBuffer(String filePath, String encode, String txtPath) throws Exception {
        setUp();
        File file = new File(filePath);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file), 10 * 1024 * 1024);
            String stringLine = null;
            String newstr = null;
            while ((stringLine = reader.readLine()) != null) {
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
                System.out.print("ID " + buf + "\t");
                HandleFiles.WirteTxt(txtPath, "ID\r\t" + buf + "\r\tName\r\t");
                i = newstr.indexOf("en\":{\"language", i);
                if(i > 0) {
                    i += 30;
                    buf = "";
                    while (i < len && newstr.charAt(i) != '\"') {
                        buf = buf + newstr.charAt(i); //buf记录下这个entity的language
                        i++;
                    }
                    System.out.println("\tName " + buf);
                    HandleFiles.WirteTxt(txtPath, buf + "\r\n");
                }
                else {
                    i = newstr.indexOf("language", i);
                    i += 11;
                    buf = "";
                    while (i < len && newstr.charAt(i) != '\"') {
                        buf = buf + newstr.charAt(i); //buf记录下这个entity的language
                        i++;
                    }
                    i= newstr.indexOf("value", i);
                    //String buf2 = null;
                    String buf2 = "";
                    i += 8;
                    while (i < len && newstr.charAt(i) != '\"') {
                        buf2 = buf2 + newstr.charAt(i); //buf2记录下这个entity的name
                        i++;
                    }
                    if (!(buf.equals("en"))) { // 如果language不是英文，那么接着调用google翻译的API将其自动检测语言并翻译成英文存入txt文件
                        buf2 = factory.get("google").trans(LANG.auto, LANG.en, buf2);
                    }
                    System.out.println("\tName " + buf2);
                    HandleFiles.WirteTxt(txtPath, buf2 + "\r\n");
                }
            }
            reader.close();
            }
        catch(FileNotFoundException e){
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String filePath = "F:\\Data\\Wikidata\\first_100_lines.json";
        String encode = HandleFiles.getFileEncode(filePath);

        /*// 这是读取json的数据文件操作
        try {
            readFilesByStringBuffer(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // 这是对json的数据文件进行处理的输出结果
        //String xlsPath = "F:\\extractInfo\\GoogleTranslate.test.xls";
        //int sheetNum = 0;
        String txtPath = "F:\\WikidataTest.txt";
        try {
            //infoExtByStringBuffer(filePath, encode, xlsPath, sheetNum);
            infoExtByStringBuffer(filePath, encode, txtPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
