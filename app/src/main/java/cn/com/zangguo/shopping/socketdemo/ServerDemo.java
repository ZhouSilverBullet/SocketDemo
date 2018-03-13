package cn.com.zangguo.shopping.socketdemo;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerDemo {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(4869);
		Socket ss = serverSocket.accept();
		OutputStream os = ss.getOutputStream();
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String value = scanner.nextLine();
			os.write(value.getBytes("UTF-8"));
			os.flush();
			if("886".equals(value)){
				break;
			}
		}
		ss.close();
		serverSocket.close();
	}
}
