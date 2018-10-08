CREATE TABLE `t_access` (
  `id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `headimg` varchar(45) DEFAULT NULL COMMENT '访问头像',
  `date` timestamp NULL DEFAULT NULL COMMENT '日期时间',
  `time` int(11) DEFAULT NULL COMMENT '次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
