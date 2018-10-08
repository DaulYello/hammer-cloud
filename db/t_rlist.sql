CREATE TABLE `t_rlist` (
  `id` int(11) NOT NULL,
  `no` int(11) DEFAULT NULL COMMENT '排名',
  `headimage` varchar(45) DEFAULT NULL COMMENT '头像',
  `nickname` varchar(45) DEFAULT NULL COMMENT '昵称',
  `r` int(11) DEFAULT NULL COMMENT 'R积分',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
