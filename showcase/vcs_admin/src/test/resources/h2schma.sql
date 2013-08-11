alter table tb_data_dictionary drop foreign key FK_layhfd1butuigsscgucmp2okd
alter table tb_dictionary_category drop foreign key FK_bernf41kympxy2kjl4vbq5q44
alter table tb_group drop foreign key FK_idve4hc50mytxm181wl1knw28
alter table tb_group_resource drop foreign key FK_q82fpmfh128qxoeyymrkg71e2
alter table tb_group_resource drop foreign key FK_3tjs4wt3vvoibo1fvcvog5srd
alter table tb_group_user drop foreign key FK_7k068ltfepa1q75qtmvxuawk
alter table tb_group_user drop foreign key FK_rgmkki7dggfag6ow6eivljmwv
alter table tb_resource drop foreign key FK_k2heqvi9muk4cjyyd53r9y37x
drop table if exists tb_data_dictionary
drop table if exists tb_dictionary_category
drop table if exists tb_group
drop table if exists tb_group_resource
drop table if exists tb_group_user
drop table if exists tb_resource
drop table if exists tb_user
create table tb_data_dictionary (id varchar(32) not null, name varchar(512) not null, pin_yin_code varchar(512), remark text, type varchar(1) not null, value varchar(64) not null, wubi_code varchar(512), fk_category_id char(32) not null, primary key (id)) ENGINE=InnoDB
create table tb_dictionary_category (id varchar(32) not null, code varchar(128) not null, name varchar(256) not null, remark text, fk_parent_id char(32), primary key (id)) ENGINE=InnoDB
create table tb_group (id varchar(32) not null, name varchar(64) not null, remark text, role varchar(64), state integer not null, type varchar(2) not null, value varchar(512), fk_parent_id varchar(32), primary key (id)) ENGINE=InnoDB
create table tb_group_resource (fk_resource_id varchar(32) not null, fk_group_id varchar(32) not null) ENGINE=InnoDB
create table tb_group_user (fk_group_id varchar(32) not null, fk_user_id varchar(32) not null) ENGINE=InnoDB
create table tb_resource (id varchar(32) not null, icon varchar(64), name varchar(64) not null, permission varchar(64), remark text, sort integer not null, type varchar(2) not null, value varchar(512), fk_parent_id varchar(32), primary key (id)) ENGINE=InnoDB
create table tb_user (id varchar(32) not null, email varchar(256), password varchar(32) not null, portrait varchar(32), realname varchar(128) not null, state integer not null, username varchar(64) not null, primary key (id)) ENGINE=InnoDB
alter table tb_data_dictionary add index FK_layhfd1butuigsscgucmp2okd (fk_category_id), add constraint FK_layhfd1butuigsscgucmp2okd foreign key (fk_category_id) references tb_dictionary_category (id)
alter table tb_dictionary_category add constraint UK_9qkei4dxobl1lm4oa0ys8c3nr unique (code)
alter table tb_dictionary_category add index FK_bernf41kympxy2kjl4vbq5q44 (fk_parent_id), add constraint FK_bernf41kympxy2kjl4vbq5q44 foreign key (fk_parent_id) references tb_dictionary_category (id)
alter table tb_group add constraint UK_byw2jrrrxrueqimkmgj3o842j unique (name)
alter table tb_group add constraint UK_tdy6pdeill9wonm82v3f0tlx8 unique (role)
alter table tb_group add index FK_idve4hc50mytxm181wl1knw28 (fk_parent_id), add constraint FK_idve4hc50mytxm181wl1knw28 foreign key (fk_parent_id) references tb_group (id)
alter table tb_group_resource add index FK_q82fpmfh128qxoeyymrkg71e2 (fk_group_id), add constraint FK_q82fpmfh128qxoeyymrkg71e2 foreign key (fk_group_id) references tb_group (id)
alter table tb_group_resource add index FK_3tjs4wt3vvoibo1fvcvog5srd (fk_resource_id), add constraint FK_3tjs4wt3vvoibo1fvcvog5srd foreign key (fk_resource_id) references tb_resource (id)
alter table tb_group_user add index FK_7k068ltfepa1q75qtmvxuawk (fk_user_id), add constraint FK_7k068ltfepa1q75qtmvxuawk foreign key (fk_user_id) references tb_user (id)
alter table tb_group_user add index FK_rgmkki7dggfag6ow6eivljmwv (fk_group_id), add constraint FK_rgmkki7dggfag6ow6eivljmwv foreign key (fk_group_id) references tb_group (id)
alter table tb_resource add constraint UK_aunvlvm32xb4e6590jc9oooq unique (name)
alter table tb_resource add constraint UK_dtdn4ty9aqsnee0b3yhlpkisa unique (permission)
alter table tb_resource add index FK_k2heqvi9muk4cjyyd53r9y37x (fk_parent_id), add constraint FK_k2heqvi9muk4cjyyd53r9y37x foreign key (fk_parent_id) references tb_resource (id)
alter table tb_user add constraint UK_4wv83hfajry5tdoamn8wsqa6x unique (username)
