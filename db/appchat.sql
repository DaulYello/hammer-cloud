
DROP TABLE IF EXISTS `hc_appchat`;

CREATE TABLE `hc_appchat` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `send_id` int(11) DEFAULT NULL COMMENT '发送消息用户ID',
  `accept_id` int(11) DEFAULT NULL COMMENT '接受消息用户ID',
  `text` varchar(1000) DEFAULT NULL COMMENT '消息内容',
  `status` tinyint(4) DEFAULT '0' COMMENT '0,未读; 1,已读',
  `create_date` timestamp NULL DEFAULT NULL COMMENT '消息产生时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;