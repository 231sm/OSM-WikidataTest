package OSM;

import FileHandle.HandleFiles;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;

import java.io.*;
import java.util.ArrayList;

import static FileHandle.HandleFiles.string2Unicode;


/**
 * Created by SmallApple on 2017/3/21.
 */
public class HandleOSMFiles {
    /*static TFactory factory = null;
    public static void setUp() throws Exception {
        factory = new TranslatorFactory();
    }*/

    public static String nameMatch (String OSMname, String WikiCSV, String encode) {
        File file = new File(WikiCSV);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(WikiCSV), 10 * 1024 * 1024);
            String stringLine = null;
            String newstr = null;
            while ((stringLine = reader.readLine()) != null) {
                newstr = new String(stringLine.getBytes(encode), encode).trim();
                int i = newstr.indexOf(",");
                int len = newstr.length();
                if(newstr.indexOf(OSMname) > 0) {
                    return newstr.substring(0,i);
                }
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //return false;
        return null;
    }

    public static void infoExt(String filePath, String encode, String CSVFilePath, String country, String WikiCSV, String triplePath) throws Exception {
        //setUp();
        FileOutputStream fos = new FileOutputStream(CSVFilePath, true);
        FileOutputStream triple = new FileOutputStream(triplePath, true);
        File file = new File(filePath);
        BufferedReader reader = null;
        String[] feature = {"node", "way", "relation"};
        int[][] quantity = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        /*
        quantity[0][0] = 有name且有wikidata链接的node id数量
        quantity[1][0] = 有name的node id数量
        quantity[2][0] = node id数量
        quantity[0][1] = 有name且有wikidata链接的way id数量
        quantity[1][1] = 有name的way id数量
        quantity[2][1] = way id数量
        quantity[0][2] = 有name且有wikidata链接的relation id数量
        quantity[1][2] = 有name的relation id数量
        quantity[2][2] = relation id数量
         */
        int num = 0;
        try {
            reader = new BufferedReader(new FileReader(file), 10 * 1024 * 1024);
            String stringLine = null;
            String newstr = null;
            while ((stringLine = reader.readLine()) != null) {
                newstr = new String(stringLine.getBytes(encode), encode).trim();
                for(int r=0; r<3; r++) {
                    if(newstr.indexOf(feature[r] + " id") > 0) {
                        quantity[2][r]++;
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
                            quantity[1][r]++;
                            int i = 17;
                            String buf = "";
                            while (newstr.charAt(i) != '\"') {//buf记录下node id/way id/relation id的name
                                buf = buf + newstr.charAt(i);
                                i++;
                            }
                            buf = string2Unicode(buf);
                            String buf2 = nameMatch(buf, WikiCSV, encode);
                            if(buf2 != null) {
                                num++;// 一个name可能对应着多个id，num是为了记录osm中有多少name存在于wikidata中
                            }
                            for(int k=0; k<j; k++) {
                                //System.out.println(feature[r] + "ID " + strGroup.get(k) + "\tName " + buf);
                                byte[] bufBytes = (feature[r] + "," + strGroup.get(k) + "," + buf + "\r\n").getBytes();
                                fos.write(bufBytes);
                                if(buf2 != null) {
                                    quantity[0][r]++;
                                    System.out.println(feature[r] + "\t" + strGroup.get(k) + "\t" + buf + "\t" + buf2);
                                    byte[] bufBytes2 = (feature[r] + "," + strGroup.get(k) + "," + buf + "," + buf2  + "\r\n").getBytes();
                                    triple.write(bufBytes2);
                                    /*
                                    triple中第一列：OSM feature的类别，node、way、relation
                                    triple中第二列：node id/way id/relation id
                                    triple中第三列：entity name
                                    triple中第四列：wikidata ID
                                     */
                                }
                            }
                        }
                    }
                }
            }
            reader.close();
            System.out.println("OSM_quantity:");
            System.out.println(quantity);
            System.out.println("num: " + num);
            HandleFiles.WirteTxt("F:/OSMquantity.txt", country + "\r\n");
            for(int i=0; i<3; i++) {
                for(int j=0; j<3; j++) {
                    HandleFiles.WirteTxt("F:/OSMquantity.txt", String.valueOf(quantity[i][j]) + "\r\t");
                }
                HandleFiles.WirteTxt("F:/OSMquantity.txt", "\r\n");
            }
            HandleFiles.WirteTxt("F:/OSMquantity.txt", String.valueOf(num) + "\r\n");
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
        //String txtPathTaiwan = "F:\\OSMTestTaiwan.txt";
        //String txtPathChina = "F:\\OSMTestChina.txt";
        String csvPathTaiwan = "F:\\OSM_Taiwan.csv";
        String csvPathChina = "F:\\OSM_China.csv";
        String WikiPathTaiwan = "F:\\Wikidata_Taiwan.csv";
        String WikiPathChina = "F:\\Wikidata_China.csv";
        String triplePathChina = "F:\\OSM-Wikidata_China.csv";
        String triplePathTaiwan = "F:\\OSM-Wikidata_Taiwan.csv";
        try {
            infoExt(filePathTaiwan, encodeT, csvPathTaiwan, "zh-tw", WikiPathTaiwan, triplePathTaiwan);
            infoExt(filePathChina, encodeC, csvPathChina, "zh-cn", WikiPathChina, triplePathChina);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
