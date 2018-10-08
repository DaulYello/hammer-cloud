package com.fmkj.race.server.hammer.contracts.PuzzleHammer.puzzle;

import java.math.BigInteger;

public class Info {
    /**
     * 竞锤合约活动编号
     */
    private BigInteger cId;
    /**
     * 竞锤合约活动类型
     */
    private BigInteger cType;
    /**
     * 活动门票类型
     */
    private BigInteger tType;
    /**
     * 活动参与人数下限
     */
    private BigInteger cLimit;

    /**
     * 通过参数，构造一个新的Info实例
     *
     * @param cId    竞锤合约活动编号
     * @param cType  竞锤合约活动类型
     * @param tType  活动门票类型
     * @param cLimit 活动参与人数下限
     */
    public Info(BigInteger cId, BigInteger cType, BigInteger tType, BigInteger cLimit) {
        this.cId = cId;
        this.cType = cType;
        this.tType = tType;
        this.cLimit = cLimit;
    }

    /**
     * @return 竞锤合约活动编号
     */
    public BigInteger getcId() {
        return cId;
    }

    /**
     *
     * @return 竞锤合约活动类型
     */
    public BigInteger getcType() {
        return cType;
    }

    /**
     *
     * @return 活动门票类型
     */
    public BigInteger gettType() {
        return tType;
    }

    /**
     *
     * @return 活动参与人数下限
     */
    public BigInteger getcLimit() {
        return cLimit;
    }
}
