/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50636
Source Host           : localhost:3306
Source Database       : admin

Target Server Type    : MYSQL
Target Server Version : 50636
File Encoding         : 65001

Date: 2018-01-11 11:41:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for fk_user_role
-- ----------------------------
DROP TABLE IF EXISTS `fk_user_role`;
CREATE TABLE `fk_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fk_user_role
-- ----------------------------
INSERT INTO `fk_user_role` VALUES ('6', '8', '1');
INSERT INTO `fk_user_role` VALUES ('16', '3', '2');
INSERT INTO `fk_user_role` VALUES ('17', '4', '3');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `org_name` varchar(50) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES ('1', '0', '系统总部', null, '2017-12-28 14:02:12');
INSERT INTO `sys_org` VALUES ('2', '1', '分部一', null, '2017-12-28 14:02:27');
INSERT INTO `sys_org` VALUES ('3', '1', '分部二', null, '2017-12-28 14:02:39');
INSERT INTO `sys_org` VALUES ('4', '2', '软件事业部', null, '2017-12-28 14:03:36');
INSERT INTO `sys_org` VALUES ('5', '2', '硬件事业部', null, '2017-12-28 14:03:52');
INSERT INTO `sys_org` VALUES ('6', '0', '系统分部', null, '2017-12-28 16:43:41');

-- ----------------------------
-- Table structure for sys_privilege
-- ----------------------------
DROP TABLE IF EXISTS `sys_privilege`;
CREATE TABLE `sys_privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `master` varchar(50) DEFAULT NULL,
  `master_value` varchar(100) DEFAULT NULL,
  `access` varchar(50) DEFAULT NULL,
  `access_value` varchar(200) DEFAULT NULL,
  `permission` varchar(50) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Records of sys_privilege
-- ----------------------------
INSERT INTO `sys_privilege` VALUES ('1', 'role', '1', 'menu', '1', ':home', null, '1');
INSERT INTO `sys_privilege` VALUES ('2', 'role', '1', 'node', '2', ':operate', null, '1');
INSERT INTO `sys_privilege` VALUES ('3', 'role', '1', 'menu', '3', 'operate:deploy', null, '1');
INSERT INTO `sys_privilege` VALUES ('4', 'role', '1', 'menu', '4', 'operate:tablespace', null, '1');
INSERT INTO `sys_privilege` VALUES ('5', 'role', '1', 'node', '8', ':sys', null, '1');
INSERT INTO `sys_privilege` VALUES ('6', 'role', '1', 'menu', '9', 'sys:user', null, '1');
INSERT INTO `sys_privilege` VALUES ('7', 'role', '1', 'menu', '10', 'sys:menu', null, '1');
INSERT INTO `sys_privilege` VALUES ('8', 'role', '1', 'menu', '11', 'sys:role', null, '1');
INSERT INTO `sys_privilege` VALUES ('14', 'role', '1', 'node', '5', 'blog:*', null, '1');
INSERT INTO `sys_privilege` VALUES ('15', 'role', '1', 'menu', '7', 'blog:release', null, '1');
INSERT INTO `sys_privilege` VALUES ('24', 'role', '2', 'menu', '1', 'home:*', null, '1');
INSERT INTO `sys_privilege` VALUES ('25', 'role', '2', 'node', '5', 'blog:*', null, '1');
INSERT INTO `sys_privilege` VALUES ('26', 'role', '2', 'menu', '7', 'blog:release', null, '1');
INSERT INTO `sys_privilege` VALUES ('27', 'role', '3', 'node', '2', 'operate:*', null, '1');
INSERT INTO `sys_privilege` VALUES ('28', 'role', '3', 'menu', '3', 'operate:deploy', null, '1');
INSERT INTO `sys_privilege` VALUES ('29', 'role', '3', 'menu', '4', 'operate:tablespace', null, '1');

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `res_name` varchar(50) DEFAULT NULL,
  `res_code` varchar(30) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `top` int(11) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1', null, '首页', 'home', 'menu', 'fa fa-home', '', '1', null, '1');
