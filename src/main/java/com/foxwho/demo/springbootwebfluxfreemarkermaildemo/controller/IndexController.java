package com.foxwho.demo.springbootwebfluxfreemarkermaildemo.controller;

import com.google.common.collect.Maps;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;


@Slf4j
@Controller
public class IndexController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/")
    public String index() {
        return "views/index";
    }

    @RequestMapping(value = "/index")
    public String index2(Model model) {
        model.addAttribute("name", LocalDate.now());
        return "views/index";
    }

    /**
     * freeMarker 模板案例
     *
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    @GetMapping("/freeMarker")
    public String freeMarker() throws IOException, TemplateException {
        String templateLocation = "mail-1.html";
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateLocation);
        log.info("Template template ={}", template);
        log.info("原原原原原原原原 Template template ={}", template);
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "这是用户名,小米同学");
        String string = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        log.info("Template template ={}", string);
        return "index<br>" + string;
    }

    /**
     * 单发邮件案例
     *
     * @return
     * @throws MessagingException
     */
    @GetMapping("/mail")
    public String mail() throws MessagingException {
        //邮件发送方
        String from = "xxxx@foxwho.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        //发送给谁
        helper.setTo(InternetAddress.parse("xxxx@foxwho.com"));
        //邮件标题
        helper.setSubject("【邮件发送测试】" + LocalDate.now());
        // 发送
        helper.setText("这是邮件内容，测试信息" + LocalDate.now(), true);
        mailSender.send(mimeMessage);
        return "index<br>xxxx";
    }

    /**
     * 邮件模板 发送邮件
     *
     * @return
     * @throws IOException
     * @throws TemplateException
     * @throws MessagingException
     */
    @GetMapping("/mail-freeMarker")
    public String mailFreeMarker() throws IOException, TemplateException, MessagingException {
        //邮件发送方
        String from = "xxxx@foxwho.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        //发送给谁
        helper.setTo(InternetAddress.parse("xxxx@foxwho.com"));
        //邮件标题
        helper.setSubject("【邮件发送测试】" + LocalDate.now());
        ////////////////////////////////////////////////////////////////////
        String templateLocation = "mail-1.html";
        Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateLocation);
        log.info("Template template ={}", template);
        log.info("原原原原原原原原 Template template ={}", template);
        Map<String, Object> map = Maps.newHashMap();
        map.put("username", "这是用户名,小米同学");
        String string = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        log.info("这是邮件内容 Template template ={}", string);
        ////////////////////////////////////////////////////////////////////
        // 发送 内容
        helper.setText(string, true);
        // 发送 带附件
        FileSystemResource file1 = new FileSystemResource(new File("templates/mail-1.html"));
        FileSystemResource file2 = new FileSystemResource(new File("templates/mail-2.html"));
        helper.addAttachment("附件-1.jpg", file1);
        helper.addAttachment("附件-2.jpg", file2);

        // 发送
        mailSender.send(mimeMessage);
        return "邮件-模板-发送测试";
    }
}
