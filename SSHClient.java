package com.evozi.slowudp;

import com.jcraft.jsch.*;
import java.io.*;

public class SSHClient {
    
    public String executeCommand(String host, String username, String password, 
                               String port, String command) throws JSchException, IOException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, Integer.parseInt(port));
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        
        try {
            session.connect(30000); // 30 second timeout
            
            ChannelExec channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
            
            channel.setOutputStream(outputStream);
            channel.setErrStream(errorStream);
            channel.connect();
            
            // انتظار انتهاء التنفيذ
            while (!channel.isClosed()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            
            channel.disconnect();
            session.disconnect();
            
            String result = outputStream.toString();
            String error = errorStream.toString();
            
            if (!error.isEmpty()) {
                return "خطأ: " + error;
            }
            
            return result.isEmpty() ? "تم التنفيذ بنجاح" : result;
            
        } catch (JSchException | IOException e) {
            throw new JSchException("فشل الاتصال: " + e.getMessage());
        }
    }
    
    public void uploadFile(String host, String username, String password, 
                          String port, InputStream fileStream, String remotePath) 
                          throws JSchException, IOException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, Integer.parseInt(port));
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        
        try {
            session.connect(30000);
            
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            
            channel.put(fileStream, remotePath);
            
            channel.disconnect();
            session.disconnect();
            
        } catch (JSchException | SftpException e) {
            throw new JSchException("فشل رفع الملف: " + e.getMessage());
        }
    }
}
