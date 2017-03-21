package test;

/**
 * Created by SmallApple on 2017/3/20.
 */

import Wikidata.HandleFiles;
import com.lsj.trans.LANG;
import com.lsj.trans.Translator;
import com.lsj.trans.factory.TFactory;
import com.lsj.trans.factory.TranslatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static OSM.HandleOSMFiles.infoExtByStringBuffer;

public class TransTest {
    TFactory factory = null;

    @Before
    public void setUp() throws Exception {
        factory = new TranslatorFactory();
    }

    @Test
    public void test() throws Exception {
        String origin = "Xamarin.Forms has several layouts and features for organizing content on screen.\n Each layout control is described below, as well as details on how to handle screen orientation changes:";
        System.out.println("金山 : " + factory.get("jinshan").trans(LANG.en, LANG.zh, origin));
        System.out.println("有道 : " + factory.get("youdao").trans(LANG.en, LANG.zh, origin));
        System.out.println("百度 : " + factory.get("baidu").trans(LANG.en, LANG.zh, origin));
        System.out.println("谷歌 : " + factory.get("google").trans(LANG.en, LANG.zh, origin));
        System.out.println("腾讯 : " + factory.get("tencent").trans(LANG.en, LANG.zh, origin));
        System.out.println("\n");

        origin = "这个巡展，大家想去的先注册，临近当地巡展时候，会有注册确认邮件发给大家，届时可以看到详细的活动地址。\n等你工作了你就知道你当时的观念是多么的幼稚";
        System.out.println("金山 : " + factory.get("jinshan").trans(LANG.zh, LANG.en, origin));
        System.out.println("有道 : " + factory.get("youdao").trans(LANG.zh, LANG.en, origin));
        System.out.println("百度 : " + factory.get("baidu").trans(LANG.zh, LANG.en, origin));
        System.out.println("谷歌 : " + factory.get("google").trans(LANG.zh, LANG.en, origin));
        System.out.println("腾讯 : " + factory.get("tencent").trans(LANG.zh, LANG.en, origin));

        System.out.println("谷歌 : " + factory.get("google").trans(LANG.auto, LANG.eu, origin));
    }

	/*@Test
	public void testTime() throws Exception {
		String origin = "Xamarin.Forms has several layouts and features for organizing content on screen. Each layout control is described below, as well as details on how to handle screen orientation changes:";
		Translator lator = factory.get("baidu");
		int cnt = 0;

		while(true){
			cnt++;
			System.out.println(cnt + " : "+lator.trans(LANG.en, LANG.zh, origin));
			Thread.sleep(10);
		}
	}*/

}
