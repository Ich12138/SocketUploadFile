package org.wq.UploadFileBySocket.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class MyClientSend implements Runnable {

    private Socket clientSendSocket;
    private Scanner clientSendSc;
    private String clientStatus = "login";//有三种状态 分别是login register file

    public MyClientSend(Socket clientSendSocket){
        this.clientSendSocket = clientSendSocket;
        this.clientSendSc = new Scanner(System.in);
    }
    @Override
    public void run() {
        try {
            while (true){
                switch (clientStatus){
                    case "login":

                        break;

                    case "register":
                        break;

                    case "file":

                        break;

                    default:
                        System.out.println("选择出现错误,请重新操作");
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void loginOpt(){

    }

    private void exitSystem(){
        System.out.println("正在退出...");
        try {
            clientSendSocket.close();
            Thread.currentThread().sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void setClientStatus(String clientStatus){
        this.clientStatus = clientStatus;
    }

}
