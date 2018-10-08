package com.fmkj.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
* @ClassName: Sendmail
* @Description: 发送Email
* @author: 孤傲苍狼
* @date: 2015-1-12 下午9:42:56
*
*/ 
public class Sendmail {

	private static final Logger logger = LoggerFactory.getLogger(Sendmail.class);

    /**
    * @Method: createSimpleMail
    * @Description: 创建一封只包含文本的邮件
    * @Anthor:孤傲苍狼
    *
    * @param session
    * @return
    * @throws Exception
    */ 
    public static MimeMessage createSimpleMail(Session session,String mail)
            throws Exception {
        //创建邮件对象
        MimeMessage message = new MimeMessage(session);
        //指明邮件的发件人
        message.setFrom(new InternetAddress("1298760615@qq.com"));
        //指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
        //邮件的标题
        message.setSubject("费马科技锤多宝官网");
        //邮件的文本内容
        message.setContent("欢迎使用锤多宝app邮箱绑定，本邮件由系统自动发出，请勿回复。<br/>感谢您的使用!<br/>费马科技有限公司", "text/html;charset=UTF-8");
        //返回创建好的邮件对象
        return message;
    }
    
    public static boolean sendMail(String mail) {
    	logger.info("邮件发送中...");
    	Properties prop = new Properties();
        prop.setProperty("mail.host", "1298760615@qq.com");
        prop.setProperty("mail.transport.protocol", "smtp");
        prop.setProperty("mail.smtp.auth", "true");
        //使用JavaMail发送邮件的5个步骤
        //1、创建session
        Session session = Session.getInstance(prop);
        //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
        session.setDebug(true);
        //2、通过session得到transport对象
        Transport ts = null;
		try {
			ts = session.getTransport();
		} catch (NoSuchProviderException e) {
			logger.info("session获取transport对象失败");
		}
        //3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
        try {
			ts.connect("smtp.qq.com", "1298760615@qq.com", "uqskmfuwimnjjijj");
		} catch (MessagingException e) {
			logger.info("发件人连接邮箱服务器失败");
		}
        //4、创建邮件
        Message message = null;
		try {
			message = createSimpleMail(session,mail);
		} catch (Exception e) {
			logger.info("创建邮件失败");
		}
        //5、发送邮件
        try {
			ts.sendMessage(message, message.getAllRecipients());
		} catch (MessagingException e) {
			logger.info("发送邮件失败");
		}
        try {
			ts.close();
		} catch (MessagingException e) {
			logger.info("邮箱连接关闭失败");
		}
        logger.info("邮件发送成功!");
		return true;
    }
}