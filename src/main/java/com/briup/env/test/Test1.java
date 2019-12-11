package com.briup.env.test;

import java.util.Collection;

import com.briup.env.bean.Environment;
import com.briup.env.client.GatherImpl;
import com.briup.env.server.DBStoreImpl;

public class Test1 {
	public static void main(String[] args) throws Exception {
		Collection<Environment> gather = new GatherImpl().gather();
		new DBStoreImpl().saveDB(gather);
		
	}
}