INSERT INTO `sys_resource` VALUES ('2', null, '运维监控', 'operate', 'node', 'fa fa-tachometer', 'operate', '10', null, '1');
INSERT INTO `sys_resource` VALUES ('3', '2', '平台部署', 'deploy', 'menu', 'fa fa-circle-thin', 'operate/deploy', '1', null, '1');
INSERT INTO `sys_resource` VALUES ('4', '2', '数据存储', 'tablespace', 'menu', 'fa fa-circle-thin', 'operate/tablespace', '2', null, '1');
INSERT INTO `sys_resource` VALUES ('5', null, '博客管理', 'blog', 'node', 'fa fa-book', 'blog', '20', null, '1');
INSERT INTO `sys_resource` VALUES ('7', '5', '博客发布', 'release', 'menu', 'fa fa-circle-thin', 'blog/release', '1', null, '1');
INSERT INTO `sys_resource` VALUES ('8', null, '系统配置', 'sys', 'node', 'fa fa-cog', 'sys', '30', null, '1');
INSERT INTO `sys_resource` VALUES ('9', '8', '用户管理', 'user', 'menu', 'fa fa-circle-thin', 'sys/user', '1', null, '1');
INSERT INTO `sys_resource` VALUES ('10', '8', '菜单管理', 'menu', 'menu', 'fa fa-circle-thin', 'sys/menu', '3', null, '1');
INSERT INTO `sys_resource` VALUES ('11', '8', '角色管理', 'role', 'menu', 'fa fa-circle-thin', 'sys/role', '2', null, '1');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `role_code` varchar(30) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', null, '管理员', 'admin', '系统管理员，超级用户。', '2018-01-03 15:16:31', '1');
INSERT INTO `sys_role` VALUES ('2', null, '普通用户', 'normal', '运维，博客', '2018-01-04 15:37:43', '1');
INSERT INTO `sys_role` VALUES ('3', null, '天泽', 'tiza', '天泽运维', '2018-01-09 11:24:36', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(80) DEFAULT NULL COMMENT '登录密码',
  `salt` varchar(50) DEFAULT NULL COMMENT '盐',
  `name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `tel` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `orgid` int(11) DEFAULT NULL COMMENT '用户所属机构',
  `status` int(11) DEFAULT NULL COMMENT '用户状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登陆次数',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('3', 'diyiliu', '56381b59736de0edf4b9f93eee6c7524', '1c4545aca468572ee4f205d3a9f5e80b', '帝一流', null, '13685132332', '4', '1', '2017-12-29 10:41:40', '12', '0:0:0:0:0:0:0:1', '2018-01-09 10:13:10');
INSERT INTO `sys_user` VALUES ('4', 'test', '16e0c60498b629d0d2b02e96c2d119f4', '7be4d8e86d915a61b9edff53d1e2d115', '测试', null, '13352888888', '5', '0', '2017-12-29 11:35:33', '5', '0:0:0:0:0:0:0:1', '2018-01-10 10:15:10');
INSERT INTO `sys_user` VALUES ('5', 'tom', '2042a760146d08f1fa3ebbc6f4d60d23', '4d3a7c5bc8f0a92c63833708336198a6', '汤姆', null, '15852388888', '2', '0', '2017-12-29 12:06:42', '1', '0:0:0:0:0:0:0:1', '2017-12-29 13:40:27');
INSERT INTO `sys_user` VALUES ('6', 'youy', '040449df3f355a6f9d58f7d0626e6506', 'b6d4c193ccd21e177ce393d3f03a3f94', '右右', null, '18111828383', '2', '1', '2017-12-29 13:50:17', '3', '0:0:0:0:0:0:0:1', '2018-01-02 10:09:01');
INSERT INTO `sys_user` VALUES ('8', 'admin', '19a096da58f072f8dba15ed0402d9e99', '57c5eec31d71ee4e74f86e6750ad73cf', '管理员', null, '13685132332', '1', '1', '2017-01-08 15:34:15', '114', '0:0:0:0:0:0:0:1', '2018-01-11 08:57:42');

-- ----------------------------
-- Table structure for xcmg_deploy
-- ----------------------------
DROP TABLE IF EXISTS `xcmg_deploy`;
CREATE TABLE `xcmg_deploy` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform` varchar(50) DEFAULT NULL,
  `overview` varchar(100) DEFAULT NULL,
  `system` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xcmg_deploy
-- ----------------------------
INSERT INTO `xcmg_deploy` VALUES ('1', '徐工科技', '远程服务', 'windows', '58.218.196.207:15289	', 'administrator', 'xutz&152');
INSERT INTO `xcmg_deploy` VALUES ('2', '徐工科技', '通用协议网关1(移动)', 'linux', '10.1.128.15', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('3', '徐工科技', '通用协议网关2', 'linux', '10.1.128.16', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('4', '徐工科技', '通用协议网关3', 'linux', '10.1.128.17', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('5', '徐工建机', '通用协议网关1', 'linux', '10.1.128.18', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('6', '徐工建机', '通用协议网关2', 'linux', '10.1.128.19', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('7', '徐工建机', '通用协议网关3（移动）', 'linux', '10.1.128.20', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('8', '徐工重型', '采集5', 'windows', '10.1.128.160', 'administrator', 'xutz@160');
INSERT INTO `xcmg_deploy` VALUES ('9', '徐工建机', '建机手机(xlink-mobile-webservice)', 'windows', '10.1.128.222', 'administrator', 'tzxw&tz0226@222');
INSERT INTO `xcmg_deploy` VALUES ('10', 'CBMS', '徐工在线', 'linux', '10.1.128.133', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('11', '徐工科技', '数据库代理DBP', 'linux', '10.1.128.134', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('12', '徐工科技', '数据库代理DBP', 'linux', '10.1.128.81', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('13', '徐工建机', '建机DCS，建机ACS', 'windows', '10.1.128.115', 'administrator', 'map@123');
INSERT INTO `xcmg_deploy` VALUES ('14', '核心平台', '数据库代理DP1', 'linux', '10.1.128.26', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('15', '核心平台', '数据库代理DP2', 'linux', '10.1.128.27', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('16', '核心平台', '数据库代理DP3', 'linux', '10.1.128.28', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('17', '核心平台', '数据库代理DP', 'linux', '10.1.128.176', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('18', '核心平台', 'WEB业务门户', 'windows', '10.1.128.40', 'administrator', 'tzxw@1234');
INSERT INTO `xcmg_deploy` VALUES ('19', '徐工重型', '重型分析SP1', 'linux', '10.1.128.76', 'root', '123.123');
INSERT INTO `xcmg_deploy` VALUES ('20', '徐工重型', '重型分析SP5', 'linux', '10.1.128.91', 'root', '123.123');
