/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50614
Source Host           : localhost:3306
Source Database       : exitsoft-basic-curd

Target Server Type    : MYSQL
Target Server Version : 50614
File Encoding         : 65001

Date: 2013-11-11 11:10:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_data_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `tb_data_dictionary`;
CREATE TABLE `tb_data_dictionary` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `type` varchar(1) COLLATE utf8_bin NOT NULL,
  `value` varchar(32) COLLATE utf8_bin NOT NULL,
  `fk_category_id` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_layhfd1butuigsscgucmp2okd` (`fk_category_id`),
  CONSTRAINT `FK_layhfd1butuigsscgucmp2okd` FOREIGN KEY (`fk_category_id`) REFERENCES `tb_dictionary_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_data_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_dictionary_category`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dictionary_category`;
CREATE TABLE `tb_dictionary_category` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `code` varchar(128) COLLATE utf8_bin NOT NULL,
  `name` varchar(256) COLLATE utf8_bin NOT NULL,
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `fk_parent_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9qkei4dxobl1lm4oa0ys8c3nr` (`code`),
  KEY `FK_bernf41kympxy2kjl4vbq5q44` (`fk_parent_id`),
  CONSTRAINT `FK_bernf41kympxy2kjl4vbq5q44` FOREIGN KEY (`fk_parent_id`) REFERENCES `tb_dictionary_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_dictionary_category
-- ----------------------------
INSERT INTO `tb_dictionary_category` VALUES ('402881e437d467d80137d46fc0e50001', 'state', '状态', null, null);

-- ----------------------------
-- Table structure for `tb_group`
-- ----------------------------
DROP TABLE IF EXISTS `tb_group`;
CREATE TABLE `tb_group` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `role` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `state` int(11) NOT NULL,
  `type` varchar(2) COLLATE utf8_bin NOT NULL,
  `value` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `fk_parent_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_byw2jrrrxrueqimkmgj3o842j` (`name`),
  KEY `FK_idve4hc50mytxm181wl1knw28` (`fk_parent_id`),
  CONSTRAINT `FK_idve4hc50mytxm181wl1knw28` FOREIGN KEY (`fk_parent_id`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_group
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_group_resource`
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_resource`;
CREATE TABLE `tb_group_resource` (
  `fk_resource_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `fk_group_id` varchar(32) COLLATE utf8_bin NOT NULL,
  KEY `FK_q82fpmfh128qxoeyymrkg71e2` (`fk_group_id`),
  KEY `FK_3tjs4wt3vvoibo1fvcvog5srd` (`fk_resource_id`),
  CONSTRAINT `FK_3tjs4wt3vvoibo1fvcvog5srd` FOREIGN KEY (`fk_resource_id`) REFERENCES `tb_resource` (`id`),
  CONSTRAINT `FK_q82fpmfh128qxoeyymrkg71e2` FOREIGN KEY (`fk_group_id`) REFERENCES `tb_group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_group_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_group_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_group_user`;
CREATE TABLE `tb_group_user` (
  `fk_group_id` varchar(32) COLLATE utf8_bin NOT NULL,
  `fk_user_id` varchar(32) COLLATE utf8_bin NOT NULL,
  KEY `FK_7k068ltfepa1q75qtmvxuawk` (`fk_user_id`),
  KEY `FK_rgmkki7dggfag6ow6eivljmwv` (`fk_group_id`),
  CONSTRAINT `FK_rgmkki7dggfag6ow6eivljmwv` FOREIGN KEY (`fk_group_id`) REFERENCES `tb_group` (`id`),
  CONSTRAINT `FK_7k068ltfepa1q75qtmvxuawk` FOREIGN KEY (`fk_user_id`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_group_user
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_resource`
-- ----------------------------
DROP TABLE IF EXISTS `tb_resource`;
CREATE TABLE `tb_resource` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `icon` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(32) COLLATE utf8_bin NOT NULL,
  `permission` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `remark` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `sort` int(11) NOT NULL,
  `type` varchar(2) COLLATE utf8_bin NOT NULL,
  `value` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `fk_parent_id` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_aunvlvm32xb4e6590jc9oooq` (`name`),
  KEY `FK_k2heqvi9muk4cjyyd53r9y37x` (`fk_parent_id`),
  CONSTRAINT `FK_k2heqvi9muk4cjyyd53r9y37x` FOREIGN KEY (`fk_parent_id`) REFERENCES `tb_resource` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` varchar(32) COLLATE utf8_bin NOT NULL,
  `email` varchar(128) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(32) COLLATE utf8_bin NOT NULL,
  `portrait` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `realname` varchar(64) COLLATE utf8_bin NOT NULL,
  `state` int(11) NOT NULL,
  `username` varchar(32) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4wv83hfajry5tdoamn8wsqa6x` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
