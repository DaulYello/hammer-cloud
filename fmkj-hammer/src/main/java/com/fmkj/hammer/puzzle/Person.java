package com.fmkj.hammer.puzzle;

import java.math.BigInteger;

public class Person {
    /**
     * 人员名称
     */
    private String Name;
    /**
     * 人员编号
     */
    private BigInteger ID;

    /**
     * 通过参数，构造一个新的Person实例
     *
     * @param name 人员名称
     * @param ID   人员编号
     */
    public Person(String name, BigInteger ID) {
        Name = name;
        this.ID = ID;
    }

    /**
     *
     * @return 人员名称
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @return 人员编号
     */
    public BigInteger getID() {
        return ID;
    }
}
