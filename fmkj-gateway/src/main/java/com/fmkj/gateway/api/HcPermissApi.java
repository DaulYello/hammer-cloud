package com.fmkj.gateway.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="FMKJ-USER", fallback = HcPermissApi.HcPermissClientFallBack.class)
public interface HcPermissApi {

    //作为客户端去调用race里面的selectPage方法、通过@GetMapping("/race/selectPage")匹配到
    //这里注意路径一定要写对
    @GetMapping("/hcPermiss/getToken")
    public Boolean queryToken(@RequestParam("token") String token);

    @Component
    public static class HcPermissClientFallBack implements HcPermissApi{

        @Override
        public Boolean queryToken(String token) {
            return false;
        }
    }
}
