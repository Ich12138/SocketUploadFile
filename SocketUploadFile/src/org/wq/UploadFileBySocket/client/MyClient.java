package org.wq.UploadFileBySocket.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MyClient {
    public static void main(String[] args)  {
        Socket socket = null;
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("************************");
            System.out.println("******            ******");
            System.out.println("*****远程文件 操作系统*****");
            System.out.println("******            ******");
            System.out.println("************************");
            String ip;
            System.out.print("请输入服务器ip地址: ");
            ip = sc.nextLine();
            socket = new Socket(ip,9999);
            System.out.println("正在请求,请等待...");
            Thread.currentThread().sleep(3000);
            System.out.println("与服务器建立连接已经建立");

            MyClientSend myClientSend = new MyClientSend(socket);
            Thread clientSendThread = new Thread(myClientSend);
            clientSendThread.start();

            MyClientRead myClientRead = new MyClientRead(socket,myClientSend);
            Thread clientReadThread = new Thread(myClientRead);
            clientReadThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
