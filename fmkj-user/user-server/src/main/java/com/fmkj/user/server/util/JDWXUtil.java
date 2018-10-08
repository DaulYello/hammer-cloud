package com.fmkj.user.server.util;

import com.alibaba.fastjson.JSONObject;
import com.fmkj.user.dao.domain.HcAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class JDWXUtil {

	@Value("${cardPath}")
	private String cardPath;

	@Value("${cardPwd}")
	private String cardPwd;

	@Value("${wordPath}")
	private String wordPath;

	/**
	 * 身份认证检测
	 * @param ha
	 * @return
	 */

	public synchronized Boolean cardRealName(HcAccount ha){
		RequestModel model = new RequestModel();
		model.setGwUrl(cardPath);
		model.setAppkey(cardPwd);
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("cardNo", ha.getCardnum()); // 访问参数
		queryMap.put("realName", ha.getName()); // 访问参数
		model.setQueryParams(queryMap);
		WxApiCall call = new WxApiCall();
		call.setModel(model);
		String request = call.request();

		JSONObject jb = JSONObject.parseObject(request);

		Object code = jb.get("code");
		if (!"10000".equals(code)) {
			return false;
		}
		JSONObject result = (JSONObject) jb.get("result");

		JSONObject resp = (JSONObject) result.get("result");

		Boolean isok = (Boolean) resp.get("isok");
		if (!isok) {
			return false;
		}
		return true;
	}
	
	/**
	 * 敏感词汇检测
	 */
	public synchronized String wordIsOk(String word) {
		RequestModel model = new RequestModel();
		model.setGwUrl(wordPath);
		model.setAppkey(cardPwd);
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("src",word); //访问参数
		model.setQueryParams(queryMap);
		WxApiCall call = new WxApiCall();
		call.setModel(model);
		String request = call.request();
		JSONObject jb = JSONObject.parseObject(request);

		Object code = jb.get("code");
		if (!"10000".equals(code)) {
			return "500";
		}
		JSONObject jsonObject = (JSONObject) jb.get("result");
		String result =  (String) jsonObject.get("result");
		return result;
	}
}
