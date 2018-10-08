-- MySQL dump 10.13  Distrib 5.6.41, for Win64 (x86_64)
--
-- Host: localhost    Database: activity
-- ------------------------------------------------------
-- Server version	5.6.41-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `gc_activity`
--

DROP TABLE IF EXISTS `gc_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_activity` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `startid` int(11) DEFAULT NULL COMMENT '竟锤发起人的id',
  `getid` int(11) DEFAULT NULL COMMENT '活动结束，中奖用户的id',
  `typeid` int(11) DEFAULT NULL COMMENT '对应活动类型表gc_activitytype中的主键',
  `contract` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '合约地址',
  `isbond` int(1) DEFAULT '0' COMMENT '是否缴纳保证金',
  `place` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '发起人的地点',
  `pname` varchar(55) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品的名称',
  `pnumber` int(11) DEFAULT NULL COMMENT '产品的数量',
  `price` double(18,2) DEFAULT NULL COMMENT '产品价格',
  `premium` double(4,2) DEFAULT '0.00' COMMENT '产品的溢价率',
  `pdescribe` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '产品的描述详情',
  `status` int(11) DEFAULT NULL COMMENT '活动(竟锤)的状态 0:待审核  1:驳回 2:活动中 3：已结束 4：活动异常 5：活动失败 ',
  `delivergoodstatus` int(1) DEFAULT NULL COMMENT '发货的标志0：没发货 1：已发货',
  `collectgoodstatus` int(1) DEFAULT NULL COMMENT '收货的标志 0：没收货 1：已收货',
  `time` timestamp NULL DEFAULT NULL COMMENT '发起活动（竟锤）的时间',
  `begintime` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `endtime` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '活动名称',
  `num` int(11) DEFAULT '0' COMMENT '参与活动人数',
  `par` int(11) DEFAULT NULL COMMENT '票面值',
  PRIMARY KEY (`Id`),
  KEY `typeid_idx` (`typeid`),
  CONSTRAINT `typeid` FOREIGN KEY (`typeid`) REFERENCES `gc_activitytype` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动（竟锤）表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gc_activitytype`
--

DROP TABLE IF EXISTS `gc_activitytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_activitytype` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '活动的类型',
  `days` int(11) DEFAULT NULL COMMENT '持续天数',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动（竟锤）类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gc_address`
--

DROP TABLE IF EXISTS `gc_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_address` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人姓名',
  `telephone` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人电话',
  `province` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '市',
  `county` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '区',
  `lock` int(1) DEFAULT '0',
  `status` int(1) DEFAULT '0',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '收货人详细地址',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gc_joinactivityrecord`
--

DROP TABLE IF EXISTS `gc_joinactivityrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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

--
-- Table structure for table `gc_message`
--

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

--
-- Table structure for table `gc_order`
--

DROP TABLE IF EXISTS `gc_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_order` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL COMMENT '活动id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流名称',
  `number` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '物流单号',
  PRIMARY KEY (`Id`),
  KEY `aido_idx` (`aid`),
  CONSTRAINT `aido` FOREIGN KEY (`aid`) REFERENCES `gc_activity` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动物流表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gc_pimage`
--

DROP TABLE IF EXISTS `gc_pimage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_pimage` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `aid` int(11) DEFAULT NULL COMMENT '活动id',
  `imageurl` varchar(250) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '产品图片',
  `flag` int(1) DEFAULT NULL COMMENT '图片标识',
  PRIMARY KEY (`Id`),
  KEY `aidp_idx` (`aid`),
  CONSTRAINT `aidp` FOREIGN KEY (`aid`) REFERENCES `gc_activity` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品图片表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `gc_premium`
--

DROP TABLE IF EXISTS `gc_premium`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gc_premium` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `premax` int(11) DEFAULT '0' COMMENT '溢价区间最大值，最小值为0',
  `integralmin` int(11) DEFAULT '0' COMMENT '积分最小值',
  `integralmax` int(11) DEFAULT '0' COMMENT '积分最大值',
  `defaultval` double DEFAULT '0' COMMENT '溢价区间默认值',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='溢价区间表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-28 15:45:37
