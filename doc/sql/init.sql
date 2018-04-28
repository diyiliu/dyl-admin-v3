INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (1, '首页', null , 'menu', '', '', 'sf-house', '1', 1);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (2, '海洋浮球', null , 'node', '', '', 'sf-compass', '0', 2);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (3, '浮球列表', 2, 'menu', '', '', null, '1', 1);

INSERT INTO sys_asset (id, name, pid, type, controller, action, icon_css, is_menu, sort)
VALUES (4, '历史回放', 2, 'menu', '', '', null, '1', 2);

