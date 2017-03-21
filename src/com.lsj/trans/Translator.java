package com.lsj.trans;
/**
 * Created by SmallApple on 2017/3/20.
 */
public interface Translator {
	public String trans(LANG from, LANG targ, String query) throws Exception;
}
