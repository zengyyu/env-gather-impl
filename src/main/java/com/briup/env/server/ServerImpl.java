package com.briup.env.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.briup.env.bean.Environment;
import com.briup.env.support.PropertiesAware;

public class ServerImpl implements Server,PropertiesAware{
	InputStream in;
	ObjectInputStream ois;
	private volatile boolean flag = false;
	private int serverPort;
	private int poolSize = 10;
	private ExecutorService pool;
	//压制警告
	@SuppressWarnings("unchecked")
	@Override
	public void reciver() {
		ServerSocket server = null;
		try {
			server = new ServerSocket(8099);
			while (!flag) {
				Socket socket = server.accept();
				pool.execute(new Handler(socket));
				
				in = socket.getInputStream();
				ois = new ObjectInputStream(in);
				
				//获取客户端发送的集合对象
			
				try {
					Collection<Environment> coll = (Collection<Environment>) ois.readObject();	
					DBStore db = new DBStoreImpl();
					db.saveDB(coll);
					
					if (coll!=null && coll.size()>0) {
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (ois!=null) {
					ois.close();
				}
				if (in!=null) {
					in.close();
				}
				if (server!=null) {
					server.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private class Handler implements Runnable{
		private Socket socket;
		public Handler(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			
			
		}
		
	}
	
	@Override
	public void shutdown() throws Exception {
		
	}

	@Override
	public void init(Properties p) throws Exception {
		serverPort = Integer.parseInt(p.getProperty(""));
		poolSize = Integer.parseInt(p.getProperty(""));
		pool = Executors.newFixedThreadPool(poolSize);
	}
	
}
