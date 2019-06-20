package com.huangyang.emailsystem.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {

    @Resource
    MailService mailService;

    @Resource
    TemplateEngine templateEngine;

    @Test
    public void sayHelloTest(){
        mailService.sayHello();
    }

    @Test
    public void sendSimpleMailTest(){
        mailService.sendSimpleMail(
                "huangyang_0517@163.com",
                "这是纯文本邮件",
                "喜来登大酒店8：00举办年会"
        );
    }
    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String content = "<html>\n" +
                "<body>\n" +
                "<h3>喜来登大酒店8：00举办年会</h3>\n" +
                "</body>\n" +
                "</html>";

        mailService.sendHtmlMail(
                "huangyang_0517@163.com",
                "这是html格式邮件",
                content
        );
    }

    @Test
    public void sendAttachmentsMailTest() throws MessagingException {

        mailService.sendAttachmentsMail(
                "huangyang_0517@163.com",
                "这是带附件的邮件",
                "喜来登大酒店8：00举办年会",
                "/Users/huangyang/Desktop/二维码管理/江苏省/盐城市_阜宁县/阜宁信访二维码.png"
        );
    }
    @Test
    public void sendInlinResourceMailTest(){
        String imgPath = "/Users/huangyang/Desktop/二维码管理/江苏省/盐城市_阜宁县/阜宁信访二维码.png";
        String rscId = "huangyang001";
        String content = "<html><body><h3>喜来登大酒店8：00举办年会</h3><img src=\'cid:"+rscId+"\'></img></body></html>";

        mailService.sendInlinResourceMail(
                "huangyang_0517@163.com",
                "这是带图片的邮件",
                content,
                imgPath,
                rscId
        );
    }

    @Test
    public void testTemplateMailTest() throws MessagingException{
        Context content = new Context();
        content.setVariable("id","006");
        String emailContent = templateEngine.process("emailTemplate",content);

        mailService.sendHtmlMail(
                "huangyang_0517@163.com",
                "模板邮件",
                emailContent
        );
    }
}
