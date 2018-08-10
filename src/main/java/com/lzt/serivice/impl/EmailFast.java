package com.lzt.serivice.impl;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Enzo Cotter on 2018/8/11.
 */
public class EmailFast implements Runnable {
    private  String str;
    public EmailFast(String str){
        this.str=str;
    }
    @Override
    public void run() {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost("smtp.sh-liangxin.com");
        senderImpl.setPort(465);
        senderImpl.setUsername("liangzhuotong@sh-liangxin.com");               // 根据自己的情况,设置发件邮箱地址
        senderImpl.setPassword("liangtong884");          // 根据自己的情况, 设置password
        senderImpl.setDefaultEncoding("UTF-8");
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");                 // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        senderImpl.setJavaMailProperties(prop);

        MimeMessage mailMessage = senderImpl.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        // 设置收件人，寄件人
        try {
            messageHelper.setTo("guxianghai@sh-liangxin.com");

            messageHelper.setFrom("liangzhuotong@sh-liangxin.com");
            messageHelper.setSubject("续保提醒");
            // true 表示启动HTML格式的邮件
            messageHelper.setText(str, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        senderImpl.send(mailMessage);

    }
}
