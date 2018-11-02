package com.fmkj.user.server.async;

import com.fmkj.user.dao.domain.UserOperateLog;
import com.fmkj.user.server.service.UserLogService;
import com.fmkj.user.server.util.SpringContextUtil;
import org.springframework.beans.BeansException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author youxun
 */
public class AsyncFactory {
    /**
     * 操作日志记录
     *
     * @return 任务task
     */
    public static TimerTask recordOper(final UserOperateLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    UserLogService raceLogService = SpringContextUtil.getBean(UserLogService.class);
                    raceLogService.insert(operLog);
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static TimerTask sendEmail(String email) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    //建立邮件消息
                    SimpleMailMessage mainMessage = new SimpleMailMessage();
                    //发送者
                    mainMessage.setFrom("yooxuu@163.com");
                    //接收者
                    mainMessage.setTo(email);
                    //发送的标题
                    mainMessage.setSubject("绿天鹅邮箱绑定通知");
                    //发送的内容
                    mainMessage.setText("欢迎使用绿天鹅APP邮箱绑定，本邮件由系统自动发出，请勿回复。\n" +
                            "感谢您的使用!\n" +
                            "贵州风云科技有限公司");
                    SpringContextUtil.getBean(JavaMailSender.class).send(mainMessage);
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
