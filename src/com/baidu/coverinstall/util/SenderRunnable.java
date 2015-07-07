package com.baidu.coverinstall.util;


public class SenderRunnable implements Runnable {
    private String user;
    private String password;
    private String subject;
    private String body;
    private String receiver;
    private MailSender sender;
    private String attachment;

    public SenderRunnable(String user, String password) {
        this.user = user;
        this.password = password;
        sender = new MailSender(user, password);
        sender.setMailhost("email.baidu.com");
    }
    
    public void setMail(String subject, String body, String receiver, String attachment) {
        this.subject = subject;
        this.body = body;
        this.receiver = receiver;
        this.attachment = attachment;
    }

    public void run() {
        // TODO Auto-generated method stub
        try {
            sender.sendMail(subject, body, user, receiver, attachment);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
