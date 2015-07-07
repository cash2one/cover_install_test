package com.baidu.coverinstall.util;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class SendHtmlMail {
	public static void sendMessage(String smtpHost, String from, String to,String subject, String messageText) throws MessagingException,java.io.UnsupportedEncodingException {
        // Step 1:  Configure the mail session
         System.out.println("Configuring mail session for: " + smtpHost);
         java.util.Properties props = new java.util.Properties();
         props.setProperty("mail.smtp.auth", "true");  //指定是否需要SMTP验证
         props.setProperty("mail.smtp.host", smtpHost);  //指定SMTP服务器
         props.put("mail.transport.protocol", "smtp");
         props.put("mail.smtp.port", "25");
         
         Session mailSession = Session.getDefaultInstance(props);
         mailSession.setDebug(true);  //是否在控制台显示debug信息
 
         // Step 2:  Construct the message
         System.out.println("Constructing message -  from=" + from + "  to=" + to);
         String sendFrom = from + "@baidu.com";
         String toFrom = to + "@baidu.com";
         InternetAddress fromAddress = new InternetAddress(sendFrom);
         InternetAddress toAddress = new InternetAddress(toFrom);
 
         MimeMessage testMessage = new MimeMessage(mailSession);
         testMessage.setFrom(fromAddress);
         testMessage.addRecipient(javax.mail.Message.RecipientType.TO, toAddress);
         testMessage.setSentDate(new java.util.Date());
         testMessage.setSubject(MimeUtility.encodeText(subject,"GB2312","B"));
 
         testMessage.setContent(messageText, "text/html;charset=GB2312");
         System.out.println("Message constructed");
 
         // Step 3:  Now send the message
         Transport transport = mailSession.getTransport("smtp");
         transport.connect(smtpHost, from, "Vw*hmNg#pN");
         transport.sendMessage(testMessage, testMessage.getAllRecipients());
         transport.close();
 
 
         System.out.println("Message sent!");
     }
			 
     public static void main(String[] args) {
	 
     	 String smtpHost = "email.baidu.com";
         String from = "bdmobile";
         String to = "liuwenbo01";
         String subject = "html邮件测试";   //subject javamail自动转码

         StringBuffer theMessage = new StringBuffer();
         theMessage.append("<h2><font color=red>test</font></h2>");
         theMessage.append("<hr>");
         theMessage.append("<i>test</i>");
 
         try {
             SendHtmlMail.sendMessage(smtpHost, from, to, subject, theMessage.toString());
         }
         catch (javax.mail.MessagingException exc) {
             exc.printStackTrace();
         }
         catch (java.io.UnsupportedEncodingException exc) {
             exc.printStackTrace();
         }
     }
}
