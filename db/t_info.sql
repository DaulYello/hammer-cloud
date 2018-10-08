CREATE TABLE `t_info` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `event` varchar(45) DEFAULT NULL COMMENT '事件',
  `location` varchar(45) DEFAULT NULL COMMENT '地点',
  `time` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
