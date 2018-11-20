package com.fmkj.race.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.race.dao.domain.GcAddress;
import com.fmkj.race.server.annotation.RaceLog;
import com.fmkj.race.server.service.GcAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * @ Author     ：yangshengbin
 * @ Date       ：10:25 2018/9/3 0003
 * @ Description：用户地址控制层
 */
@RestController
@RequestMapping("/gcaddress")
@DependsOn("springContextHandler")
@Api(tags ={ "用户地址"},description = "用户地址接口-网关路径/api-race")
public class GcAddressController extends BaseController<GcAddress,GcAddressService> implements BaseApiService<GcAddress> {


    private static final Logger LOGGER = LoggerFactory.getLogger(GcAddressController.class);

    @Autowired
    private GcAddressService gcAddressService;//用户地址接口




    /**
     * @author yangshengbin
     * @Description： 新增用户地址
     * @date 2018/9/3 0003 10:29
     * @param  gcAddress
     * @return
    */
    @ApiOperation(value="新增用户地址", notes="新增用户地址")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "新增用户地址")
    @PostMapping("/insertNewAddress")
    public BaseResult insertNewAddress(@RequestBody GcAddress gcAddress) {
        try {
            if(StringUtils.isNull(gcAddress) || StringUtils.isNull(gcAddress.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "UID不能为空", "UID不能为空");
            }
            LOGGER.info("新增地址之前，先查询该用户是否已经有地址了，如果有了，直接插入，如果没有，将这条地址设为默认地址。");
            EntityWrapper<GcAddress> entityWrapper = new EntityWrapper<GcAddress>(gcAddress);
            List<GcAddress> addresses=gcAddressService.selectList(entityWrapper);
            if(addresses.size()>0){
                LOGGER.info("已有旧地址！");
                gcAddress.setStatus(0);
            }else{
                LOGGER.info("没有旧地址，所以把这条新地址设为默认地址！");
                gcAddress.setStatus(1);
            }
            gcAddress.setLock(0);
            gcAddress.setCreateTime(new Date());
            gcAddressService.insert(gcAddress);
            return new BaseResult(BaseResultEnum.SUCCESS,"数据添加成功");
        }
        catch (Exception e) {
            throw new RuntimeException("新增异常：" + e.getMessage());
        }
    }



    /**
     * @author yangshengbin
     * @Description：传入地址id删除地址
     * @date 2018/9/3 0003 10:55
     * @param gcAddress
     * @return
    */
    @ApiOperation(value="传入地址id删除地址", notes="传入地址id删除地址")
    @PostMapping("/deleteAddressById")
    public BaseResult deleteAddressById(@RequestBody GcAddress gcAddress){
        try {
            if( gcAddress.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", "ID不能为空");
            }
            gcAddressService.deleteById(gcAddress.getId());
            LOGGER.info("删除地址，后查询用户的地址");
            GcAddress address = new GcAddress();
            address.setUid(gcAddress.getUid());
            address.setStatus(1);
            EntityWrapper<GcAddress> entityWrapper = new EntityWrapper<GcAddress>(address);
            GcAddress gcAddress1= gcAddressService.selectOne(entityWrapper);
            if(gcAddress1 == null){
                GcAddress addre = new GcAddress();
                addre.setUid(gcAddress.getUid());
                List<GcAddress> addresses = gcAddressService.selectListByTimeOrder(addre);
                LOGGER.info("通过用户id查询用户的收货地址"+addresses.size());
                if (addresses.size()>0){
                    GcAddress site = addresses.get(0);
                    site.setStatus(1);
                    site.setUpdateTime(new Date());
                    gcAddressService.updateAllColumnById(site);
                }
            }
            return new BaseResult(BaseResultEnum.SUCCESS,"数据删除成功");
        } catch (Exception e) {
            throw new RuntimeException("删除异常：" + e.getMessage());
        }
    }


    @ApiOperation(value="修改地址", notes="修改地址")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "修改地址")
    @PostMapping("/updateAddressById")
    public BaseResult updateAddressById(@RequestBody GcAddress gcAddress){
        try {
            if( gcAddress.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", "ID不能为空");
            }
            gcAddressService.updateById(gcAddress);
            return new BaseResult(BaseResultEnum.SUCCESS,"数据修改成功");
        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
        }
    }



    @ApiOperation(value="修改默认地址", notes="修改默认地址")
    @RaceLog(module= LogConstant.Gc_Activity, actionDesc = "修改默认地址，参数：id,uid")
    @PostMapping("/updateAddressByStatus")
    public BaseResult updateAddressByStatus(@RequestBody GcAddress gcAddress){
        try {
            if(StringUtils.isNull(gcAddress.getId())&&StringUtils.isNull(gcAddress.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", "ID不能为空");
            }
            boolean flag = gcAddressService.updateAddressByStatus(gcAddress);
            if (!flag){
                return new BaseResult(BaseResultEnum.ERROR,"数据修改失败");
            }
            return new BaseResult(BaseResultEnum.SUCCESS,"数据修改成功");
        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
        }

    }





    /**
     * @author yangshengbin
     * @Description：查询用户地址
     * @date 2018/9/3 0003 14:16
     * @param gc
     * @return
     */
    @ApiOperation(value="查询用户所有收货地址", notes="查询用户所有收货地址")
    @PutMapping("/queryAllAddressByUid")
    public BaseResult<Page<GcAddress>> queryAllAddressByUid(@RequestBody GcAddress gc){
        try {
            if(StringUtils.isNull(gc.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            Page<GcAddress> tPage =new Page<GcAddress>();
            GcAddress gcAddress = new GcAddress();
            gcAddress.setUid(gc.getUid());
            EntityWrapper<GcAddress> entityWrapper = new EntityWrapper<GcAddress>(gcAddress);
            return new BaseResult(BaseResultEnum.SUCCESS,service.selectPage(tPage, entityWrapper));
        } catch (Exception e) {
            throw new RuntimeException("查询商品列表异常：" + e.getMessage());
        }
    }
    @ApiOperation(value="通过用户id，查询用户是否有已填地址，参数(用户的id)：uid ", notes="参与活动之前，先查询用户是否填了收货地址")
    @PutMapping("/judgeFillAddressed")
    public BaseResult judgeFillAddressed(@RequestBody GcAddress address) {

        try {
            if(StringUtils.isNull(address) ||StringUtils.isNull(address.getUid())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            EntityWrapper<GcAddress> entityWrapper = new EntityWrapper<GcAddress>(address);
            List<GcAddress> addresses=gcAddressService.selectList(entityWrapper);
            boolean flag = false;
            if (addresses.size()>0){
                flag = true;
            }
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", flag);
        } catch (Exception e) {
            throw new RuntimeException("查询用户是否有已填地址异常：" + e.getMessage());
        }
    }
}
