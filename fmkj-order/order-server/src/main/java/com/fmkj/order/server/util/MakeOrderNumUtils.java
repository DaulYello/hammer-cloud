package com.fmkj.order.server.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MakeOrderNumUtils{

    /**
     * 生成订单编号
     * @return
     */
    public static String createOrderNum() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }


    /**
     * 8位不无重复商品编码
     * @return
     */
    public static String createProductNum() {
        String no="";
        int num[]=new int[8];
        int c=0;
        for (int i = 0; i < 8; i++) {
            num[i] = new Random().nextInt(10);
            c = num[i];
            for (int j = 0; j < i; j++) {
                if (num[j] == c) {
                    i--;
                    break;
                }
            }
        }
        if (num.length>0) {
            for (int i = 0; i < num.length; i++) {
                no+=num[i];
            }
        }
        return no;
    }
}
