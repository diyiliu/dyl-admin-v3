drop table if exists soft_deploy;

/*==============================================================*/
/* Table: soft_deploy                                           */
/*==============================================================*/
create table soft_deploy
(
   id                   int not null auto_increment,
   os                   varchar(50) comment '操作系统',
   address              varchar(100) comment '地址',
   tag                  varchar(200) comment '标签',
   username             varchar(100) comment '用户名',
   password             varchar(100) comment '密码',
   comment              varchar(200) comment '备注',
   primary key (id)
);

