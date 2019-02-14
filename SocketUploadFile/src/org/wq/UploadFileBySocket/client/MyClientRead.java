package org.wq.UploadFileBySocket.client;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

//用来显示服务器返回的数据
public class MyClientRead implements Runnable {
    private Socket socket;
    private String clientStatus = "login";//有三种状态 分别是login register file
    private MyClientSend myClientSend;
    public MyClientRead(Socket socket,MyClientSend myClientSend){
        this.socket = socket;
        this.myClientSend = myClientSend;
    }
    @Override
    public void run() {
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = bfr.readLine())!=null) {
                System.out.println(msg);
                JSONObject jsonObject = JSONObject.parseObject(msg);
                String status = jsonObject.getString("status");
                if (status.equals("13")) {
                    System.out.println("退出");
                    break;
                } else {
                    String type = jsonObject.getString("type");
                    switch (type){
                        case "user":
                            String userRes = optUser(jsonObject);
                            System.out.println(userRes);
                            break;
                        case "file":
                            String fileRes = optFile(jsonObject);
                            System.out.println(fileRes);
                            break;
                        default:
                            System.out.println("返回值异常请重新操作");
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String optFile(JSONObject fileJsonObject){
        String method = fileJsonObject.getString("method");
        String fileRes = "";
        switch (method){
            case "disFileTreeStruct":
                String fileTreeStruct = fileJsonObject.getString("msg");
                if(fileTreeStruct.equals("")){
                    fileRes = "是个文件或者文件夹";
                }else {
                    fileRes = fileTreeStruct;
                }
                break;
            case "getFileCatalogAboutOneLevel":
                String status = fileJsonObject.getString("status");
                String result = fileJsonObject.getString("msg");
                if(status.equals("0")) {
                    JSONArray childFilesNamesArray = JSONArray.parseArray(result);
                    System.out.println(childFilesNamesArray.toArray());
                    Object[] childFilesNamesObject = childFilesNamesArray.toArray();
                    for (Object childFileName : childFilesNamesObject) {
                        fileRes += childFileName.toString() + "\t";
                    }
                    fileRes = fileRes + "\n";
                }else {
                    fileRes = result;
                }
                break;
        }
        return fileRes;
    }



    public String optUser(JSONObject userJsonObject){
        String method = userJsonObject.getString("method");
        String status = userJsonObject.getString("status");
        String msg = userJsonObject.getString("msg");
        String userRes = "";
        switch (method){
            case "login":
                if (status.equals("0")){
                    myClientSend.setClientStatus("file");
                }else if (status.equals("11")){
                    myClientSend.setClientStatus("register");
                }
                userRes = msg;
                break;
            case "register":

                break;

            default:
                userRes = msg;
                break;
        }
        return userRes;
    }

    public String getClientStatus(){
        return this.clientStatus;
    }

    public void setClientStatus(String clientStatus){
        this.clientStatus = clientStatus;
    }
}
