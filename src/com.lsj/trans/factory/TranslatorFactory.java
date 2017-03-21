package com.lsj.trans.factory;
/**
 * Created by SmallApple on 2017/3/20.
 */
import com.lsj.trans.Translator;
import com.lsj.trans.exception.DupIdException;

import java.net.URISyntaxException;

final public class TranslatorFactory extends AbstractTranslatorFactory {
	public TranslatorFactory() throws ClassNotFoundException, InstantiationException, IllegalAccessException, DupIdException, URISyntaxException {
		super();
	}

	@Override
	public Translator get(String id) {
		return translatorMap.get(id);
	}

}
