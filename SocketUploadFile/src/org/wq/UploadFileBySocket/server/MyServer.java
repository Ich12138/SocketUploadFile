package org.wq.UploadFileBySocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("接收到一个客户端");
            MyServerDao myServerDao = new MyServerDao(clientSocket);
            Thread t = new Thread(myServerDao);
            t.start();
            System.out.println("建立一个连接");
        }
    }
}
