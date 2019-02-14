package org.wq.UploadFileBySocket.server;

import java.net.Socket;

public class MyServerDao implements Runnable {
    private Socket clientSocekt;

    public MyServerDao(Socket client){
        this.clientSocekt = client;
    }
    @Override
    public void run() {

    }
}
