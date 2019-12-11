package com.briup.env.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BackupImpl implements Backup {
	private String filePath;
	
	@SuppressWarnings("resource")
	@Override
	public Object load(String fileName, boolean del) throws Exception {
		File file = new File(filePath + fileName);
		if (!file.exists()) {
			System.out.println(fileName);
			return null;
		}
		
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream in = new ObjectInputStream(fis);
		Object obj = in.readObject();
		
		if (del) {
			file.delete();
		}
		
		if (in!=null) {
			in.close();
		}
		if (fis!=null) {
			fis.close();
		}
		return obj;
	}

	@Override
	public void store(String fileName, Object obj, boolean append) throws Exception {
		FileOutputStream fos = new FileOutputStream(filePath+fileName,append);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(obj);
		oos.flush();
		
		if (oos!=null) {
			oos.close();
		}
		if (fos!=null) {
			fos.close();
		}
	}
	
}
