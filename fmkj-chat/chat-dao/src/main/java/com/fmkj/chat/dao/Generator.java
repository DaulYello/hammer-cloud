package com.fmkj.chat.dao;



import com.fmkj.common.util.GeneratorUtil;
import com.fmkj.common.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码生成器
 */
public class Generator {
    // 根据命名规范，只修改此常量值即可
    private static String MODULE = "fmkj-chat";
    private static String DATABASE = "hammerchain";
    private static String TABLE_NAME = "fm_news";
    private static String PACKAGE_NAME = "com.fmkj.chat";
    private static String DAO_NAME = "chat-dao";
    private static String SERVICE_NAME = "chat-server";
    private static String JDBC_DRIVER = PropertiesUtil.getInstance("generator").get("generator.jdbc.driver");
    private static String JDBC_URL = PropertiesUtil.getInstance("generator").get("generator.jdbc.url");
    private static String JDBC_USERNAME = PropertiesUtil.getInstance("generator").get("generator.jdbc.username");
    private static String JDBC_PASSWORD = PropertiesUtil.getInstance("generator").get("generator.jdbc.password");
    // 需要insert后返回主键的表配置，key:表名,value:主键名
    private static Map<String, String> LAST_INSERT_ID_TABLES = new HashMap<>();
    static {
        LAST_INSERT_ID_TABLES.put("fm_news", "id");
    }

    /**
     * 自动代码生成
     * @param args
     */
    public static void main(String[] args) throws Exception {
        GeneratorUtil.generator(JDBC_DRIVER, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, MODULE, DATABASE, TABLE_NAME, PACKAGE_NAME, LAST_INSERT_ID_TABLES, DAO_NAME,  SERVICE_NAME);
    }
}
