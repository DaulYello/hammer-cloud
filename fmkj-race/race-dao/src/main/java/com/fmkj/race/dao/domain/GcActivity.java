package com.fmkj.race.dao.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：15:58 2018/8/29 0029
 * @ Description：活动实体类
 */
@TableName("gc_activity")
public class GcActivity extends Model<GcActivity> implements Serializable{

    /**
     *主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 竟锤发起人的id
     */
    private Integer startid;

    /**
     * 活动结束，中奖用户的id
     */
    private Integer getid;

    /**
     * 对应活动类型表gc_activitytype中的主键
     */
    private Integer typeid;

    /**
     * 合约地址
     */
    private String contract;

    /**
     * 是否缴纳保证金 0 ,1
     */
    private Integer isbond;

    /**
     * 发起人的地点
     */
    private String place;

    /**
     * 产品的名称
     */
    private String pname;

    /**
     * 产品的数量
     */
    private Integer pnumber;

    /**
     * 产品价格
     */
    private Double price;

    /**
     * 产品的溢价率
     */
    private Double premium;

    /**
     * 产品的描述详情
     */
    private String pdescribe;

    /**
     * 活动(竟锤)的状态 0:待审核 1:驳回 2:活动中 3：已结束 4：活动异常 5：活动失败
     */
    private Integer status;

    /**
     * 发货的标志0：没发货 1：已发货
     */
    private Integer delivergoodstatus;

    /**
     * 收货的标志 0：没收货 1：已收货
     */
    private Integer collectgoodstatus;

    /**
     * 发起活动（竟锤）的时间
     */
    private Date time;

    /**
     * 开始时间
     */
    private Date begintime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 参与活动人数
     */
    private Integer num;

    /**
     * 票面值
     */
    private Double par;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartid() {
        return startid;
    }

    public void setStartid(Integer startid) {
        this.startid = startid;
    }

    public Integer getGetid() {
        return getid;
    }

    public void setGetid(Integer getid) {
        this.getid = getid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Integer getIsbond() {
        return isbond;
    }

    public void setIsbond(Integer isbond) {
        this.isbond = isbond;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getPnumber() {
        return pnumber;
    }

    public void setPnumber(Integer pnumber) {
        this.pnumber = pnumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public String getPdescribe() {
        return pdescribe;
    }

    public void setPdescribe(String pdescribe) {
        this.pdescribe = pdescribe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelivergoodstatus() {
        return delivergoodstatus;
    }

    public void setDelivergoodstatus(Integer delivergoodstatus) {
        this.delivergoodstatus = delivergoodstatus;
    }

    public Integer getCollectgoodstatus() {
        return collectgoodstatus;
    }

    public void setCollectgoodstatus(Integer collectgoodstatus) {
        this.collectgoodstatus = collectgoodstatus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPar() {
        return par;
    }

    public void setPar(Double par) {
        this.par = par;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
