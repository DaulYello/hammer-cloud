package com.fmkj.race.server.controller;

import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.server.annotation.RaceLog;
import com.fmkj.race.dao.domain.GcPremium;
import com.fmkj.race.server.service.GcPremiumService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/race/Premiu")
public class GcPremiumController extends BaseController<GcPremium, GcPremiumService> implements BaseApiService<GcPremium> {

//    @Autowired
//    private  GcPremiumService gcPremiumService;

    @ApiOperation(value="查询溢价率信息", notes="按照用户积分查询溢价率信息")
    @PutMapping(value = "/getPremiumByIntegral")
    public BaseResult<GcPremium> getPremiumByIntegral (@RequestBody Map<String, Integer> parm){
        try {
            if(StringUtils.isNull(parm) || parm.isEmpty() == true){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "premax不能为空", "premax不能为空");
            }

            GcPremium premium = service.getPremiumByIntegral( parm.get("integral"));
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", premium);
        } catch (Exception e) {
            throw new RuntimeException("查询异常：" + e.getMessage());
        }
    }

}
