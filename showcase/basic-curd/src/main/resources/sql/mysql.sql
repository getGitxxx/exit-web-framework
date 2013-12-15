/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : exitsoft-basic-curd

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2013-12-15 14:41:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_user_aud
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_aud`;
CREATE TABLE `tb_user_aud` (
  `id` varchar(32) NOT NULL,
  `rev` int(11) NOT NULL,
  `revtype` tinyint(4) DEFAULT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `portrait` varchar(256) DEFAULT NULL,
  `realname` varchar(64) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`,`rev`),
  KEY `FK_lls5cx43tqbwx5qin1iw82led` (`rev`),
  CONSTRAINT `FK_lls5cx43tqbwx5qin1iw82led` FOREIGN KEY (`rev`) REFERENCES `revinfo` (`rev`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_aud
-- ----------------------------
