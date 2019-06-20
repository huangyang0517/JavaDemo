package com.huangyang.emailsystem.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.logging.Logger;

@Service
public class MailService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());


    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    public void sayHello(){
        System.out.println("Hello World,我的项目启动了");
    }


    //纯文本邮件
    public void sendSimpleMail(String to,String subject,String content){
        SimpleMailMessage message = new SimpleMailMessage();
        System.out.println("发送方邮箱:"+from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);

        mailSender.send(message);
    }

    //html格式邮件
    public void sendHtmlMail(String to,String subject,String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        mailSender.send(message);
    }
    //带附件邮件
    public void sendAttachmentsMail(String to,String subject,String content,String filePath) throws MessagingException {
        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();

        System.out.println("附件:"+fileName);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        //这里附件可以n个
        helper.addAttachment(fileName,file);

        mailSender.send(message);
    }

    //带图片的邮件
    public void sendInlinResourceMail(String to,String subject,String content,String rscPath,String rscId){


        logger.info("发送邮件开始: {},{},{},{},{}",to,subject,content,rscPath,rscId);
        try {
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            //String fileName = file.getFilename();

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);

            helper.addInline(rscId,res);

            mailSender.send(message);
            logger.info("发送邮件成功:");
        }
        catch (MessagingException e){
            logger.error("发送邮件异常:",e);
        }

    }
}

