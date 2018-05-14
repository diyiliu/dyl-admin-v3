drop table if exists rel_user_role;

drop table if exists sys_asset;

drop table if exists sys_privilege;

drop table if exists sys_role;

drop table if exists sys_user;

/*==============================================================*/
/* Table: rel_user_role                                         */
/*==============================================================*/
create table rel_user_role
(
   id                   int not null auto_increment,
   user_id              int,
   role_id              int,
   primary key (id)
);

/*==============================================================*/
/* Table: sys_asset                                             */
/*==============================================================*/
create table sys_asset
(
   id                   int not null auto_increment,
   name                 varchar(50) comment '资源名称',
   code                 varchar(50) comment '资源代码',
   pid                  int comment '父ID',
   pids                 varchar(80) comment '父节组ID',
   type                 varchar(50) comment '类型',
   controller           varchar(100)  comment '控制器',
   view                 varchar(100)  comment '视图',
   icon_css             varchar(100) comment '图标css',
   is_menu              int comment '是否菜单',
   sort                 int comment '排序',
   primary key (id)
);

alter table sys_asset comment '系统资源';

/*==============================================================*/
/* Table: sys_privilege                                         */
/*==============================================================*/
create table sys_privilege
(
   id                   int not null auto_increment,
   master               varchar(50),
   master_value         varchar(100),
   access               varchar(50),
   access_value         varchar(200),
   permission           varchar(50),
   comment              varchar(200),
   primary key (id)
);

alter table sys_privilege comment '权限';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int not null auto_increment,
   pid                  int comment '父ID',
   name                 varchar(50) comment '角色名称',
   code                 varchar(30) comment '角色代码',
   comment              varchar(100) comment '角色描述',
   create_user          varchar(50) comment '创建人',
   create_time          datetime comment '创建时间',
   primary key (id)
);

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   int not null auto_increment,
   username             varchar(50) comment '用户名',
   password             varchar(80)  comment '登录密码',
   salt                 varchar(50) comment '盐',
   name                 varchar(20)  comment '真实姓名',
   email                varchar(50) comment '邮箱',
   tel                  varchar(15) comment '联系电话',
   org_id               int comment '用户所属机构',
   create_time          datetime comment '创建时间',
   create_user          varchar(50)  comment '创建人',
   status               int comment '用户状态',
   expire_time          datetime comment '过期时间',
   login_count          int comment '登陆次数',
   last_login_ip        varchar(20) comment '最后登陆IP',
   last_login_time      datetime comment '最后登陆时间',
   primary key (id)
);