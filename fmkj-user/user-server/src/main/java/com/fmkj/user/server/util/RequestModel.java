package com.fmkj.user.server.util;

import com.fmkj.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by swh on 17-11-20.
 */
public class RequestModel {
	private String filePath;
	private String bodyStr;
	private String appkey;
	private String gwUrl;
	private Map<String,Object> queryParams = new HashMap<String, Object>();
	private Map<String,String> headerParams = new HashMap<String, String>();
	private final String USER_AGENT = "WxJavaSdk/1.0";

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getBodyStr() {
		return bodyStr;
	}

	public void setBodyStr(String bodyStr) {
		this.bodyStr = bodyStr;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getGwUrl() {
		return gwUrl;
	}

	public void setGwUrl(String gwUrl) {
		this.gwUrl = gwUrl;
	}

	public Map<String, Object> getQueryParams() {
		if (null != queryParams && !queryParams.containsKey("appkey") && StringUtils.isNotEmpty(appkey)) {
			queryParams.put("appkey", appkey);
		}
		return queryParams;
	}

	public void setQueryParams(Map<String, Object> queryParams) {
		this.queryParams = queryParams;
	}
	public Map<String, String> getHeaderParams() {
		if (null != headerParams && headerParams.get("User-Agent") == null) {
			headerParams.put("User-Agent",USER_AGENT);
		}
		return headerParams;
	}

	public String getMethod(){
		return StringUtils.isEmpty(filePath) && StringUtils.isEmpty(bodyStr) ? "get" : "post";
	}
}
