package com.fmkj.race.server.util;

import java.math.BigDecimal;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：17:29 2018/8/31 0031
 * @ Description：计算活动手续费工具类
 */
public class PoundageUtil {



    /**
     * @author yangshengbin
     * @Description：计算活动的手续费
     * @date 2018/8/31 0031 17:30
     * @param  price,  premium, pnumber
     * @returnDouble
    */
    public static Double getpoundage(Double price, Double premium,Integer pnumber) {

        double fee = 0.0;
        //计算溢价部分
        double yj = premium*price*pnumber;
        if (premium>=0&&premium<0.05) {
            fee = 0.01*yj;
        }
        if (premium>=0.05&&premium<0.1) {
            fee = 0.02*yj;
        }
        if (premium>=0.1&&premium<0.2) {
            fee = 0.04*yj;
        }
        if (premium>=0.2&&premium<0.35) {
            fee = 0.07*yj;
        }
        if (premium>=0.35&&premium<=0.5) {
            fee = 0.11*yj;
        }
        if (fee<=1.0) {
            fee=1.0;
        }
        return fee;
    }


}
