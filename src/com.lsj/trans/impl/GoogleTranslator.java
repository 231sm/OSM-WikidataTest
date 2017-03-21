package com.lsj.trans.impl;
/**
 * Created by SmallApple on 2017/3/20.
 */
import com.lsj.http.HttpParams;
import com.lsj.http.HttpPostParams;
import com.lsj.trans.AbstractOnlineTranslator;
import com.lsj.trans.LANG;
import com.lsj.trans.annotation.TranslatorComponent;
import net.sf.json.JSONArray;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

@TranslatorComponent(id = "google")
final public class GoogleTranslator extends AbstractOnlineTranslator {
	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
	
	public GoogleTranslator(){
		langMap.put(LANG.en, "en");
		langMap.put(LANG.zh, "zh-CN");
		langMap.put(LANG.ru, "ru");
		langMap.put(LANG.auto, "auto");
		langMap.put(LANG.sq, "sq");//阿尔巴尼亚语
		langMap.put(LANG.ar, "ar");//阿拉伯语
		langMap.put(LANG.az, "az");//阿塞拜疆语
		langMap.put(LANG.ga, "ga");//爱尔兰语
		langMap.put(LANG.et, "et");//爱沙尼亚语
		langMap.put(LANG.be, "be");//白俄罗斯语
		langMap.put(LANG.bg, "bg");//保加利亚语
		langMap.put(LANG.is, "is");//冰岛语
		langMap.put(LANG.pl, "pl");//波兰语
		langMap.put(LANG.fa, "fa");//波斯语
		langMap.put(LANG.af, "af");//布尔语(南非荷兰语)
		langMap.put(LANG.da, "da");//丹麦语
		langMap.put(LANG.de, "de");//德语
		//langMap.put(LANG.ru, "ru");//俄语
		langMap.put(LANG.fr, "fr");//法语
		langMap.put(LANG.tl, "tl");//菲律宾语
		langMap.put(LANG.fi, "fi");//芬兰语
		langMap.put(LANG.ka, "ka");//格鲁吉亚语
		langMap.put(LANG.gu, "gu");//古吉拉特语
		langMap.put(LANG.ht, "ht");//海地克里奥尔语
		langMap.put(LANG.ko, "ko");//韩语
		langMap.put(LANG.nl, "nl");//荷兰语
		langMap.put(LANG.gl, "gl");//加利西亚语
		langMap.put(LANG.ca, "ca");//加泰罗尼亚语
		langMap.put(LANG.cs, "cs");//捷克语
		langMap.put(LANG.hr, "hr");//克罗地亚语
		langMap.put(LANG.la, "la");//拉丁语
		langMap.put(LANG.lv, "lv");//拉脱维亚语
		langMap.put(LANG.lt, "lt");//立陶宛语
		langMap.put(LANG.ro, "ro");//罗马尼亚语
		langMap.put(LANG.mt, "mt");//马耳他语
		langMap.put(LANG.ms, "ms");//马来语
		langMap.put(LANG.mk, "mk");//马其顿语
		langMap.put(LANG.bn, "bn");//孟加拉语
		langMap.put(LANG.no, "no");//挪威语
		langMap.put(LANG.pt, "pt");//葡萄牙语
		langMap.put(LANG.ja, "ja");//日语
		langMap.put(LANG.sv, "sv");//瑞典语
		langMap.put(LANG.sr, "sr");//塞尔维亚语
		langMap.put(LANG.eo, "eo");//世界语
		langMap.put(LANG.sk, "sk");//斯洛伐克语
		langMap.put(LANG.sl, "sl");//斯洛文尼亚语
		langMap.put(LANG.sw, "sw");//斯瓦希里语
		langMap.put(LANG.th, "th");//泰语
		langMap.put(LANG.tr, "tr");//土耳其语
		langMap.put(LANG.cy, "cy");//威尔士语
		langMap.put(LANG.uk, "uk");//乌克兰语
		langMap.put(LANG.iw, "iw");//希伯来语
		langMap.put(LANG.el, "el");//希腊语
		langMap.put(LANG.eu, "eu");//西班牙的巴斯克语
		langMap.put(LANG.es, "es");//西班牙语
		langMap.put(LANG.hu, "hu");//匈牙利语
		langMap.put(LANG.hy, "hy");//亚美尼亚语
		langMap.put(LANG.it, "it");//意大利语
		langMap.put(LANG.yi, "yi");//意第绪语
		langMap.put(LANG.hi, "hi");//印地语
		langMap.put(LANG.kn, "kn");//印度的卡纳达语
		langMap.put(LANG.te, "te");//印度的泰卢固语
		langMap.put(LANG.ta, "ta");//印度的泰米尔语
		langMap.put(LANG.ur, "ur");//印度乌尔都语
		langMap.put(LANG.id, "id");//印尼语
		langMap.put(LANG.en, "en");//英语
		langMap.put(LANG.vi, "vi");//越南语
		//langMap.put(LANG.zh, "zh");//中文
		//langMap.put(LANG.zh-TW, "zh-TW");//中文(繁体)
		//langMap.put(LANG.zh-CN, "zh-CN);//中文(简体)
	}
	
