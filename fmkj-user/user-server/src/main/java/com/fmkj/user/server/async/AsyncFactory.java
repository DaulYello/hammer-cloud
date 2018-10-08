package com.fmkj.user.server.async;

import com.fmkj.user.dao.domain.UserOperateLog;
import com.fmkj.user.server.service.UserLogService;
import com.fmkj.user.server.util.SpringContextUtil;
import org.springframework.beans.BeansException;

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

}
