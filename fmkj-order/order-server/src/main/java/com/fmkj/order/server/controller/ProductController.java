package com.fmkj.order.server.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.order.dao.domain.ProductInfo;
import com.fmkj.order.dao.dto.ProductDto;
import com.fmkj.order.dao.queryVo.ProductQueryVo;
import com.fmkj.order.server.annotation.OrderLog;
import com.fmkj.order.server.enmu.ProductEnum;
import com.fmkj.order.server.service.ProductService;
import com.fmkj.order.server.util.MakeOrderNumUtils;
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
 * 商品服务
 */
@RestController
@RequestMapping("/product")
@DependsOn("springContextHolder")
@Api(tags ={ "商品服务"},description = "商品服务接口-网关路径/api-order")
public class ProductController extends BaseController<ProductInfo, ProductService> implements BaseApiService<ProductInfo> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;


    @ApiOperation(value="查询商品列表", notes="分页查询商品列表")
    @OrderLog(module= LogConstant.HC_PRODUCT, actionDesc = "查询商品列表")
    @PutMapping("/getProductPage")
    public BaseResult<Page<ProductDto>> getProductPage(@RequestBody ProductQueryVo productQueryVo){
        try {
            Page<ProductDto> tPage = buildPage(productQueryVo);
            List<ProductDto> list = productService.getProductPage(tPage, productQueryVo);
            tPage.setRecords(list);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", tPage);
        } catch (Exception e) {
           throw new RuntimeException("查询商品列表异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="根据ID查询商品详情", notes="根据ID查询商品详情-入参：ID")
    @PutMapping("/getProductById")
    public BaseResult<ProductInfo> getOrderById(@RequestBody ProductQueryVo productQueryVo){
        try {
            if(StringUtils.isNull(productQueryVo) || StringUtils.isNull(productQueryVo.getId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
            }
            ProductInfo productInfo = productService.selectById(productQueryVo.getId());
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", productInfo);
        } catch (Exception e) {
            throw new RuntimeException("根据ID查询商品异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="删除商品", notes="根据ID删除商品")
    @OrderLog(module= LogConstant.HC_PRODUCT, actionDesc = "删除商品")
    @PostMapping("/deleteById")
    public BaseResult deleteById(@RequestBody ProductInfo productInfo){
        try {
            if(StringUtils.isNull(productInfo) || productInfo.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", "ID不能为空");
            }
            service.deleteById(productInfo.getId());
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "删除成功", "数据删除成功");
        } catch (Exception e) {
            throw new RuntimeException("删除异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="新增商品", notes="新增商品")
    @OrderLog(module= LogConstant.HC_PRODUCT, actionDesc = "新增商品")
    @PostMapping("/addProduct")
    public BaseResult addProduct(@RequestBody ProductInfo productInfo){
        try {
            productInfo.setProductNo(MakeOrderNumUtils.createProductNum());
            productInfo.setProductStock(productInfo.getProductSum());
            productInfo.setStatus(ProductEnum.PRODUCT_ADD.status);
            productInfo.setCreateTime(new Date());
            return super.insert(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("新增异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="修改商品", notes="根据ID修改商品")
    @OrderLog(module= LogConstant.HC_PRODUCT, actionDesc = "修改商品")
    @PostMapping("/updateProduct")
    public BaseResult updateProduct(@RequestBody ProductInfo productInfo){
        try {
            if(StringUtils.isNull(productInfo) || productInfo.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", "ID不能为空");
            }
            productInfo.setUpdateTime(new Date());
            return super.updateById(productInfo);
        } catch (Exception e) {
            throw new RuntimeException("修改异常：" + e.getMessage());
        }
    }


    @ApiOperation(value="发布商品", notes="发布商品、扣除用户等于销售数量的P能量--入参为：id, userId, productSum, productType")
    @OrderLog(module= LogConstant.HC_PRODUCT, actionDesc = "发布商品")
    @PostMapping("/publishProduct")
    public BaseResult publishProduct(@RequestBody ProductInfo productInfo){
        try {
            if(StringUtils.isNull(productInfo) || productInfo.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
            }
            if(StringUtils.isNull(productInfo.getUserId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            if(StringUtils.isNull(productInfo.getProductSum())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "出售数量不能为空", false);
            }
            if(StringUtils.isNull(productInfo.getProductType())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "类型不能为空", false);
            }

            //第一步：更新商品表状态
            //第二步：扣除P能量
            productInfo.setUpdateTime(new Date());
            productInfo.setStatus(ProductEnum.PRODUCT_LINE.status);
            boolean result = productService.publishProduct(productInfo);
            if(result){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "发布商品", "账号扣除: " + productInfo.getProductSum() + "P");
            }else {
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "发布商品", false);
            }

        } catch (Exception e) {
            throw new RuntimeException("出售异常：" + e.getMessage());
        }
    }

    @ApiOperation(value="下架", notes="商品下架、如果库存有剩余，用户需要加P能量--入参为：id, userId, productStock")
    @OrderLog(module= LogConstant.HC_PRODUCT, actionDesc = "下架")
    @PostMapping("/unLineProduct")
    public BaseResult unLineProduct(@RequestBody ProductInfo productInfo){
        try {
            if(StringUtils.isNull(productInfo) || productInfo.getId() == null){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
            }
            if(StringUtils.isNull(productInfo.getUserId())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
            }
            if(StringUtils.isNull(productInfo.getProductStock())){
                return new BaseResult(BaseResultEnum.BLANK.getStatus(), "库存不能为空", false);
            }
            //第一步：更新商品表状态
            productInfo.setUpdateTime(new Date());
            productInfo.setStatus(ProductEnum.PRODUCT_UNLINE.status);
            boolean rs = productService.unLineProduct(productInfo);
            if(rs){
                return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "下架成功", "用户增加: " + productInfo.getProductStock() + "P");
            }else {
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "下架失败", false);
            }

        } catch (Exception e) {
            throw new RuntimeException("下架异常：" + e.getMessage());
        }
    }

    /**
     * 构建查询条件
     * @param productQueryVo
     */
    private EntityWrapper<ProductInfo> transWrapper(ProductQueryVo productQueryVo) {
        EntityWrapper<ProductInfo> entityWrapper = new EntityWrapper<ProductInfo>();
        if(StringUtils.isNotEmpty(productQueryVo.getProductName())){
            entityWrapper.eq("product_name", productQueryVo.getProductName());
        }
        if(StringUtils.isNotEmpty(productQueryVo.getProductNo())){
            entityWrapper.eq("product_no", productQueryVo.getProductNo());
        }
        if(StringUtils.isNotNull(productQueryVo.getProductPrice())){
            entityWrapper.eq("product_price", productQueryVo.getProductPrice());
        }
        if(StringUtils.isNotNull(productQueryVo.getProductStock())){
            entityWrapper.eq("product_stock", productQueryVo.getProductStock());
        }
        if(StringUtils.isNotNull(productQueryVo.getStatus())){
            entityWrapper.eq("status", productQueryVo.getStatus());
        }
        if(StringUtils.isNotNull(productQueryVo.getCategoryType())){
            entityWrapper.eq("category_type", productQueryVo.getCategoryType());
        }
        if(StringUtils.isNotNull(productQueryVo.getUserId())){
            entityWrapper.eq("user_id", productQueryVo.getUserId());
        }
        if(StringUtils.isNotNull(productQueryVo.getCreateTime())){
            entityWrapper.eq("create_time", productQueryVo.getCreateTime());
        }
        if(StringUtils.isNotNull(productQueryVo.getUpdateTime())){
            entityWrapper.eq("update_time", productQueryVo.getUpdateTime());
        }
        if(StringUtils.isNotNull(productQueryVo.getProductType())){
            entityWrapper.eq("product_type", productQueryVo.getProductType());
        }
        // 降序字段
        if(StringUtils.isNotEmpty(productQueryVo.getOrderBy())) {
            entityWrapper.orderBy(productQueryVo.getOrderBy(),false);
        }
        // 升序字段
        if(StringUtils.isNotEmpty(productQueryVo.getOrderByAsc())) {
            entityWrapper.orderBy(productQueryVo.getOrderByAsc(),true);
        }

        return entityWrapper;

    }

    private Page<ProductDto> buildPage(ProductQueryVo productQueryVo) {
        Page<ProductDto> tPage =new Page<ProductDto>(productQueryVo.getPageNo(),productQueryVo.getPageSize());
        if(StringUtils.isNotEmpty(productQueryVo.getOrderBy())){
            tPage.setOrderByField(productQueryVo.getOrderBy());
            tPage.setAsc(false);
        }
        if(StringUtils.isNotEmpty(productQueryVo.getOrderByAsc())){
            tPage.setOrderByField(productQueryVo.getOrderByAsc());
            tPage.setAsc(true);
        }
        return tPage;
    }

}
