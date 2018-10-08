/*
package com.fmkj.order.client;

import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

*/
/**
 * @Auther: youxun
 * @Date: 2018/8/25 15:53
 * @Description:
 *//*

//name表示访问哪个应用的接口、对应配置文件中的name值
@FeignClient(name="FMKJ-RACE", fallback = RaceClient.RaceClientFallBack.class)
public interface RaceClient{

    //作为客户端去调用race里面的selectPage方法、通过@GetMapping("/race/selectPage")匹配到
    //这里注意路径一定要写对
    @GetMapping("/race/selectPage")
    BaseResult<Page<HcAccount>> queryRacePage(@RequestParam HashMap<String, Object> params);

    @Component
    public static class RaceClientFallBack implements RaceClient{
        @Override
        public BaseResult<Page<HcAccount>> queryRacePage(HashMap<String, Object> params) {
            System.out.println("服务降级处理");
            return new BaseResult<Page<HcAccount>>(BaseResultEnum.DEGRAD, null);
        }
    }
}
*/
