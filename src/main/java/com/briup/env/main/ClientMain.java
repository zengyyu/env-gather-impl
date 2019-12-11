package com.briup.env.main;

import java.util.Collection;

import com.briup.env.bean.Environment;
import com.briup.env.client.Client;
import com.briup.env.client.ClientImpl;
import com.briup.env.client.Gather;
import com.briup.env.client.GatherImpl;

public class ClientMain {
	public static void main(String[] args) throws Exception {
		//客户端先去采集数据
		Gather g = new GatherImpl();
		Collection<Environment> gather = g.gather();
		System.out.println(gather.size());
		//然后通过网络传输给服务器
		Client client = new ClientImpl();
		client.send(gather);
	}
}
