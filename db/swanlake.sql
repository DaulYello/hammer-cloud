--天鹅湖表结构

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;


insert  into `fm_release_rule`(`id`,`rpool_id`,`fy_min`,`fy_max`,`weight`,`allot_percent`,`dilut_percent`,`create_date`,`update_date`,`allot_desc`) values
(1,1,0.0000000000,1000.0000000000,20.00,60.00,40.00,'2018-10-10 16:44:35','2018-10-10 16:44:38','100*20%*60%'),
(2,1,1000.0000000000,3000.0000000000,10.00,70.00,30.00,'2018-10-10 16:45:40','2018-10-10 16:45:43','100*10%*70%+100*20%*40%*25%'),
(3,1,3000.0000000000,6000.0000000000,20.00,80.00,20.00,'2018-10-10 16:47:28','2018-10-10 16:47:30','100*20%*80%'),
(4,1,6000.0000000000,10000.0000000000,30.00,90.00,10.00,'2018-10-10 16:48:13','2018-10-10 16:48:16','100*30%*90%'),
(5,1,10000.0000000000,9999999.0000000000,20.00,100.00,0.00,'2018-10-10 16:49:08','2018-10-10 16:49:11','100*20%');


DROP TABLE IF EXISTS `fm_rpool`;

CREATE TABLE `fm_rpool` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` int(4) DEFAULT NULL COMMENT '投放年度',
  `rintegral_num` double(18,10) DEFAULT NULL COMMENT '年度R积分投放池',
  `recycle_num` double(18,10) DEFAULT NULL COMMENT '年度R积分回收池',
  `createDate` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `updateDate` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


insert  into `fm_rpool`(`id`,`year`,`rintegral_num`,`recycle_num`,`createDate`,`updateDate`) values
(1,2018,10000.0000000000,NULL,'2018-10-10 16:40:42','2018-10-10 16:40:44');


