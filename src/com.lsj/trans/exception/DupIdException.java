package com.lsj.trans.exception;

/**
 * Created by SmallApple on 2017/3/20.
 */
public class DupIdException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5743754674671902221L;
	
	public DupIdException(String info) {
		super(info, null);
	}
}
