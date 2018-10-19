package com.fmkj.common.comenum;

/**
 * @Description:
 * 签到       1飞羽/次
 * 参与活动	  2飞羽/次
 * 发布活动	 10飞羽/次
 * 购买CNT	 2飞羽/次
 * 邀请人注册	 5飞羽/次
 * 上传头像	 5飞羽
 * 绑定邮箱	 10飞羽
 * 自己注册	 10飞羽
 * 绑定支付宝	  15飞羽
 * 绑定微信支付	15飞羽
 * 实名认证	 20飞羽
 * 完成任务	 1飞羽/次
 * 充值虚拟货币	5飞羽/次
 * 任务中心发布任务	10飞羽/次
 * 兑换飞羽	 1飞羽/1CNT
 * @Author: youxun
 * @Version: 1.0
 **/
public enum PointEnum {
    //签到
    SIGN_IN(1,1D),
    //参与活动
    PART_ACITIVITY(2,2D),
    //发布活动
    PUBLISH_ACITIVITY(3,10D),
    //购买CNT
    BUY_CTN(4,2D),

    //兑换飞羽
    EXCHANGE_POINT(5,1D),

    //邀请人注册
    INVIT_REGISTER(6,5D),

    /**
     * 7 实名认证在后台管理系统操作
     */

    //上传头像
    UPLOAD_HEAD(8,5D),

    //绑定邮箱
    BIND_EMAIL(9,10D),

    //充值
    CHARGE(10,5D),

    //自己注册
    SELF_REGISTER(11,10D),

    //绑定支付宝
    BIND_ALIPAY(12,15D),
    //绑定微信支付
    BIND_WEBCHAT_PAY(13,15D),
    //完成任务
    FINISH_TASK(14,1D),
    //任务中心发布任务
    PUBLISH_TASK(15,10D);



    public int pointId;
    public Double pointNum;

    private PointEnum(int pointId, double pointNum) {
        this.pointId = pointId;
        this.pointNum = pointNum;
    }

    public int getPointId() {
        return pointId;
    }

    public void setPointId(int pointId) {
        this.pointId = pointId;
    }

    public double getPointNum() {
        return pointNum;
    }

    public void setPointNum(double pointNum) {
        this.pointNum = pointNum;
    }
}
