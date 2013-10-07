--删除所有表
drop table tb_data_dictionary if exists;
drop table tb_dictionary_category if exists;
drop table tb_group if exists;
drop table tb_group_resource if exists;
drop table tb_group_user if exists;
drop table tb_resource if exists;
drop table tb_user if exists;

--创建系统字典表
create table tb_data_dictionary (id varchar(32) not null, name varchar(256) not null, remark varchar(512), type varchar(1) not null, value varchar(32) not null, fk_category_id varchar(32) not null, primary key (id));
create table tb_dictionary_category (id varchar(32) not null, code varchar(128) not null, name varchar(256) not null, remark varchar(512), fk_parent_id varchar(32), primary key (id));

--创建权限表
create table tb_group (id varchar(32) not null, name varchar(32) not null, remark varchar(512), state integer not null, type varchar(2) not null, fk_parent_id varchar(32), role varchar(64), value varchar(256), primary key (id));
create table tb_group_resource (fk_resource_id varchar(32) not null, fk_group_id varchar(32) not null);
create table tb_group_user (fk_group_id varchar(32) not null, fk_user_id varchar(32) not null);
create table tb_resource (id varchar(32) not null, permission varchar(64), remark varchar(512), sort integer not null, name varchar(32) not null, type varchar(2) not null, value varchar(256), fk_parent_id varchar(32), icon varchar(32), primary key (id));
create table tb_user (id varchar(32) not null, email varchar(128), password varchar(32) not null, portrait varchar(32), realname varchar(64) not null, state integer not null, username varchar(32) not null, primary key (id));

--创建所有表关联
alter table tb_dictionary_category add constraint UK_9qkei4dxobl1lm4oa0ys8c3nr unique (code);
alter table tb_group add constraint UK_byw2jrrrxrueqimkmgj3o842j unique (name);
alter table tb_resource add constraint UK_aunvlvm32xb4e6590jc9oooq unique (name);
alter table tb_user add constraint UK_4wv83hfajry5tdoamn8wsqa6x unique (username);
alter table tb_data_dictionary add constraint FK_layhfd1butuigsscgucmp2okd foreign key (fk_category_id) references tb_dictionary_category;
alter table tb_dictionary_category add constraint FK_bernf41kympxy2kjl4vbq5q44 foreign key (fk_parent_id) references tb_dictionary_category;
alter table tb_group add constraint FK_idve4hc50mytxm181wl1knw28 foreign key (fk_parent_id) references tb_group;
alter table tb_group_resource add constraint FK_q82fpmfh128qxoeyymrkg71e2 foreign key (fk_group_id) references tb_group;
alter table tb_group_resource add constraint FK_3tjs4wt3vvoibo1fvcvog5srd foreign key (fk_resource_id) references tb_resource;
alter table tb_group_user add constraint FK_7k068ltfepa1q75qtmvxuawk foreign key (fk_user_id) references tb_user;
alter table tb_group_user add constraint FK_rgmkki7dggfag6ow6eivljmwv foreign key (fk_group_id) references tb_group;
alter table tb_resource add constraint FK_k2heqvi9muk4cjyyd53r9y37x foreign key (fk_parent_id) references tb_resource;