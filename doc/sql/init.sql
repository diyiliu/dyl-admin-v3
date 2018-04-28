INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (1, '首页', null , 'menu', '', '', 'sf-house', 1, 1);


INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (2, '海洋浮球', null , 'node', '', '', 'sf-compass', 1, 2);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (3, '浮球列表', 2, 'menu', '', '', null, 1, 1);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (4, '历史回放', 2, 'menu', '', '', null, 1, 2);


INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (5, '系统管理', null , 'node', '', '', 'sf-cog', 1, 3);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (6, '用户管理', 5, 'menu', '', '', null, 1, 1);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (7, '角色管理', 5, 'menu', '', '', null, 1, 2);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (8, '菜单管理', 5, 'menu', '', '', null, 1, 3);

