package com.jah.Zimple;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyConfigs {

    // injects smtp.gmail.com
    @Value("${email.smtp.server}")
    private String server;

    // injects 467
    @Value("${email.smtp.port}")
    private String port;

    // injects hello@gmail.com
    @Value("${email.smtp.username}")
    private String username;

    @Value("${message}")
    private String message;

    @Value("${configvalue}")
    private String configValue;

    @Value("${db_url: localhost}")
    private String dburl;

    @Value("${email}")
    private String email;

    @Value("${filename")
    private String filename;


    /*
     Note: if you use ${path} -->> Java system will return the actual path,
     as from command line type in path:
     cmd: c:\path
     -->> will return the Windows path variable!!
     */
    @Value("${mypath}")
    private String path;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDburl() {
        return dburl;
    }

    public void setDburl(String dburl) {
        this.dburl = dburl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAll(){
        return "MyConfigs{" +
                "server='" + server + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", configValue='" + configValue + '\'' +
                ", dburl='" + dburl + '\'' +
                ", mount path='" + path + '\'' +
                '}';
    }

    @Override
    public String toString() {
        return "MyConfigs{" +
                "server='" + server + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                ", configValue='" + configValue + '\'' +
                '}';
    }
}
