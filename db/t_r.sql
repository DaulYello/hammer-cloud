CREATE TABLE `t_r` (
  `id` int(11) NOT NULL,
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `time` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
