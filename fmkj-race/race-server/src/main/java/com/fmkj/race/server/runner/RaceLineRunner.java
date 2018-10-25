package com.fmkj.race.server.runner;

import com.fmkj.race.dao.dto.JoinActivityDto;
import com.fmkj.race.server.service.GcJoinactivityrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class RaceLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RaceLineRunner.class);

    @Autowired
    private GcJoinactivityrecordService joinactivityrecordService;

    @Scheduled(cron = "0 0/10 * * * ?") // 每10分钟执行一次
    public void run() {
        LOGGER.info("===================上链轮询开始扫描=============================");
        //每10分钟最多执行5条上链计划
        List<JoinActivityDto> recordList =  joinactivityrecordService.queryJoinActivityList();
        LOGGER.info("执行上链计划开始，一共执行：" + recordList.size() + "条上链计划=================");
        for(JoinActivityDto dto : recordList){
            synchronized (this){
                boolean result = joinactivityrecordService.onChain(dto);
                if (result){
                    LOGGER.info("恭喜==用户【" + dto.getNickname() +"】参与活动【"+dto.getAid()+"】上链成功!");
                }else{
                    LOGGER.info("很抱歉==用户【" + dto.getNickname() +"】参与活动【"+dto.getAid()+"】上链失败, 请检查执行日志!");
                    break;
                }
            }
        }
        LOGGER.info("执行上链计划结束==================================结束");


    }

}
