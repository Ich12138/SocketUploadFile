package org.wq.UploadFileBySocket.pojo;

public class FileRes {
    private String user;
    private String filepath;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public String toString() {
        return "FileRes{" +
                "user='" + user + '\'' +
                ", filepath='" + filepath + '\'' +
                '}';
    }
}
