package com.fmkj.user.server.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.fmkj.common.base.BaseApiService;
import com.fmkj.common.base.BaseController;
import com.fmkj.common.base.BaseResult;
import com.fmkj.common.base.BaseResultEnum;
import com.fmkj.common.comenum.PointEnum;
import com.fmkj.common.constant.LogConstant;
import com.fmkj.common.util.*;
import com.fmkj.user.dao.domain.*;
import com.fmkj.user.dao.dto.HcAccountDto;
import com.fmkj.user.dao.dto.Recode;
import com.fmkj.user.server.annotation.UserLog;
import com.fmkj.user.server.async.AsyncFactory;
import com.fmkj.user.server.async.AsyncManager;
import com.fmkj.user.server.service.*;
import com.fmkj.user.server.util.ALiSmsUtil;
import com.fmkj.user.server.util.CalendarTime;
import com.fmkj.user.server.util.JDWXUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/hcAccount")
@DependsOn("springContextUtil")
@Api(tags ={ "用户信息"},description = "用户信息接口-网关路径/api-user")
public class HcAccountController extends BaseController<HcAccount, HcAccountService> implements BaseApiService<HcAccount> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HcAccountController.class);

    //用户表接口
    @Autowired
    private HcAccountService hcAccountService;

    @Autowired
    private HcSessionService hcSessionService;

    @Autowired
    private HcPointsRecordService hcPointsRecordService;

    @Autowired
    private HcRcodeService hcRcodeService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JDWXUtil jdwxUtil;

    private static HashMap<String, String> codeMap = new HashMap<String, String>();

    @Value("${userHeadImageIpPath}")
    private String userHeadImageIpPath;

    @Value("${userHeadImagePath}")
    private String userHeadImagePath;


    @ApiOperation(value="用户通过电话号码和密码进行登录", notes="参数：telephone, password")
    @PostMapping(value = "/loginByPassword")
    public BaseResult<Map<String,Object>> loginByPassword(@RequestBody HcAccount hcAccount){
        String pwd = hcAccount.getPassword();
        String telephone = hcAccount.getTelephone();
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(telephone)) {
            return new BaseResult(BaseResultEnum.BLANK.status,"用户名或密码为空!",false);
        }
        pwd = DigestUtils.md5Hex(pwd);
        //判断用户电话号码和密码是否匹配
        HcAccount hca = new HcAccount();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("telephone",telephone);
        map.put("password",pwd);
        List<HcAccount> account = hcAccountService.selectByMap(map);
        if (account.size()<=0 || StringUtils.isNull(account)) {
            return new BaseResult(BaseResultEnum.ERROR.status,"用户名或密码错误!",false);
        }

        //创建token及生存时间
        String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        CalendarTime clt = new CalendarTime();
        Timestamp btime = clt.thisDate();//获取当前时间
        Timestamp etime = clt.dateStartEed(30);// 获取30天后的时间
        Timestamp ltime = clt.thisDate();//当前时间

        Integer uid = account.get(0).getId();
        //创建hcsession对象存放session值
        HcSession hcSession = new HcSession();
        hcSession.setToken(token);
        hcSession.setBtime(btime);
        hcSession.setEtime(etime);
        hcSession.setLtime(ltime);

        // 更新hc_session表，
        HcSession session = new HcSession();
        session.setUid(uid);
        EntityWrapper<HcSession> wrapper = new EntityWrapper<HcSession>();
        wrapper.setEntity(session);
        int row = hcSessionService.selectCount(wrapper); //根据用户id查询是否存在记录
        boolean isupdate = false;
        if (row > 0) {
            EntityWrapper<HcSession> wra = new EntityWrapper<HcSession>();
            wra.setEntity(session);
            isupdate = hcSessionService.update(hcSession, wra);  //update第一个参数为修改的参数,第二个为条件
            if (!isupdate) {
                LOGGER.error("error:hc_session表更新失败或用户状态更改失败");
                return new BaseResult(BaseResultEnum.ERROR.status,"系统错误，请重新登录!",null);
            }
        }else {
            hcSession.setUid(uid);
            boolean result = hcSessionService.insert(hcSession);
            if(!result) {
                LOGGER.error("error:hc_session表更新失败或用户状态更改失败");
                return new BaseResult(BaseResultEnum.ERROR.status,"系统错误，请重新登录!",null);
            }
        }

        // 登录成功把用户的登录状态字段改为authlock=1
        account.get(0).setAuthlock(true);
        boolean flg = hcAccountService.updateBatchById(account);
        if (!flg) {
            LOGGER.error("error:hc_session表更新失败或用户状态更改失败");
            return new BaseResult(BaseResultEnum.ERROR.status,"系统错误，请重新登录!",null);
        }

        map.put("token", token);
        map.put("uid", account.get(0).getId()+"");
        return new BaseResult(BaseResultEnum.SUCCESS.status,"用户名密码正确，登录成功!",map);
    }




    @ApiOperation(value="查询最新一条中奖用户信息", notes="查询最新一条中奖用户信息")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "查询最新一条中奖用户信息")
    @PutMapping("/queryOneNewNotice")
    public BaseResult queryOneNewNotice(){
        try {
            List<Map<String,Object>> map = hcAccountService.queryOneNewNotice();
            return new BaseResult(BaseResultEnum.SUCCESS,map);
        } catch (Exception e) {
            throw new RuntimeException("查询最新一条中奖用户信息异常：" + e.getMessage());
        }
    }


    //更改用户p能量
    @ApiOperation(value = "更改用户CNT", notes = "更改用户CNT--竞锤调用")
    @UserLog(module = LogConstant.HC_ACCOUNT, actionDesc = "更改用户cnt")
    @PostMapping("/updateUserP")
    public Boolean updateUserP(@RequestBody HcAccount hc) {
        double par = hc.getCnt();
        HcAccount account = hcAccountService.selectById(hc.getId());
       if (Double.doubleToLongBits(account.getCnt()) < Double.doubleToLongBits(par)) {
            return false;
        }
        double newCnt = account.getCnt() - par;//用户新的CNT
        account.setCnt(newCnt);
        boolean result = false;
        try {
            result = hcAccountService.updateUserP(account, par);
        } catch (Exception e) {
            throw new RuntimeException("更改用户CNT异常" + e.getMessage());
        }
        return result;
    }


    @ApiOperation(value="活动添加飞羽", notes="活动添加飞羽")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "活动添加飞羽")
    @PostMapping("/addHcPointsRecord")
    public Boolean addHcPointsRecord(@RequestBody HcPointsRecord hc) {
        LOGGER.info("活动添加飞羽参数:" + JSON.toJSONString(hc));
        if(StringUtils.isNull(hc)){
            return false;
        }
        return hcPointsRecordService.insert(hc);
    }

    @ApiOperation(value="发放CNT", notes="确认收货后，将资产对应的CNT给发起活动的用户")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "确认收货后，将资产对应的CNT给发起活动的用户")
    @PostMapping("/grantUserP")
    public Boolean grantUserP(@RequestBody HcAccount hc) {
        Double starterCnt = hc.getCnt();
        HcAccount account = hcAccountService.selectById(hc.getId());
        Double tocalCnt = null;
        if (account != null) {
            Double cnt = account.getCnt();
            tocalCnt = cnt + starterCnt;
            account.setCnt(tocalCnt);
            return hcAccountService.grantUserP(account, starterCnt);
        }
        return false;
    }

    @ApiOperation(value="根据ID获取用户-App调用", notes="参数： id")
    @PutMapping("/selectAccountById")
    public BaseResult selectAccountById(@RequestBody HcAccount hcAccount){
        if(StringUtils.isNull(hcAccount.getId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空", false);
        }
        HcAccountDto hcAccountDto = hcAccountService.selectAccountById(hcAccount.getId());
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", hcAccountDto);

    }

    @ApiOperation(value="根据ID获取用户-竞锤服务调用", notes="根据ID获取用户")
    @GetMapping("/getAccountById")
    public HcAccount getAccountById(Integer id){
        LOGGER.info("根据ID获取用户参数：" + id);
        HcAccount hc = hcAccountService.selectById(id);
        return hc;

    }

    @ApiOperation(value="用户验证码信息发送", notes="参数：telephone")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "用户验证码信息发送")
    @PostMapping("/sendDycode")
    public BaseResult sendDycode(@RequestBody HcAccount hcAccount) {
        String telephone = hcAccount.getTelephone();
        if(StringUtils.isEmpty(telephone)){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "电话号码不能为空", false);
        }
        // 发送短信验证码
        new Thread() {
            public void run() {
                try {
                    ALiSmsUtil.sendSMS(telephone,codeMap);
                } catch (Exception e) {
                    e.getMessage();
                }
            };
        }.start();
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "验证码发送成功!", true);
    }


    @ApiOperation(value="用户通过电话号码和短信动态码进行登录", notes="参数：telephone，dycode")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "用户通过电话号码和短信动态码进行登录")
    @PostMapping("/loginByTelephone")
    public BaseResult loginByTelephone(@RequestBody HcAccount ha) {
        String telephone = ha.getTelephone();
        if (StringUtils.isEmpty(telephone)) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "电话号码不能为空！", false);
        }
        String dycode = ha.getDycode();
        if (StringUtils.isEmpty(dycode)) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "验证码不能为空！", false);
        }
        String code = codeMap.get(telephone + "code");
        if (StringUtils.isEmpty(code) || !code.equals(ha.getDycode())) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "验证码错误！", false);
        }
        String codetime = codeMap.get(telephone + "time");
        long time = (new Date().getTime() - Long.parseLong(codetime)) / 1000;
        if (time >= 90) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "验证码已过期，请重新获取！", false);
        }
        // 获取该电话号码的用户
        HashMap<String, Object> params = new HashMap<>();
        params.put("telephone", telephone);
        List<HcAccount> list = hcAccountService.selectByMap(params);
        if (StringUtils.isEmpty(list)) {
            return new BaseResult(BaseResultEnum.LOGIN_STATUS.getStatus(), "验证码正确，用户首次登陆，需要邀请码登陆！", true);
        }
        HcAccount account = list.get(0);
        String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        boolean result = hcAccountService.loginByTelephone(account.getId(), token);
        if (!result) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "系统错误，请重新登录!登陆ID：" + account.getId(), false);
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("telephone",telephone);
        resultMap.put("token", token);
        resultMap.put("uid", account.getId() + "");
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "登录成功!", resultMap);
    }



    /**
     * 获取用户填写的邀请码并注册登录,需传入手机号
     */
    @ApiOperation(value="用户通过邀请码登录", notes="参数：telephone， rcode")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "用户通过电话号码和短信动态码进行登录")
    @PostMapping("/loginByRcodeAndPhone")
    public BaseResult loginByRcodeAndPhone(@RequestBody Recode recode) {
        if (StringUtils.isEmpty(recode.getTelephone())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "电话号码不能为空！", false);
        }
        if (StringUtils.isEmpty(recode.getRcode())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "验证码不能为空！", false);
        }
        HcRcode hcRcode = new HcRcode();
        hcRcode.setCode(recode.getRcode());
        EntityWrapper<HcRcode> wrapper = new EntityWrapper<>(hcRcode);
        HcRcode hc = hcRcodeService.selectOne(wrapper);
        if (StringUtils.isNull(hc)) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "该邀请码比对失败,返回登录界面!", false);
        }

        HcAccount ha = new HcAccount();
        ha.setTelephone(recode.getTelephone());
        EntityWrapper<HcAccount> entityWrapper = new EntityWrapper<>(ha);
        HcAccount hcAccount = hcAccountService.selectOne(entityWrapper);
        if (StringUtils.isNotNull(hcAccount)) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "用户已存在!", false);
        }

        int count = hcAccountService.selectCount(new EntityWrapper<>(new HcAccount()));
        if (count <= 2009) {
            ha.setIsboong(1);
        }else {
            ha.setIsboong(0);
        }
        // 插入用户表
        ha.setAuthlock(true);
        ha.setTelephone(recode.getTelephone());
        ha.setRid(hc.getUid());
        ha.setGradeId(1);
        Long cdbid = createRandom();
        ha.setCdbid(cdbid);
        ha.setNickname(cdbid + "");
        String token = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        int result =  hcAccountService.loginByRcodeAndPhone(ha, hc.getUid(), token);
        if (result > 0){
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("telephone",recode.getTelephone());
            resultMap.put("token", token);
            resultMap.put("uid", result + "");
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "邀请码比对成功,用户注册并登录!", resultMap);
        }
        return new BaseResult(BaseResultEnum.ERROR.getStatus(), "用户注册登陆失败!", false);
    }


    /**
     * 通过传入的用户字段和用户唯一id修改用户信息
     */
    @ApiOperation(value="修改用户信息-昵称-设置密码-姓名", notes="参数：id， 修改字段，比如nickname")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "传入id及需要修改的字段")
    @PostMapping("/updateAccoutById")
    public BaseResult updateAccoutById(@RequestBody HcAccount ha) {
        if (StringUtils.isNull(ha) || StringUtils.isNull(ha.getId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空", false);
        }
        String nickname = ha.getNickname();
        if (StringUtils.isNotEmpty(nickname)) {
            String wordIsOk= SensitiveWordUtil.replaceBadWord(nickname.trim(),2,"*");
            if(!nickname.equals(wordIsOk.trim())) {
                return new BaseResult(BaseResultEnum.ERROR.getStatus(), "昵称含有敏感词汇，请重新写一个吧!", false);
            }
        }
        if(StringUtils.isNotEmpty(ha.getPassword())){
            ha.setPassword(DigestUtils.md5Hex(ha.getPassword()));
        }

        hcAccountService.updateById(ha);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "修改成功!", true);
    }

     /**
     * 用户头像上传
     *
     * @throws IOException
     */
    @ApiOperation(value="用户头像上传", notes="参数：id")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "用户头像上传")
    @PostMapping("/uploadUserHead")
    public BaseResult uploadUserHead(@PathParam(value = "id") Integer id,
                                     @RequestParam("file") MultipartFile file){
        try {
            if(StringUtils.isNull(id)){
                return new BaseResult(BaseResultEnum.BLANK.status, "用户ID不能为空!", false);
            }
            String fileName =PropertiesUtil.uploadImage(file,userHeadImagePath);
            HcAccount hcAccount = new HcAccount();
            hcAccount.setId(id);
            hcAccount.setLogo(userHeadImageIpPath + fileName);
            boolean result = hcAccountService.uploadUserHead(hcAccount, fileName, userHeadImagePath);
            if(result){
                HashMap<String, Object> resultMap = new HashMap<>();
                resultMap.put("url", userHeadImageIpPath + fileName);
                return new BaseResult(BaseResultEnum.SUCCESS.status, "头像上传成功!", resultMap);
            }
            return new BaseResult(BaseResultEnum.ERROR.status, "头像上传失败!", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BaseResult(BaseResultEnum.ERROR.status, "头像上传失败!", false);
    }


    /**
     * 这种方式实现在线预览文件
     * @param id
     * @return
     * @throws FileNotFoundException
     */
    @GetMapping(value = "/showUserHead", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity showUserHead(@RequestParam Integer id) throws FileNotFoundException {
        HcAccount hc = hcAccountService.selectById(id);
        String logo = hc.getLogo();
        InputStream in = new FileInputStream(new File(userHeadImagePath + logo));
        InputStreamResource resource = new InputStreamResource(in);
        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity(resource, httpHeaders, HttpStatus.OK);
    }



    /**
     * 用户身份证号码与姓名验证
     */
    @ApiOperation(value="用户身份证号码与姓名验证", notes="参数：id, cardnum， name")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "用户身份证号码与姓名验证")
    @PostMapping("/queryCarIdisOk")
    public BaseResult queryCarIdisOk(@RequestBody HcAccount ha) {
        if(StringUtils.isNull(ha) || StringUtils.isNull(ha.getId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "ID不能为空!", false);
        }
        if(StringUtils.isNull(ha.getCardnum())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "身份证号码不能为空!", false);
        }
        if(StringUtils.isNull(ha.getName())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "姓名不能为空!", false);
        }
        boolean result = jdwxUtil.cardRealName(ha);
        if(!result) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "身份认证失败!", false);
        }
        boolean updateUser = hcAccountService.updateById(ha);
        //ha.setCardStatus(1);
        if (!updateUser) {
            return new BaseResult(BaseResultEnum.ERROR.getStatus(), "系统错误，请重试!", false);
        }
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "身份认证成功!", true);
    }


    /**
     * 用户签到
     *
     * @param
     */
    @ApiOperation(value="用户签到", notes="参数：id")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "用户签到、成功签到、用户得到1飞羽（成长值）")
    @PostMapping("/signIn")
    public BaseResult signIn(@RequestBody HcAccount hcAccount) {
        if(StringUtils.isNull(hcAccount) || StringUtils.isNull(hcAccount.getId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        HcPointsRecord hcPointsRecord =  hcPointsRecordService.getHcPointsRecord(hcAccount.getId());
        if (StringUtils.isNull(hcPointsRecord)) {
            // 如果不存在今天签到的记录，那么就可以签到，插入签到记录
            HcPointsRecord record = new HcPointsRecord();
            record.setPointsId(PointEnum.SIGN_IN.pointId);
            record.setPointsNum(PointEnum.SIGN_IN.pointNum);
            record.setUid(hcAccount.getId());
            record.setTime(DateUtil.getNowInMillis(0L));
            hcPointsRecordService.insert(record);
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "签到成功!", true);
        }
        // 今天已经签到过了，不能在签到
        return new BaseResult(BaseResultEnum.BLANK.getStatus(), "您今天已经签到过了!", false);
    }

    /**
     * 查询邀请好友（推广）周排行榜
     *
     * @return
     */
    @ApiOperation(value="查询邀请好友（推广）周排行榜", notes="参数：id")
    @PutMapping("/queryRankInWeek")
    public BaseResult queryRankInWeek(@RequestBody HcAccount hc) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if(StringUtils.isNull(hc) || StringUtils.isNull(hc.getId())){
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        // 查询邀请码
        HcRcode hcRcode = new HcRcode();
        hcRcode.setUid(hc.getId());
        EntityWrapper<HcRcode> wrapper = new EntityWrapper<>(hcRcode);
        HcRcode rcode = hcRcodeService.selectOne(wrapper);
        if (StringUtils.isNull(rcode)) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户没有邀请码!", false);
        }
        // 查询邀请人来注册获得的总积分
        Integer pointNum = hcPointsRecordService.queryPoints(hc.getId());
        if (pointNum == null) {
            pointNum = 0;
        }

        // 查询邀请人来注册获得的总能量
        HcAccount hcAccount = new HcAccount();
        hcAccount.setRid(hc.getId());
        EntityWrapper<HcAccount> hcWrapper = new EntityWrapper<>(hcAccount);
        List<HcAccount> queryUserByExample = hcAccountService.selectList(hcWrapper);

        // 每邀请到一个人得到1P，getPs就是获得的总能量
        Integer getAllP = queryUserByExample.size() * 1;
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("points", pointNum);
        hashMap.put("getAllP", getAllP);
        hashMap.put("getAllUser", queryUserByExample.size());
        hashMap.put("reode", rcode);

        int accountNum = hcAccountService.selectCount(new EntityWrapper<>());
        // 查询用户的排名(邀请人数大于0)
        List<BaseBean> rankWeek = hcPointsRecordService.queryInvitingFriendsRankWeek(0,accountNum);
        if (StringUtils.isEmpty(rankWeek)) {
            hashMap.put("position", "你本周未邀请人");
        }
        for (int i = 0; i < rankWeek.size(); i++) {
            BaseBean BaseBean = rankWeek.get(i);
            if (BaseBean.getUid() == hc.getId()) {
                hashMap.put("position", i + 1);
                break;
            }
            if (i == rankWeek.size() - 1) {
                hashMap.put("position", "你本周未邀请人");
            }
        }
        // ·榜
        List<BaseBean> friendsRankWeek = hcPointsRecordService.queryInvitingFriendsRankWeek(0,10);
        result.put("rank", friendsRankWeek);
        result.put("myData", hashMap);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功", result);
    }

    /**
     * 邮箱绑定,传入用户id及用户邮箱
     */
    @ApiOperation(value="邮箱绑定,传入用户id及用户邮箱", notes="参数：id, email")
    @UserLog(module= LogConstant.HC_ACCOUNT, actionDesc = "邮箱绑定")
    @PostMapping("/bindEmail")
    public BaseResult bindEmail(@RequestBody HcAccount ha) {
        if (StringUtils.isNull(ha.getId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        String email = ha.getEmail();
        if (StringUtils.isEmpty(email)) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户传入邮箱信息错误!", false);
        }
        //绑定邮箱给予积分奖励
        boolean isUpdate = hcAccountService.bindEmail(ha);
        if(isUpdate){
            AsyncManager.me().execute(AsyncFactory.sendEmail(email));
            return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "邮箱绑定成功!", true);
        }
        return new BaseResult(BaseResultEnum.ERROR.getStatus(), "邮箱绑定失败!", true);
    }

    /**
     * 首次进入任务中心查询接口
     */
    @ApiOperation(value="首次进入任务中心查询接口", notes="参数：id")
    @PutMapping("/queryUserTaskMessage")
    public BaseResult queryUserTaskMessage(@RequestBody HcAccount hcAccount) {
        HashMap<String, Object> result = new HashMap<>();
        Integer uid = hcAccount.getId();
        if (StringUtils.isNull(uid)) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        HcAccount user = hcAccountService.queryUserTaskMessage(uid);
        //查询用户是否签到过
        HcPointsRecord hcPointsRecord = hcPointsRecordService.getHcPointsRecord(uid);
        if(StringUtils.isNull(hcPointsRecord)) {
            result.put("singnStatus", "0");
        }else {
            result.put("singnStatus", "1");
        }
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", result);
    }

    /**
     * 查询用户的总积分（成长值）
     *
     * @return
     */
    @ApiOperation(value="查询用户的总积分（成长值）", notes="参数：id")
    @PutMapping("/queryUserScoresByUid")
    public  BaseResult queryUserScoresByUid(@RequestBody HcAccount hcAccount) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.isNull(hcAccount.getId())) {
            return new BaseResult(BaseResultEnum.BLANK.getStatus(), "用户ID不能为空!", false);
        }
        int pointsNum = hcPointsRecordService.queryUserScoresByUid(hcAccount.getId());
        result.put("scores", pointsNum);
        return new BaseResult(BaseResultEnum.SUCCESS.getStatus(), "查询成功!", result);
    }

    private Long createRandom() {
        Long cdbid = (long) ((Math.random()*1)*1000000000);
        HcAccount hcAccount = new HcAccount();
        hcAccount.setCdbid(cdbid);
        EntityWrapper<HcAccount> wrapper = new EntityWrapper<>(hcAccount);
        List<HcAccount> list = hcAccountService.selectList(wrapper);
        if(list.size()>0) {
            createRandom();
        }
        return cdbid;
    }


}
