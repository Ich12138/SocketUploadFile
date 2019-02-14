package org.wq.UploadFileBySocket.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.util.HashMap;

public class FileOpt {
    private static String fileTreeStruct = "";

    //返回值 0成功 -1服务器错误
    public String disFileTreeStruct(String filePath) {
        HashMap<String,String> resMap = new HashMap<>();
        resMap.put("type","file");
        resMap.put("method","disFileTreeStruct");
        fileTreeStruct = "";
        try {
            getFileTreeStruct(filePath, "");
            resMap.put("status","0");
            resMap.put("msg",fileTreeStruct);
        } catch (Exception e) {
            e.printStackTrace();
            HashMap<String,String> errorMap = new HashMap<>();
            errorMap.put("status","2");
            errorMap.put("msg","服务器错误");
            return JSON.toJSONString(errorMap);
        }
        return JSON.toJSONString(resMap);
    }

    private void getFileTreeStruct(String filePath, String fileIcon) throws Exception {
        File tempFile = new File(filePath);
        if (fileIcon.equals("")) {
            fileIcon = "|_";
        }
        if (!tempFile.exists()) {
            return;
        }

        if (tempFile.isFile()) {
            fileTreeStruct += fileIcon + tempFile.getName() + "\n";
        } else {
            fileTreeStruct += fileIcon + tempFile.getName() + "\n";
            File[] files = tempFile.listFiles();
            fileIcon = " " + fileIcon;
            for (File childFile : files) {
                if (childFile.isFile()) {
                    fileTreeStruct += fileIcon + childFile.getName() + "\n";
                } else {
                    getFileTreeStruct(childFile.getAbsolutePath(), fileIcon);
                }
            }
        }
    }

    //返回值状态 0是文件夹 1该文件夹不存在 -1服务器错误 3只是个文件 4文件夹下面没有子文件
    public String getFileCatalogAboutOneLevel(String filePath) {
        HashMap<String, String> resMap = new HashMap<>();
        resMap.put("type","file");
        resMap.put("method","getFileCatalogAboutOneLevel");
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                resMap.put("status", "1");
                resMap.put("msg", "该文件不存在");
            } else {
                if (file.isFile()) {
                    resMap.put("status", "3");
                    resMap.put("msg", "只是一个文件");
                } else {
                    String[] childFilesName = file.list();
                    resMap.put("status", "0");
                    if (childFilesName.length == 0) {
                        resMap.put("msg","该文件夹下面没有子文件");
                    } else {
                        resMap.put("msg",JSON.toJSONString(childFilesName));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("status", "-1");
            errorMap.put("msg", "服务器错误");
            return JSON.toJSONString(errorMap);
        }
        return JSON.toJSONString(resMap);
    }

    public static void main(String[] args) {
        String res = new FileOpt().getFileCatalogAboutOneLevel("/home/wq/xxx");
        System.out.println(res);
        JSONObject jsonObject = JSON.parseObject(res);
        System.out.println((String) jsonObject.get("msg"));
        String childFileNameList = (String) jsonObject.get("msg");
        JSONArray jsonArray = JSONArray.parseArray(childFileNameList);
        System.out.println(jsonArray.toArray().length == 0);
        for (Object o : jsonArray.toArray()) {
            System.out.println(o.toString());
        }
    }
}
