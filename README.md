# spring-boot-webflux-freemarker-mail-demo


webflux 发送邮件

webflux 模板

webflux 发送 自定义模板邮件 邮件

# 请先配置好 邮件服务器 

在 application.yml  中配置


# 注意
如果使用  查看 页面 模板  请看 `IndexController`
注解必须是 `@Controller`，否则不起作用的

浏览器访问

```SHELL
http://localhost:8080/

http://localhost:8080/index

http://localhost:8080/freeMarker

http://localhost:8080/mail

http://localhost:8080/mail-freeMarker
```


```SHELL
http://localhost:8080/rest/

http://localhost:8080/rest/index

http://localhost:8080/rest/freeMarker

http://localhost:8080/rest/mail

http://localhost:8080/rest/mail-freeMarker
```