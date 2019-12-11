package com.briup.env.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.briup.env.bean.Environment;
import com.briup.env.support.PropertiesAware;

public class GatherImpl implements Gather,PropertiesAware{

	@Override
	public Collection<Environment> gather() throws Exception {
		BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream("src/main/resources/a.txt")));
		Collection<Environment> list = new ArrayList<>();
	
		
		String line = "";
		while ((line = br.readLine())!=null) {
			String[] split = line.split("[|]");
			if (split.length!=9) {
				System.out.println("异常数据：" + line);
			}
			for (String string : split) {
//				System.out.println(string);
			}
			String type = split[3];
			String data = split[6];
			
			//存储公共数据
			Environment en = new Environment();
//			en.setName(name);
			en.setSrcId(split[0]);
			en.setDesId(split[1]);
			en.setDevId(split[2]);
			en.setSersorAddress(type);
			en.setCount(Integer.parseInt(split[4]));
			en.setCmd(split[5]);
			en.setStatus(Integer.parseInt(split[7]));
//			en.setData(data);
			en.setGather_date(new Timestamp(Long.parseLong(split[8])));
			
			long t = new Timestamp(Long.parseLong(split[8])).getTime();
			Date date = new java.sql.Date(t);
			System.out.println(date);
			
			if ("16".equals(type)) {
				String name1 = "温度";
				String name2 = "湿度";
				
				String wd = data.substring(0, 4);
				String sd = data.substring(4,8);
				
				int iwd = Integer.parseInt(wd, 16);
				int isd = Integer.parseInt(sd, 16);
				
				float Temperature = (float) ((iwd*0.00268127)-46.85);
//				System.out.println(Temperature);
				float Humidity = (float) ((isd*0.00190735)-6);
//				System.out.println(Humidity);
				
				en.setName(name1);
				en.setData(Temperature);
				
				Environment en2 = new Environment();
				en2.setName(name2);
				en2.setSrcId(split[0]);
				en2.setDesId(split[1]);
				en2.setDevId(split[2]);
				en2.setSersorAddress(type);
				en2.setCount(Integer.parseInt(split[4]));
				en2.setCmd(split[5]);
				en2.setStatus(Integer.parseInt(split[7]));
				en2.setData(Humidity);
				en2.setGather_date(new Timestamp(Long.parseLong(split[8])));
				
				list.add(en2);
			}else if ("256".equals(type)) {
				String name = "光照强度";
				String g = data.substring(0,4);
				float fg = Integer.parseInt(g,16);
//				System.out.println(fg);
				
				en.setName(name);
				en.setData(fg);
			}else if ("1280".equals(type)) {
				String name = "二氧化碳";
				String e = data.substring(0, 4);
				float fe = Integer.parseInt(e,16);
//				System.out.println(fe);
				
				en.setName(name);
				en.setData(fe);
			}else {
				System.out.println("未知类型的数据:" + split[3]);
			}
			
			list.add(en);
		}
		if (br!=null) {
			br.close();
		}
		return list;
	}

	@Override
	public void init(Properties p) throws Exception {
		
		
	}	
}


