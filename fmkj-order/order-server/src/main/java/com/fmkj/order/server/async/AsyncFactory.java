package com.fmkj.order.server.async;

import com.fmkj.order.dao.domain.OperateLog;

import com.fmkj.order.server.service.OrderLogService;
import com.fmkj.order.server.util.SpringContextHolder;
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
    public static TimerTask recordOper(final OperateLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    OrderLogService raceLogService = SpringContextHolder.getBean(OrderLogService.class);
                    raceLogService.insert(operLog);
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
