/*==============================================================*/
/* sys_asset 系统资源                                           */
/*==============================================================*/
INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (1, '首页', 'index', 0 , '0', 'menu', '', 'index', 'sf-house', 1, 1);

INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (2, '海洋浮球', 'buoy', 0, '0', 'node', '', '', 'sf-compass', 1, 5);

INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (3, '浮球列表', 'list', 2, '0/2', 'menu', 'home/buoy1', 'buoy/list', null, 1, 5);

INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (4, '历史回放', 'history',  2, '0/2',  'menu', 'home/buoy2', 'buoy/historyData', null, 1, 10);


INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (5, '系统管理', 'sys', 0 , '0', 'node', '', '', 'sf-cog', 1, 10);

INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (6, '用户管理', 'user',  5, '0/5', 'menu', 'home/user', 'sys/user', null, 1, 5);

INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (7, '角色管理', 'role', 5, '0/5', 'menu', 'home/role', 'sys/role', null, 1, 10);

INSERT INTO sys_asset (id, name, code, pid, pids, type, controller, view, icon_css, is_menu, sort)
VALUES (8, '菜单管理', 'menu',  5, '0/5', 'menu', 'home/menu', 'sys/menu', null, 1, 15);

/*==============================================================*/
/* sys_asset 系统资源                                           */
/*==============================================================*/

INSERT INTO sys_user (id, username, password, salt, name)
VALUES (1, 'admin', 'b0f46351f2dac85a119e0dab1f8d7dbf', '6833ce53eb357aa850c8aeb750d256ee', '管理员');