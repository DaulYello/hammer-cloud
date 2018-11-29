
DROP TABLE IF EXISTS `pm_extend`;

CREATE TABLE `pm_extend` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tid` int(11) DEFAULT NULL COMMENT '任务ID',
  `clounm_key` varchar(20) DEFAULT NULL COMMENT '列名',
  `clounm_name` varchar(50) DEFAULT NULL COMMENT '列名名称',
  `clounm_tip` varchar(50) DEFAULT NULL COMMENT '提示信息',
  `is_empty` tinyint(4) DEFAULT '0' COMMENT '是否为空0、可为空, 1、必填',
  `empty_hint` varchar(50) DEFAULT NULL COMMENT '为空提示',
  `order_num` tinyint(4) DEFAULT NULL COMMENT '显示顺序',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `pm_extend_value` */

DROP TABLE IF EXISTS `pm_extend_value`;

CREATE TABLE `pm_extend_value` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` int(11) DEFAULT NULL COMMENT '参与记录ID',
  `xid` int(11) DEFAULT NULL COMMENT '任务扩展ID',
  `xvalue` varchar(200) DEFAULT NULL COMMENT '扩展字段的值',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

/*Table structure for table `pm_image` */

DROP TABLE IF EXISTS `pm_image`;

CREATE TABLE `pm_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `path` varchar(100) DEFAULT NULL COMMENT '保存地址',
  `image_url` varchar(200) DEFAULT NULL COMMENT '图片地址',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '上传时间',
  `part_id` int(11) DEFAULT NULL COMMENT '参与记录图片',
  `image_type` tinyint(4) DEFAULT NULL COMMENT '图片类型1、参与记录; 2、攻略图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

/*Table structure for table `pm_part` */

DROP TABLE IF EXISTS `pm_part`;

CREATE TABLE `pm_part` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` int(11) DEFAULT NULL COMMENT '参与用户',
  `tid` int(11) DEFAULT NULL COMMENT '任务id',
  `audit_status` tinyint(4) DEFAULT '0' COMMENT '审核状态0,未审核;1、待审核; 2、审核通过;-1、驳回',
  `audit_option` varchar(200) DEFAULT NULL COMMENT '审核意见',
  `telephone` varchar(20) DEFAULT NULL COMMENT '提交电话',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '参与时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

/*Table structure for table `pm_prompt` */

DROP TABLE IF EXISTS `pm_prompt`;

CREATE TABLE `pm_prompt` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tid` int(11) DEFAULT NULL COMMENT '任务ID',
  `prompt_text` varchar(200) DEFAULT NULL COMMENT '内容',
  `order_num` tinyint(4) DEFAULT NULL COMMENT '显示顺序',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `pm_strategy` */

DROP TABLE IF EXISTS `pm_strategy`;

CREATE TABLE `pm_strategy` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tid` int(11) DEFAULT NULL COMMENT '任务ID',
  `strategy` varchar(500) DEFAULT NULL COMMENT '攻略',
  `order_num` tinyint(4) DEFAULT NULL COMMENT '顺序',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Table structure for table `pm_task` */

DROP TABLE IF EXISTS `pm_task`;

CREATE TABLE `pm_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(100) DEFAULT NULL COMMENT '任务标题',
  `task_target` varchar(200) DEFAULT NULL COMMENT '任务目标',
  `sub_desc` varchar(200) DEFAULT NULL COMMENT '二级描述',
  `reward` double(18,2) DEFAULT NULL COMMENT '任务奖励',
  `audit_cycle` varchar(30) DEFAULT NULL COMMENT '审核周期',
  `type` tinyint(4) DEFAULT '0' COMMENT '是否需要下载APP:0,不需要;1、需要',
  `down_url` varchar(200) DEFAULT NULL COMMENT '打开地址',
  `start_date` timestamp NULL DEFAULT NULL COMMENT '任务开始时间',
  `end_date` timestamp NULL DEFAULT NULL COMMENT '任务结束时间',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` timestamp NULL DEFAULT NULL COMMENT '任务更新时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态0、正常; 1、已发布;-1、已删除',
  `logoId` int(11) DEFAULT NULL COMMENT '任务标题图片',
  `imageId` int(11) DEFAULT NULL COMMENT '详情图片',
  `proportion` int(11) DEFAULT NULL COMMENT '任务奖励分给用户的占比',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

