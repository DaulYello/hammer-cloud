package com.fmkj.race.server.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.fmkj.common.base.BaseServiceImpl;
import com.fmkj.common.util.PropertiesUtil;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.*;
import com.fmkj.race.dao.dto.GcActivityDto;
import com.fmkj.race.dao.mapper.*;
import com.fmkj.race.dao.queryVo.GcBaseModel;
import com.fmkj.race.server.service.GcActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @ Author     ：yangshengbin
 * @ Date       ：16:17 2018/8/29 0029
 * @ Description：活动业务接口实现类
 */
@Service
@Transactional
public class GcActivityServiceImpl extends BaseServiceImpl<GcActivityMapper,GcActivity> implements GcActivityService{


    @Autowired
    private GcActivityMapper gcActivityMapper;

    @Autowired
    private GcMessageMapper gcMessageMapper;

    @Autowired
    private GcActivitytypeMapper gcActivitytypeMapper;

    @Autowired
    private GcNoticeMapper gcNoticeMapper;

    @Autowired
    private GcPimageMapper gcPimageMapper;

    @Override
    /**
     * @author yangshengbin
     * @Description：活动广场分页查询所有活动,只查询活动中(status=2)
     * @date 2018/9/4 0004 14:56
     * @param gcBaseModel
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */
    public List<GcActivityDto> queryAllActivityByPage(Pagination pagination, GcBaseModel gcBaseModel) {
        return gcActivityMapper.queryAllActivityByPage(pagination, gcBaseModel);
    }

    /**
     * @author yangshengbin
     * @Description：传入活动id查询活动详情
     * @date 2018/9/4 0004 15:43
     * @param gcBaseModel
     * @return java.util.HashMap<java.lang.String,java.lang.Object>
    */
    public HashMap<String, Object> queryActivityById(GcBaseModel gcBaseModel) {
        HashMap<String, Object> map = gcActivityMapper.queryActivityById(gcBaseModel);
        return map;
    }


    /**
     * @author yangshengbin
     * @Description：传入uid查询用户参与的活动    status：1进行中  2已锤到的  3.已结束
     * @date 2018/9/4 0004 17:14
     * @param gcBaseModel
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */
    public  List<GcActivityDto> queryMyJoinActivityByUid(Pagination pagination,GcBaseModel gcBaseModel) {
        return gcActivityMapper.queryMyJoinActivityByUid(pagination, gcBaseModel);
    }




    /**
     * @author yangshengbin
     * @Description：传入uid查询用户发起的活动   status：0:待审核 1:驳回 2:活动中 3：已结束 4：活动异常 5：活动失败
     * @date 2018/9/4 0004 17:54
     * @param gcBaseModel
     * @return java.util.List<java.util.HashMap<java.lang.String,java.lang.Object>>
    */
    public List<GcActivityDto> queryMyStartActivityByUid(Pagination pagination, GcBaseModel gcBaseModel) {
        return gcActivityMapper.queryMyStartActivityByUid(pagination, gcBaseModel);
    }


    /**
     * 插入发起活动
     * @param ga
     * @param activityImagePath
     * @param activityImageIpPath
     * @return
     */
    @Override
    public boolean addGcActivity(GcActivity ga, MultipartFile[] file, String activityImagePath, String activityImageIpPath) {
        int row = gcActivityMapper.insert(ga);
        if(row > 0){
            boolean result = addNoticeAndMessage(ga.getStartid(), ga.getTypeid());
            if(result){
                //上传活动的文件
                if(StringUtils.isNotNull(file)&&file.length>0) {
                    int i = 1;
                    for (MultipartFile multipartFile : file) {
                        String fileName = null;
                        try {
                            fileName = PropertiesUtil.uploadImage(multipartFile, activityImagePath);
                        } catch (IOException e) {
                            throw new RuntimeException("上传活动图片异常：" + e.getMessage());
                        }
                        GcPimage gp = new GcPimage();
                        gp.setAid(ga.getId());
                        gp.setFlag(i++);
                        gp.setImageurl(activityImageIpPath + fileName);
                        gcPimageMapper.insert(gp);
                    }
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * 发起活动插入信息
     * @param startid
     * @return
     */
    public boolean addNoticeAndMessage(Integer startid,Integer typeid) {
        //查询活动类型
        GcActivitytype gcActivitytype = gcActivitytypeMapper.selectById(typeid);
        String type = gcActivitytype.getType();
        GcMessage gcMessage = new GcMessage();
        gcMessage.setTime(new Date());
        gcMessage.setMessage("您已发起了"+type+"溢价活动，系统审核通过后，活动完成系统将扣除相应后的资产包发送到您的账户，详情请查询活动发起规则。");
        gcMessage.setType(0);
        int row = gcMessageMapper.insert(gcMessage);
        if(row > 0){
            GcNotice gn = new GcNotice();
            gn.setFlag(1);
            gn.setUid(startid);
            gn.setMid(gcMessage.getId());
            gcNoticeMapper.insert(gn);
            return true;
        }
        return false;
    }


    /**
     * 查询中奖人信息
     * @param gcActivity
     * @return
     */
    @Override
    public HashMap<String, Object> queryActivityByUserId(GcActivity gcActivity) {
        return gcActivityMapper.queryActivityByUserId(gcActivity);
    }


    /**
     * 传入uid查询用户未处理的活动
     * @param gcBaseModel
     * @return
     */
    @Override
    public List<GcActivityDto> queryMyUntreatedActivityByUid(Pagination pagination, GcBaseModel gcBaseModel) {
        return gcActivityMapper.queryMyUntreatedActivityByUid(pagination, gcBaseModel);
    }
}
