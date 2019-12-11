package com.briup.env.main;

import com.briup.env.client.Client;
import com.briup.env.client.ClientImpl;
import com.briup.env.client.Gather;
import com.briup.env.client.GatherImpl;
import com.briup.env.server.Server;
import com.briup.env.server.ServerImpl;

public class ServerMain {
	public static void main(String[] args) throws Exception {
		//从客户端接受传递的数据
		Server server = new ServerImpl();
		server.reciver();
		//往数据库中写入
//		Gather gather = new GatherImpl();
//		Client client = new ClientImpl();
	}
}
