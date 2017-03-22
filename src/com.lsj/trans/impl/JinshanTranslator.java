package com.lsj.trans.impl;
/**
 * Created by SmallApple on 2017/3/20.
 */
import com.lsj.http.HttpParams;
import com.lsj.http.HttpPostParams;
import com.lsj.trans.AbstractOnlineTranslator;
import com.lsj.trans.LANG;
import com.lsj.trans.annotation.TranslatorComponent;
import net.sf.json.JSONObject;

@TranslatorComponent(id = "jinshan")
final public class JinshanTranslator extends AbstractOnlineTranslator {
	
	public JinshanTranslator(){
		langMap.put(LANG.auto, "auto");
		langMap.put(LANG.en, "en");
		langMap.put(LANG.zh, "zh");
	}
	
	@Override
	protected String getResponse(LANG from, LANG targ, String query) throws Exception{
		HttpParams params = new HttpPostParams()
				.put("f", langMap.get(from))
				.put("t", langMap.get(targ))
				.put("w", query);
		
		return params.send2String("http://fy.iciba.com/ajax.php?a=fy");
	}
	
	
	@Override
	protected String parseString(String jsonString){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		String result = jsonObject.getJSONObject("content").getString("out");
		return result;
	}
}
