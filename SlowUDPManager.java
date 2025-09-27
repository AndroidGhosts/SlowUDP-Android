package com.evozi.slowudp;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.*;

public class SlowUDPManager {
    private Context context;
    private SSHClient sshClient;
    private String currentServerIP;
    private String currentUsername;
    private String currentPassword;
    private String currentPort;
    
    public SlowUDPManager(Context context) {
        this.context = context;
        this.sshClient = new SSHClient();
    }
    
    public String installSlowUDP(String serverIP, String username, String password, String port) {
        try {
            // حفظ معلومات الاتصال
            this.currentServerIP = serverIP;
            this.currentUsername = username;
            this.currentPassword = password;
            this.currentPort = port;
            
            // نسخ السكريبتات إلى الخادم
            copyScriptsToServer();
            
            // جعل السكريبتات قابلة للتنفيذ وتشغيلها
            String command = "chmod +x /tmp/slowudp.sh /tmp/install_server.sh && " +
                           "cd /tmp && bash slowudp.sh";
            
            return executeRemoteCommand(command);
            
        } catch (Exception e) {
            return "خطأ في التثبيت: " + e.getMessage();
        }
    }
    
    public String startSlowUDP() {
        String command = "systemctl start slowudp-server";
        return executeRemoteCommand(command);
    }
    
    public String stopSlowUDP() {
        String command = "systemctl stop slowudp-server";
        return executeRemoteCommand(command);
    }
    
    public String checkStatus() {
        String command = "systemctl status slowudp-server";
        return executeRemoteCommand(command);
    }
    
    public String getConfig() {
        String command = "cat /root/slowudp/url.txt 2>/dev/null || echo 'لم يتم العثور على ملف التكوين'";
        return executeRemoteCommand(command);
    }
    
    private String executeRemoteCommand(String command) {
        try {
            return sshClient.executeCommand(currentServerIP, currentUsername, 
                                          currentPassword, currentPort, command);
        } catch (Exception e) {
            return "خطأ في الاتصال: " + e.getMessage();
        }
    }
    
    private void copyScriptsToServer() throws Exception {
        AssetManager assetManager = context.getAssets();
        
        // نسخ slowudp.sh
        InputStream slowudpScript = assetManager.open("scripts/slowudp.sh");
        sshClient.uploadFile(currentServerIP, currentUsername, currentPassword, currentPort,
                           slowudpScript, "/tmp/slowudp.sh");
        
        // نسخ install_server.sh
        InputStream installScript = assetManager.open("scripts/install_server.sh");
        sshClient.uploadFile(currentServerIP, currentUsername, currentPassword, currentPort,
                           installScript, "/tmp/install_server.sh");
    }
}
