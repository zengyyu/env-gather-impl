package com.briup.env.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

import com.briup.env.bean.Environment;
import com.briup.env.client.GatherImpl;

public class DBStoreImpl implements DBStore{
	private static String driverClass;
	private static String url;
	private static String user;
	private static String password;
	
	static {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream("jdbc.properties");
			p.load(is);
			driverClass = p.getProperty("jdbc.driverClass");
			url = p.getProperty("jdbc.url");
			user = p.getProperty("jdbc.user");
			password = p.getProperty("jdbc.password");
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (is!=null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void saveDB(Collection<Environment> c) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		int count = 0;
		try {
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url, user, password);
			conn.setAutoCommit(false);//修改事务为手动提交
						
				for (Environment en : c) {
					count++;
						
					//方法一
//					long time = en.getGather_date().getTime();
//					Date d = new java.sql.Date(time);
//					String[] str = d.toString().split("[-]");
//					int parseInt = Integer.parseInt(str[2]);
					
//					String sql = "insert into e_detail_" + parseInt + " values(?,?,?,?,?,?,?,?,?,?)";
					
				//方法二
//					long time = en.getGather_date().getTime();
//					SimpleDateFormat sdf = new SimpleDateFormat("DD");
//					int parseInt = Integer.parseInt(sdf.format(time));
				//方法三
					Timestamp date = en.getGather_date();
					Calendar calendar = Calendar.getInstance();
					calendar.setTimeInMillis(date.getTime());
					int day = calendar.get(Calendar.DATE);
					
					String tableName = "e_detail_" + day;
					
					String sql = "insert into " + tableName + " values(?,?,?,?,?,?,?,?,?,?)";
					
						ps = conn.prepareStatement(sql);
						
						ps.setString(1, en.getName());
						ps.setString(2, en.getSrcId());
						ps.setString(3, en.getDesId());
						ps.setString(4, en.getDevId());
						ps.setString(5, en.getSersorAddress());
						ps.setInt(6, en.getCount());
						ps.setString(7, en.getCmd());
						ps.setInt(8, en.getStatus());
						ps.setFloat(9, en.getData());
						long t = en.getGather_date().getTime();
						ps.setDate(10, new java.sql.Date(t));
//						ps.setTimestamp(10, en.getGather_date());
						ps.addBatch();
						
//						ps.executeUpdate();
						
						if (count%10 == 0 || count == c.size()) {
							ps.executeBatch();
//							conn.commit();
						}
				}	
				ps.executeBatch();
				conn.commit();//统一提交事务
				
			System.out.println("数据插入成功");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
