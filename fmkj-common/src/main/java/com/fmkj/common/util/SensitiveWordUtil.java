package com.fmkj.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
/**
 * @ Author     ：yangshengbin
 * @ Date       ：11:37 2018/8/31 0031
 * @ Description：获取敏感词汇
 */

/**参考DFA算法demo:http://blog.csdn.net/chenssy/article/details/26961957*/
public class SensitiveWordUtil {

    //敏感词库文件路径
    public static String filePath = SensitiveWordUtil.class.getClassLoader().getResource("CensorWords.txt").getPath();
    public static Set<String> words;
    public static Map<String,String> wordMap;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则
    static{
        SensitiveWordUtil.words = readTxtByLine(filePath);
        addBadWordToHashMap(SensitiveWordUtil.words);
    }

    public static Set<String> readTxtByLine(String path){
        Set<String> keyWordSet = new HashSet<String>();
        File file=new File(path);
        if(!file.exists()){      //文件流是否存在
            return keyWordSet;
        }
        BufferedReader reader=null;
        String temp=null;
        //int line=1;
        try{
            //reader=new BufferedReader(new FileReader(file));这样在web运行的时候，读取会乱码
            reader=new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            while((temp=reader.readLine())!=null){
                //System.out.println("line"+line+":"+temp);
                keyWordSet.add(temp);
                //line++;
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(reader!=null){
                try{
                    reader.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }


    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings({ "rawtypes"})
    public static int checkBadWord(String txt,int beginIndex,int matchType){
        boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = wordMap;
        for(int i = beginIndex; i < txt.length() ; i++){
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if(nowMap != null){     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if(minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            }
            else{     //不存在，直接返回
                break;
            }
        }
        /*“粉饰”匹配词库：“粉饰太平”竟然说是敏感词
         * “个人”匹配词库：“个人崇拜”竟然说是敏感词
         * if(matchFlag < 2 && !flag){
            matchFlag = 0;
        }*/
        if(!flag){
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 判断文字是否包含敏感字符
     * @param txt  文字
     * @param matchType  匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public static boolean isContaintBadWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0 ; i < txt.length() ; i++){
            int matchFlag = checkBadWord(txt, i, matchType); //判断是否包含敏感字符
            if(matchFlag > 0){    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 替换敏感字字符
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public static String replaceBadWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getBadWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }
    /**
     * 获取文字中的敏感词
     * @param txt 文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public static Set<String> getBadWord(String txt , int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();

        for(int i = 0 ; i < txt.length() ; i++){
            int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
            if(length > 0){    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i+length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 获取替换字符串
     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private static String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1 ; i < length ; i++){
            resultReplace += replaceChar;
        }

        return resultReplace;
    }

    /**
     * TODO 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
     * @param keyWordSet 敏感词库
     * @author yqwang0907
     * @date 2018年2月28日下午5:28:08
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static void addBadWordToHashMap(Set<String> keyWordSet) {
        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = wordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }


    /**
     * @author 杨胜彬
     * @comments main测试敏感词
     * @time 2018年7月19日 下午5:41:40
     * @param args
     * @returnType void
     * @modification
     */
    /*public static void main(String[] args) {
        Set<String> s = SensitiveWordTest.words;
        Map<String,String> map = SensitiveWordTest.wordMap;

        System.out.println("敏感词的数量：" + SensitiveWordTest.wordMap.size());
        String string = "太多的伤yuming感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
                + "然后法轮功 我们的扮演的角色就是跟随着主人yum公的喜红客联盟 怒于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
                + "然后法轮功 我们的扮演的角色就是跟随着主人yum公的喜红客联盟 怒哀20于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
                + "然后法轮功 我们的扮演的角色就是跟随着主人yum公的喜红客联盟 怒哀20哀2015/4/16 20152015/4/16乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
                + "关, 人, 流, 电, 发, 情, 太, 限, 法轮功, 个人, 经, 色, 许, 公, 动, 地, 方, 基, 在, 上, 红, 强, 自杀指南, 制, 卡, 三级片, 一, 夜, 多, 手机, 于, 自，"
                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。自由门撒UI的嘎斯";
        System.out.println("待检测语句字数：" + string.length());
        long beginTime = System.currentTimeMillis();
        Set<String> set = SensitiveWordTest.getBadWord(string, 2);
        Boolean i = SensitiveWordTest.isContaintBadWord(string, 2);
        String str = SensitiveWordTest.replaceBadWord(string,2,"*");
        long endTime = System.currentTimeMillis();
        System.out.println("i:"+i);
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
        System.out.println("总共消耗时间为：" + (endTime - beginTime));
        System.out.println("str：" +str);
    }*/

}