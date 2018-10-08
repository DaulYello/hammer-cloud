package com.fmkj.race.server.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(name="FMKJ-USER", fallback = BmListApi.HcTokenClientFallBack.class)
public interface BmListApi {

    @GetMapping("/bmList/isBlacker")
    Boolean isActivityBlack(@RequestParam HashMap<String, Object> params);

    @Component
    public static class HcTokenClientFallBack implements BmListApi {
        @Override
        public Boolean isActivityBlack(HashMap<String, Object> params) {
            return false;
        }
    }
}
