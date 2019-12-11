package com.briup.env.client;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;

import com.briup.env.bean.Environment;

public class ClientImpl implements Client{
//	private String host = "127.0.0.1";
//	private int port = 8099;
	@Override
	public void send(Collection<Environment> c) throws Exception {
		Socket socket = new Socket("127.0.0.1",8099);
		OutputStream out = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(out);
		//把数据发送给服务器
		oos.writeObject(c);
		oos.flush();
		
		if (oos!=null) 
			oos.close();
		if (socket!=null) 
			socket.close();
	}

}
