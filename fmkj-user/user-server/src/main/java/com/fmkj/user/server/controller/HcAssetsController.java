package com.fmkj.user.server.controller;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.StringUtils;
import com.fmkj.user.dao.domain.HcAssets;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.service.HcAssetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/HcAssets")
@DependsOn("springContextUtil")
@Api(tags ={ "钱包地址"},description = "钱包地址接口-网关路径/api-user")
public class HcAssetsController extends BaseController<HcAssets,HcAssetsService> implements BaseApiService<HcAssets> {
	private static final Logger LOGGER = LoggerFactory.getLogger(HcUserimageController.class);

	@Autowired
	private HcAssetsService hcassetsService;

	/**
	 * 查询用户的资产信息
	 * @param asset
	 * @return
	 */
	@ApiOperation(value="查询用户的资产信息",notes="查询用户的资产信息,参数：userId")
	@UserLog(module= LogConstant.HC_ASSETS, actionDesc = "查询用户的资产信息")
	@PutMapping("/queryAssetsInfo")
	public BaseResult queryAssetsInfo(@RequestBody HcAssets asset) {

		try{
			if(StringUtils.isNull(asset.getUserId())){
				return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
			}
			EntityWrapper<HcAssets> wrapper = new EntityWrapper<>(asset);
			List<HcAssets> assets= hcassetsService.selectList(wrapper);
			return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询用户的资产信息成功!", assets);
		}catch (Exception e){
			return new BaseResult(BaseResultEnum.ERROR.getStatus(), "查询用户的资产信息失败!", e.getMessage());
		}
	}

	/**
	 * 保存用户的资产信息
	 * @param assets
	 * @return
	 */
	@ApiOperation(value="保存用户的资产信息",notes="保存用户的资产信息,参数：userId,walletfile,keystore,assetskind")
	@UserLog(module= LogConstant.HC_ASSETS, actionDesc = "保存用户的资产信息")
	@PostMapping("/saveAssetsContent")
	public BaseResult saveAssetsContent(@RequestBody HcAssets assets) {

		if(StringUtils.isNull(assets.getUserId())){
			return  new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空", false);
		}
		if(StringUtils.isNull(assets.getAssetskind())){
			return  new BaseResult(BaseResultEnum.BLANK.status, "资产种类不为空", false);
		}
		if(StringUtils.isNull(assets.getKeystore())){
			return  new BaseResult(BaseResultEnum.BLANK.status, "Keystore文件不能为空", false);
		}
		if(StringUtils.isNull(assets.getWalletfile())){
			return  new BaseResult(BaseResultEnum.BLANK.status, "钱包地址不能为空", false);
		}
		assets.setCreatetime(new Date());
		boolean result = hcassetsService.insert(assets);

		if(result){
			return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "保存用户的资产信息成功!", true);
		}else {
			return new BaseResult(BaseResultEnum.ERROR.getStatus(), "保存用户的资产信息失败!", false);
		}
	}
	
	/**
	 * 更新资产信息
	 * @param assets
	 * @return
	 */
	@ApiOperation(value="更新资产信息",notes="更新资产信息,参数：userId,walletfile")
	@UserLog(module= LogConstant.HC_ASSETS, actionDesc = "更新资产信息")
	@PostMapping("/updataAssetsContent")
	public BaseResult updataAssetsContent(@RequestBody HcAssets assets) {
		if(StringUtils.isNull(assets.getUserId())){
			return  new BaseResult(BaseResultEnum.BLANK.status, "用户id不能为空", false);
		}
		if(StringUtils.isNull(assets.getWalletfile())){
			return  new BaseResult(BaseResultEnum.BLANK.status, "钱包地址不能为空", false);
		}
		boolean result = hcassetsService.updateAssetsContent(assets);
		if(result){
			return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "更新资产信息成功!", true);
		}else {
			return new BaseResult(BaseResultEnum.ERROR.getStatus(), "更新资产信息失败!", false);
		}
	}
	
	/**
	 * 删除用户的资产信息，根据传过的用户id和钱包地址
	 * @param assets
	 * @return
	 */
	@ApiOperation(value="删除用户的资产信息",notes="删除用户的资产信息,参数：userId,walletfile")
	@UserLog(module= LogConstant.HC_ASSETS, actionDesc = "删除用户的资产信息")
	@PostMapping("/deleteAssetsContent")
	public BaseResult deleteAssetsContent(@RequestBody HcAssets assets) {

		if(StringUtils.isNull(assets.getUserId())){
			return  new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户id不能为空", false);
		}
		if(StringUtils.isNull(assets.getWalletfile())){
			return  new BaseResult(BaseResultEnum.BLANK.getStatus(), "钱包地址不能为空", false);
		}

		EntityWrapper<HcAssets> wrapper = new EntityWrapper<>(assets);
		boolean result = hcassetsService.delete(wrapper);
		if(result){
			return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "删除用户的资产信息成功!", true);
		}else {
			return new BaseResult(BaseResultEnum.ERROR.getStatus(), "删除用户的资产信息失败!", false);
		}
	}
}