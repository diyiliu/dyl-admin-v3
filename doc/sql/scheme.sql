/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50173
Source Host           : 106.15.89.145:3306
Source Database       : admin3

Target Server Type    : MYSQL
Target Server Version : 50173
File Encoding         : 65001

Date: 2018-05-19 23:20:57
*/

-- ----------------------------
-- Table structure for rel_user_role
-- ----------------------------
DROP TABLE IF EXISTS `rel_user_role`;
CREATE TABLE `rel_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_asset
-- ----------------------------
DROP TABLE IF EXISTS `sys_asset`;
CREATE TABLE `sys_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `code` varchar(50) DEFAULT NULL COMMENT '资源代码',
  `pid` int(11) DEFAULT NULL COMMENT '父ID',
  `pids` varchar(80) DEFAULT NULL COMMENT '父节组ID',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `controller` varchar(100) DEFAULT NULL COMMENT '控制器',
  `view` varchar(100) DEFAULT NULL COMMENT '视图',
  `icon_css` varchar(100) DEFAULT NULL COMMENT '图标css',
  `is_menu` int(11) DEFAULT NULL COMMENT '是否菜单',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='系统资源';

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
  `comment` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=156 DEFAULT CHARSET=utf8 COMMENT='权限';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL COMMENT '父ID',
  `name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(30) DEFAULT NULL COMMENT '角色代码',
  `comment` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

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
  `org_id` int(11) DEFAULT NULL COMMENT '用户所属机构',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `status` int(11) DEFAULT NULL COMMENT '用户状态',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登陆次数',
  `last_login_ip` varchar(20) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- 导航子系统
-- ----------------------------

-- ----------------------------
-- Table structure for guide_site
-- ----------------------------
DROP TABLE IF EXISTS `guide_site`;
CREATE TABLE `guide_site` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `URL` varchar(200) DEFAULT NULL,
  `IMAGE` varchar(200) DEFAULT NULL,
  `TYPE` int(11) DEFAULT NULL,
  `COMMENT` varchar(100) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  `CREATE_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 COMMENT='网站地址';

-- ----------------------------
-- Table structure for guide_type
-- ----------------------------
DROP TABLE IF EXISTS `guide_type`;
CREATE TABLE `guide_type` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CODE` varchar(5) DEFAULT NULL,
  `NAME` varchar(20) DEFAULT NULL,
  `SORT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='网址类型';


-- ----------------------------
-- 库存子系统
-- ----------------------------
drop table if exists grain_member;

/*==============================================================*/
/* Table: grain_member                                          */
/*==============================================================*/
create table grain_member
(
  id                   int not null auto_increment,
  name                 varchar(50) comment '姓名',
  tel                  varchar(20) comment '电话',
  address              varchar(100) comment '地址',
  create_user          varchar(30) comment '创建人',
  create_time          datetime comment '创建时间',
  primary key (id)
);

drop table if exists grain_stock;

/*==============================================================*/
/* Table: grain_stock                                           */
/*==============================================================*/
create table grain_stock
(
  id                   int not null auto_increment,
  book_no              int comment '账本号',
  serial_no            varchar(30) comment '序列号',
  gross                int comment '毛重',
  tare                 int comment '皮重',
  suttle               int comment '净重',
  price                numeric(5,2) comment '单价',
  money                numeric(10,1) comment '金额',
  member_id            int comment '关系人',
  create_time          datetime comment '创建时间',
  pay_time             datetime comment '付款时间',
  status               int comment '状态',
  primary key (id)
);

drop table if exists grain_sold;

/*==============================================================*/
/* Table: grain_sold                                            */
/*==============================================================*/
drop table if exists grain_sold;

/*==============================================================*/
/* Table: grain_sold                                            */
/*==============================================================*/
create table grain_sold
(
  id                   int not null auto_increment,
  company              varchar(50) comment '收购单位',
  serial_no            varchar(50) comment '出售单号',
  gross                int comment '毛重',
  tare                 int comment '去皮',
  suttle               int comment '净重',
  price                numeric(5,2) comment '单价',
  money                numeric(10,1) comment '金额',
  create_time          datetime comment '创建时间',
  primary key (id)
);
