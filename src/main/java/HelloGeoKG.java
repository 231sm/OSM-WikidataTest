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
    public static String getFileEncode(String path) {
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar
        // ASCIIDetector用于ASCII编码测定
        detector.add(ASCIIDetector.getInstance());
        // UnicodeDetector用于Unicode家族编码的测定
        detector.add(UnicodeDetector.getInstance());
        java.nio.charset.Charset charset = null;
        File f = new File(path);
        try {
            charset = detector.detectCodepage(f.toURI().toURL());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (charset != null)
            return charset.name();
        else
            return null;
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        System.out.printf("This project is to study (1) the linkage between OSM and WikiData, " +
                "(2) the linkage between OSM and POIs \n");
        System.out.println("Have Fun!");
        String str = "dsm";
        System.out.println(str.length());
        /*String str = "\\u0418\\u043d\\u0442\\u0435\\u0440\\u043d\\u0435\\u0442";
        //String str = "dsm";
        String encodelist = getcode(str);
        System.out.println(encodelist);
        String s = new String(str.getBytes("UTF-8"), "UTF-8");
        System.out.println(s);
        //String filePath = "F:\\Data\\Wikidata\\first_100_lines.json";
        //String filePath = "F:\\Data\\OSM\\cambodia-latest.osm";
        String filePath = "F:\\Data\\OSM\\cambodia-latest-free.shp\\gis.osm_buildings_a_free_1.dbf";
        System.out.println(getFileEncode(filePath));*/
    }
}
