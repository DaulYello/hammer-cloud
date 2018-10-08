package com.fmkj.user.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcAccount;
import com.fmkj.user.dao.domain.HcSession;
import com.fmkj.user.server.service.HcAccountService;
import com.fmkj.user.server.service.HcSessionService;
import com.fmkj.user.server.util.CalendarTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 *  权限、token接口写在这里
 */
@RestController
@RequestMapping("/hcPermiss")
public class HcPermissController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcPermissController.class);

    @Autowired
    private HcSessionService hcSessionService;

    @Autowired
    private HcAccountService hcAccountService;

    /**
     * @Description:  获取token状态
     * @Param:  token,map,hcsessionService
     * @return:  Boolean
     * @Author: 杨胜彬
     * @Date: 2018/8/27 0027
     */
    @GetMapping(value = "/getToken")
    public Boolean getTokenStatus(String token) {
        HcSession session = new HcSession();
        session.setToken(token);
        EntityWrapper<HcSession> wrapper = new EntityWrapper<HcSession>(session);
        HcSession h = hcSessionService.selectOne(wrapper);
        if (StringUtils.isNull(h)) {
            LOGGER.error("token不存在");
            return false;// token不存在
        }
        Date etime = h.getEtime();
        CalendarTime clt = new CalendarTime();
        if (etime.getTime() - clt.thisDate().getTime() <= 0) {
            HcAccount hcAccount = new HcAccount();
            hcAccount.setAuthlock(false);

            HcAccount hc = new HcAccount();
            hc.setId(h.getUid());
            EntityWrapper<HcAccount> wra = new EntityWrapper<HcAccount>();
            wra.setEntity(hc);
            boolean result = hcAccountService.update(hcAccount,wra);//把用户登录状态设为0

            Integer id = h.getId();
            HcSession updateHs = new HcSession();
            updateHs.setToken("token expired");

            HcSession hess = new HcSession();
            hess.setId(id);
            EntityWrapper<HcSession> wrahe = new EntityWrapper<HcSession>();
            wrahe.setEntity(hess);
            // 将token设置为空
            hcSessionService.update(updateHs,wrahe);
            LOGGER.error("token过期");
            return false;// 过期
        }
        return true;
    }
}