	@Override
	protected String getResponse(LANG from, LANG targ, String query) throws Exception{
		
		HttpParams params = null;
		params = new HttpPostParams();		//统一采用post，若字符长度小于999用get也可以的
		String tk = tk(query);
		
		params.put("client", "t")
				.put("sl", langMap.get(from))
				.put("tl", langMap.get(targ))
				.put("hl", "zh-CN")
				.put("dt", "at")
				.put("dt", "bd")
				.put("dt", "ex")
				.put("dt", "ld")
				.put("dt", "md")
				.put("dt", "qca")
				.put("dt", "rw")
				.put("dt", "rm")
				.put("dt", "ss")
				.put("dt", "t")
				.put("ie", "UTF-8")
				.put("oe", "UTF-8")
				.put("source", "btn")
				.put("srcrom", "1")
				.put("ssel", "0")
				.put("tsel", "0")
				.put("kc", "11")
				.put("tk", tk)
				.put("q", query);
		
		return params.send2String("http://translate.google.cn/translate_a/single");
	}
	
	@Override
	protected String parseString(String jsonString){
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONArray segments = jsonArray.getJSONArray(0);
		StringBuilder result = new StringBuilder();
		
		for(int i=0; i<segments.size(); i++){
			result.append(segments.getJSONArray(i).getString(0));
		}
		
		return new String(result);
	}
	
	private String tk(String val) throws Exception{
		String script ="function tk(a) {"
						+"var TKK = ((function() {var a = 561666268;var b = 1526272306;return 406398 + '.' + (a + b); })());\n"
						+"function b(a, b) { for (var d = 0; d < b.length - 2; d += 3) { var c = b.charAt(d + 2), c = 'a' <= c ? c.charCodeAt(0) - 87 : Number(c), c = '+' == b.charAt(d + 1) ? a >>> c : a << c; a = '+' == b.charAt(d) ? a + c & 4294967295 : a ^ c } return a }\n"
						+"for (var e = TKK.split('.'), h = Number(e[0]) || 0, g = [], d = 0, f = 0; f < a.length; f++) {"  
						+"var c = a.charCodeAt(f);"  
						+"128 > c ? g[d++] = c : (2048 > c ? g[d++] = c >> 6 | 192 : (55296 == (c & 64512) && f + 1 < a.length && 56320 == (a.charCodeAt(f + 1) & 64512) ? (c = 65536 + ((c & 1023) << 10) + (a.charCodeAt(++f) & 1023), g[d++] = c >> 18 | 240, g[d++] = c >> 12 & 63 | 128) : g[d++] = c >> 12 | 224, g[d++] = c >> 6 & 63 | 128), g[d++] = c & 63 | 128)"  
						+"}"      
						+"a = h;"  
						+"for (d = 0; d < g.length; d++) a += g[d], a = b(a, '+-a^+6');"  
						+"a = b(a, '+-3^+b+-f');"  
						+"a ^= Number(e[1]) || 0;"  
						+"0 > a && (a = (a & 2147483647) + 2147483648);"  
						+"a %= 1E6;"  
						+"return a.toString() + '.' + (a ^ h)\n"
						+"}";
		engine.eval(script);  
		Invocable inv = (Invocable) engine;
		return (String) inv.invokeFunction("tk", val);
	}
}