CREATE TABLE `t_cnt` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `time` timestamp NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
