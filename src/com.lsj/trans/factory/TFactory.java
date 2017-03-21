package com.lsj.trans.factory;
/**
 * Created by SmallApple on 2017/3/20.
 */
import com.lsj.trans.Translator;

public interface TFactory {
	Translator get(String id);
}
