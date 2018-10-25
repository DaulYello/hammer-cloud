package com.fmkj.race.server.runner;

import com.alibaba.fastjson.JSON;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RaceLineRunner implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceLineRunner.class);

    @Autowired
    private GcJoinactivityrecordService joinactivityrecordService;

    private Long sleepTime = 60000L;

    @Override
    public void run(ApplicationArguments args){
        while (true){
            try {
                LOGGER.info("程序延迟" + sleepTime + "毫秒执行");
                Thread.sleep(sleepTime);
                if(sleepTime > 600000L){//如果大于10分钟
                    sleepTime = 60000L;
                }
                List<JoinActivityDto> recordList =  joinactivityrecordService.queryJoinActivityList();
                if(StringUtils.isEmpty(recordList)){
                    sleepTime += 60000L;
                }else{
                    sleepTime = 1L;
                }

                LOGGER.info("执行上链计划开始，一共执行：" + recordList.size() + "条上链计划=================");
                for(JoinActivityDto dto : recordList){
                    synchronized (this){
                        boolean result = joinactivityrecordService.onChain(dto);
                        if (result){
                            LOGGER.info("恭喜==用户【" + dto.getNickname() +"】上链成功!");
                        }else{
                            LOGGER.info("很抱歉==用户【" + dto.getNickname() +"】上链失败, 请检查执行日志!");
                            break;
                        }
                    }
                }
                LOGGER.info("执行上链计划执行结束==================================结束");
            } catch (InterruptedException e) {
                LOGGER.info("执行上链计出现异常：" + e.getMessage());
            }

        }
    }
}
