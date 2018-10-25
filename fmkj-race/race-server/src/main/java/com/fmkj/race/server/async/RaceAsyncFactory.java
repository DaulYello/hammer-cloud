package com.fmkj.race.server.async;

import com.fmkj.common.util.PropertiesUtil;
import com.fmkj.race.dao.domain.GcActivity;
import com.fmkj.race.dao.domain.GcPimage;
import com.fmkj.race.dao.domain.RaceOperateLog;
import com.fmkj.race.server.service.GcPimageService;
import com.fmkj.race.server.service.RaceLogService;
import com.fmkj.race.server.util.SpringContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public static TimerTask uploadImage(GcActivity ga, MultipartFile[] file, String activityImagePath, String activityImageIpPath) {
        //上传活动的文件
        return new TimerTask() {
            @Override
            public void run() {
                try {
                    int i = 1;
                    for (MultipartFile multipartFile : file) {
                        String fileName = null;
                        try {
                            fileName = PropertiesUtil.uploadImage(multipartFile, activityImagePath);
                        } catch (IOException e) {
                            throw new RuntimeException("上传活动图片异常：" + e.getMessage());
                        }
                        GcPimage gp = new GcPimage();
                        gp.setAid(ga.getId());
                        gp.setFlag(i++);
                        gp.setImageurl(activityImageIpPath + fileName);
                        GcPimageService gcPimageService = SpringContextHandler.getBean(GcPimageService.class);
                        boolean result = gcPimageService.insert(gp);
                        LOGGER.info("=====上传活动图片返回结果===：" + result);
                    }
                } catch (BeansException e) {
                    e.printStackTrace();
                }
            }
        };

    }

}
