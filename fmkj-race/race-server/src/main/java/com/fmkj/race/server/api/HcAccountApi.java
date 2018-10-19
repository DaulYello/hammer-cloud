package com.fmkj.race.server.api;

import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcPointsRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="FMKJ-USER", fallback = HcAccountApi.HcAccountApiFallBack.class)
public interface HcAccountApi {

    @PostMapping("/hcAccount/updateUserP")
    public Boolean updateUserP(@RequestBody HcAccount hc);

    @PostMapping("/hcAccount/grantUserP")
    public Boolean grantUserP(@RequestBody HcAccount hc);

    @GetMapping("/hcAccount/getAccountById")
    public HcAccount selectHcAccountById(@RequestParam("id") Integer id);

    @PostMapping("/hcAccount/addHcPointsRecord")
    Boolean addHcPointsRecord(HcPointsRecord hcPointsRecord);

    @Component
    public static class HcAccountApiFallBack implements HcAccountApi {
        @Override
        public Boolean updateUserP(HcAccount hc) {
            return false;
        }

        @Override
        public Boolean grantUserP(HcAccount hc) {
            return false;
        }

        @Override
        public HcAccount selectHcAccountById(Integer id) {
            return null;
        }

        @Override
        public Boolean addHcPointsRecord(HcPointsRecord uid) {
            return false;
        }

    }
}
