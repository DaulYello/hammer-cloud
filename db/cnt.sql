
DROP TABLE IF EXISTS `fm_cnt_info`;

CREATE TABLE `fm_cnt_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `cnt_num` double(18,10) DEFAULT NULL COMMENT '产生CNT数量',
  `status` tinyint(4) DEFAULT '0' COMMENT 'CNT状态0,未收取;1已收取;2被系统回收',
  `create_date` timestamp NULL DEFAULT NULL COMMENT 'CNT产生时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=179 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `fm_cnt_pool`;

CREATE TABLE `fm_cnt_pool` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cnt_pool` double(18,10) DEFAULT '0.0000000000' COMMENT 'cnt释放资金池、由定时任务每天按收益比例注入',
  `cnt_recyle` double(18,10) DEFAULT '0.0000000000' COMMENT '当天没用户收取回收了多少,真正回收的值需要入公司账户',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '生成时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '回收时间',
  `year` int(4) DEFAULT NULL COMMENT '年度、方便统计',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `fm_integral_info`;

CREATE TABLE `fm_integral_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `integral_num` double(18,10) DEFAULT NULL COMMENT '每个阶段生成的R积分数量',
  `status` tinyint(4) DEFAULT '0' COMMENT '0未收取状态; 1自己已收取; 2系统回收;-1被好友偷走',
  `create_date` timestamp NULL DEFAULT NULL COMMENT 'R积分生成时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=444 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `fm_recyle_log`;

CREATE TABLE `fm_recyle_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `friend_id` int(11) DEFAULT NULL COMMENT '偷取的用户ID',
  `take_num` double(18,10) DEFAULT NULL COMMENT '收取\\回收CNT或R积分数量',
  `take_date` timestamp NULL DEFAULT NULL COMMENT '收取\\回收时间',
  `take_type` tinyint(4) DEFAULT NULL COMMENT '收取类型:0、用户收取;1、定时任务回收;2、释放CNT时没有用户回收',
  `recyle_type` tinyint(4) DEFAULT NULL COMMENT '所属类型1、CNT; 2、R积分',
  `take_msg` varchar(500) DEFAULT NULL COMMENT '详细描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `fm_rpool`;

CREATE TABLE `fm_rpool` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` int(4) DEFAULT NULL COMMENT '投放年度',
  `rintegral_num` double(18,10) DEFAULT NULL COMMENT '年度R积分投放池',
  `recycle_num` double(18,10) DEFAULT '0.0000000000' COMMENT '年度R积分回收池',
  `createDate` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updateDate` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `fm_release_rule`;

CREATE TABLE `fm_release_rule` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `rpool_id` int(11) DEFAULT NULL COMMENT 'R积分池',
  `fy_min` double(18,10) DEFAULT NULL COMMENT '飞羽区间最小值',
  `fy_max` double(18,10) DEFAULT NULL COMMENT '飞羽区间最大值',
  `weight` double(10,2) DEFAULT NULL COMMENT '权重',
  `allot_percent` double(10,2) DEFAULT NULL COMMENT '分配比例',
  `dilut_percent` double(10,2) DEFAULT NULL COMMENT '稀释比例',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  `allot_desc` varchar(300) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '总分配描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*created by huangshuang on 2018-10-25*/
CREATE TABLE `fm_assets_poundage` (
  `id` INT (11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `aid` INT (11) COMMENT '活动id',
  `total_assets` DOUBLE (18, 2) COMMENT '活动溢价后的总资产',
  `poundage` DOUBLE (18, 2) COMMENT '手续费',
  `release_rate` DOUBLE (18, 2) COMMENT '释放比例',
  `create_date` DATE COMMENT '数据记录时间',
  `update_date` DATE COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE = INNODB CHARSET = utf8 COLLATE = utf8_general_ci;