/**
 * Created by John on 3/15/17.
 */
import info.monitorenter.cpdetector.io.*;

import java.io.*;

public class HelloGeoKG {
    //getcode函数用于识别字符串的字符编码
    public static String getcode(String str) {
        String[] encodelist = {"GB2312", "ISO-8859-1", "UTF-8", "GBK", "gb 18030", "Big5", "UTF-16LE", "Shift_JIS", "EUC-JP", "ISO-2002-JP"};
        for (int i = 0; i < encodelist.length; i++) {
            try {
                if (str.equals(new String(str.getBytes(encodelist[i]), encodelist[i]))) {
                    return encodelist[i];
                }
            } catch (Exception e) {
            } finally {
            }
        }
        return "";
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        System.out.printf("This project is to study (1) the linkage between OSM and WikiData, " +
                "(2) the linkage between OSM and POIs \n");
        System.out.println("Have Fun!");
    }
}
