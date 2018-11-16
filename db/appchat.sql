DROP TABLE IF EXISTS `fm_news`;

CREATE TABLE `fm_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) DEFAULT NULL COMMENT '消息模块名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '1、活动;2、竞锤;2、聊天; 4、c2c交易; 5、好友',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `fm_news` */

insert  into `fm_news`(`id`,`name`,`type`,`remark`,`create_date`) values
(1,'活动通知',1,'活动通知','2018-11-14 16:23:04'),
(2,'活动',2,'竞锤通知','2018-11-14 16:24:06'),
(3,'聊天通知',3,'聊天通知','2018-11-14 16:24:58'),
(4,'订单',4,'交易通知','2018-11-14 16:24:56'),
(5,'好友通知',5,'好友通知','2018-11-14 16:24:54');


DROP TABLE IF EXISTS `hc_appchat`;

CREATE TABLE `hc_appchat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `send_id` int(11) DEFAULT NULL COMMENT '发送消息用户ID',
  `accept_id` int(11) DEFAULT NULL COMMENT '接受消息用户ID',
  `text` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `each_label` varchar(100) DEFAULT NULL COMMENT '相互聊天标识',
  `status` tinyint(4) DEFAULT '0' COMMENT '0,未读; 1,已读',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '消息产生时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;