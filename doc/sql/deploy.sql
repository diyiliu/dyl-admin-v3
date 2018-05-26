drop table if exists opt_deploy;

/*==============================================================*/
/* Table: soft_deploy                                           */
/*==============================================================*/
create table opt_deploy
(
   id                   int not null auto_increment,
   os                   varchar(50) comment '系统',
   address              varchar(100) comment '地址',
   platform             varchar(100) comment '平台',
   tag                  varchar(200) comment '标签',
   username             varchar(100) comment '用户名',
   password             varchar(100) comment '密码',
   comment              varchar(200) comment '备注',
   create_user          varchar(30) comment '创建人',
   create_time          datetime comment '创建时间',
   primary key (id)
);

