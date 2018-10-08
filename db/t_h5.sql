CREATE TABLE `t_h5` (
  `id` int(11) NOT NULL,
  `location` varchar(45) DEFAULT NULL COMMENT '位置',
  `type` varchar(45) DEFAULT NULL COMMENT '类型',
  `content` varchar(45) DEFAULT NULL COMMENT '内容',
  `group` varchar(45) DEFAULT NULL COMMENT '分组',
  `time` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
