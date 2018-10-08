CREATE TABLE `t_center` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `grow` int(11) DEFAULT NULL COMMENT '成长值',
  `checkin` varchar(45) DEFAULT NULL COMMENT '签到',
  `type` varchar(45) DEFAULT NULL COMMENT '类型',
  `intruduction` varchar(450) DEFAULT NULL COMMENT '介绍',
  `cnt` int(11) DEFAULT NULL COMMENT 'CNT',
  `strategy` blob COMMENT '攻略',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
