package com.briup.env.test;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class HelloTest {
	private static Logger logger = Logger.getRootLogger();
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		System.out.println("hello world");
		
		logger.debug("hello debug");
		logger.info("hello info");
		logger.warn("hello warn");
		logger.error("hello error");
		
		long time = System.currentTimeMillis();
		Timestamp t = new Timestamp(time);
		int day = t.getDay();
		System.out.println(day);
	}
}
