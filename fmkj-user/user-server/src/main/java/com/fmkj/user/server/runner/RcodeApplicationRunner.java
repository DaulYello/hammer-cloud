package com.fmkj.user.server.runner;

import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcRcode;
import com.fmkj.user.server.service.HcRcodeService;
import com.fmkj.user.server.util.Rcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 处理用户没有邀请码的问题，跑完注释掉
 */
/*@Component//被spring容器管理
@Order(1)//如果多个自定义ApplicationRunner，用来标明执行顺序*/
public class RcodeApplicationRunner implements ApplicationRunner {
    @Autowired
    private HcRcodeService hcRcodeService;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        System.out.println("-------------->" + "初始化扫描开始喽，now=" + new Date());
        int count = 0;

        while (true){
            List<HcRcode> hcRcodeList = hcRcodeService.selectRecodList();
            List<HcRcode> inserList = new ArrayList<>();
            if(hcRcodeList == null || hcRcodeList.size() == 0){
                System.out.println("跳出当前while循环");
                break;
            }
            for(HcRcode hc : hcRcodeList){
                String code = hc.getCode();
                if(StringUtils.isEmpty(code)){
                    HcRcode rc = new HcRcode();
                    rc.setUid(hc.getUid());
                    rc.setCode(Rcode.getRcode(hc.getUid()));
                    inserList.add(rc);
                }
            }
            count += inserList.size();
            boolean result = hcRcodeService.insertBatch(inserList);
            System.out.println("批量插入更新返回：" + result);
        }

        System.out.println("-------------->" + "执行结束啦，end=" + new Date());
        System.out.println("-------------->" + "一共执行了" + count + "条数据");


    }
}
