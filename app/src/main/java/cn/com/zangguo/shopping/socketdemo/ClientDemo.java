package cn.com.zangguo.shopping.socketdemo;

import java.io.InputStream;
import java.net.Socket;

public class ClientDemo {
	public static void main(String[] args) throws Exception {
		Socket socket = new Socket("172.16.163.24", 4869);
		InputStream is = socket.getInputStream();
		int len = -1;
		byte[] bs = new byte[1024];
		while((len= is.read(bs))!=-1) {
			String s = new String(bs,0,len);
			System.out.println(s);
		}
	}
}
