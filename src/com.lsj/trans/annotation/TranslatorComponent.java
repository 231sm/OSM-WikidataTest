package com.lsj.trans.annotation;

/**
 * Created by SmallApple on 2017/3/20.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TranslatorComponent {
	public String id();
}
