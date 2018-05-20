/*==============================================================*/
/* sys_asset 系统资源                                           */
/*==============================================================*/
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('1', '首页', 'index', '0', '0', 'menu', 'console', 'index', 'sf-house', '1', '1');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('2', '海洋浮球', 'buoy', '0', '0', 'node', '', '', 'sf-compass', '1', '5');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('3', '浮球列表', 'list', '2', '0/2', 'menu', 'home/buoy1', 'buoy/list', '', '1', '5');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('4', '历史回放', 'history', '2', '0/2', 'menu', 'home/buoy2', 'buoy/historyData', NULL, '1', '10');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('5', '系统管理', 'sys', '0', '0', 'node', '', '', 'sf-cog', '1', '10');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('6', '用户管理', 'user', '5', '0/5', 'menu', 'home/user', 'sys/user', NULL, '1', '5');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('7', '角色管理', 'role', '5', '0/5', 'menu', 'home/role', 'sys/role', NULL, '1', '10');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('8', '菜单管理', 'menu', '5', '0/5', 'menu', 'home/menu', 'sys/menu', NULL, '1', '15');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('14', '网址导航', 'guide', '0', '0', 'menu', 'home/guide', 'guide/list', 'sf-paper-plane', '1', '8');
INSERT INTO `admin3`.`sys_asset` (`id`, `name`, `code`, `pid`, `pids`, `type`, `controller`, `view`, `icon_css`, `is_menu`, `sort`) VALUES ('15', '网址导航-子页面', 'guideSort', '0', '0', 'menu', 'home/guide.1', 'guide/sort', '', '0', '9');

/*==============================================================*/
/* sys_privilege 权限                                           */
/*==============================================================*/
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('1', 'role', '1', 'menu', '1', 'home:index', NULL);
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('2', 'role', '1', 'menu', '4', 'buoy:history', NULL);
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('3', 'role', '1', 'menu', '3', 'buoy:list', NULL);
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('4', 'role', '1', 'menu', '14', 'home:guide', NULL);
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('5', 'role', '1', 'menu', '6', 'sys:user', NULL);
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('6', 'role', '1', 'menu', '8', 'sys:menu', NULL);
INSERT INTO `admin3`.`sys_privilege` (`id`, `master`, `master_value`, `access`, `access_value`, `permission`, `comment`) VALUES ('7', 'role', '1', 'menu', '7', 'sys:role', NULL);

/*==============================================================*/
/* sys_role 角色                                                */
/*==============================================================*/
INSERT INTO `admin3`.`sys_role` (`id`, `pid`, `name`, `code`, `comment`, `create_user`, `create_time`) VALUES ('1', NULL, '管理员角色', 'admin', '超级权限', 'admin', '2018-05-09 21:55:33');
/*==============================================================*/
/* sys_user 用户                                                */
/*==============================================================*/
INSERT INTO `admin3`.`sys_user` (`id`, `username`, `password`, `salt`, `name`, `email`, `tel`, `org_id`, `create_time`, `create_user`, `status`, `expire_time`, `login_count`, `last_login_ip`, `last_login_time`) VALUES ('1', 'admin', '48946299254cd704032f752c26ef2d16', '4e9659eaef142156409d27e4703fbf48', '管理员', '6669@dyl.com', '87166669', NULL, NULL, NULL, NULL, '2020-05-11 00:00:00', '12', '0:0:0:0:0:0:0:1', '2018-05-20 19:45:36');/*==============================================================*/
/* rel_user_role 用户-角色                                                */
/*==============================================================*/
INSERT INTO `admin3`.`rel_user_role` (`id`, `user_id`, `role_id`) VALUES ('1', '1', '1');
