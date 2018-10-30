
DROP TABLE IF EXISTS `gc_activitytype`;

CREATE TABLE `gc_activitytype` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '活动的类型',
  `days` int(11) DEFAULT NULL COMMENT '持续天数',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动（竟锤）类型表';


ALTER TABLE `gc_address`
ADD COLUMN `lock` int(1) DEFAULT '0' COMMENT '初始为0,中奖用户不可修改地址';



DROP TABLE IF EXISTS `gc_joinactivityrecord`;

CREATE TABLE `gc_joinactivityrecord` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '参与活动的用户id',
  `aid` int(11) DEFAULT NULL COMMENT '对应活动表activity中的主键',
  `isChain` int(1) DEFAULT '0' COMMENT '是否上链',
  `time` timestamp NULL DEFAULT NULL COMMENT '参与活动的时间',
  PRIMARY KEY (`Id`),
  KEY `aid_idx` (`aid`),
  CONSTRAINT `aid` FOREIGN KEY (`aid`) REFERENCES `gc_activity` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参与活动的记录表';
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `gc_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_message` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `message` varchar(512) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '通知内容',
  `time` timestamp NULL DEFAULT NULL COMMENT '通知时间',
  `type` int(2) DEFAULT NULL COMMENT '消息类型 2广播 1组 0个人',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gc_notice`
--

DROP TABLE IF EXISTS `gc_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_notice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `mid` int(11) DEFAULT NULL COMMENT '消息id',
  `flag` int(1) DEFAULT NULL COMMENT '已读标识 1未读 0已读',
  PRIMARY KEY (`Id`),
  KEY `mid_idx` (`mid`),
  CONSTRAINT `mid` FOREIGN KEY (`mid`) REFERENCES `gc_message` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户消息表';
/*!40101 SET character_set_client = @saved_cs_client */;

