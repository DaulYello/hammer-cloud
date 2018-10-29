package com.fmkj.race.server.async;

import com.fmkj.race.dao.domain.RaceOperateLog;
import com.fmkj.race.server.service.RaceLogService;
import com.fmkj.race.server.util.SpringContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author youxun
 */
public class RaceAsyncFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceAsyncFactory.class);

    /**
     * 操作日志记录
     *
     * @return 任务task
     */
    public static TimerTask recordOper(final RaceOperateLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    RaceLogService raceLogService = SpringContextHandler.getBean(RaceLogService.class);
                    raceLogService.insert(operLog);
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
