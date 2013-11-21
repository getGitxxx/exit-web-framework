alter table tb_data_dictionary drop constraint FK_layhfd1butuigsscgucmp2okd
alter table tb_data_record drop constraint FK_5qmdkkc91hqkdysolv0oeji46
alter table tb_dictionary_category drop constraint FK_bernf41kympxy2kjl4vbq5q44
alter table tb_group drop constraint FK_idve4hc50mytxm181wl1knw28
alter table tb_group_resource drop constraint FK_q82fpmfh128qxoeyymrkg71e2
alter table tb_group_resource drop constraint FK_3tjs4wt3vvoibo1fvcvog5srd
alter table tb_group_user drop constraint FK_7k068ltfepa1q75qtmvxuawk
alter table tb_group_user drop constraint FK_rgmkki7dggfag6ow6eivljmwv
alter table tb_operating_record drop constraint FK_j2kne6lllqsg2pvj4fpxptq4t
alter table tb_record_parameter drop constraint FK_6tsjrad76yki1ji619t83r54y
alter table tb_resource drop constraint FK_k2heqvi9muk4cjyyd53r9y37x
drop table tb_data_dictionary if exists
drop table tb_data_record if exists
drop table tb_dictionary_category if exists
drop table tb_group if exists
drop table tb_group_resource if exists
drop table tb_group_user if exists
drop table tb_operating_record if exists
drop table tb_record_parameter if exists
drop table tb_resource if exists
drop table tb_user if exists
create table tb_data_dictionary (id varchar(32) not null, name varchar(256) not null, remark varchar(512), type varchar(1) not null, value varchar(32) not null, fk_category_id varchar(32) not null, primary key (id))
create table tb_data_record (id varchar(32) not null, end_date timestamp not null, operating_target varchar(512) not null, start_date timestamp not null, fk_user_id varchar(32) not null, primary key (id))
create table tb_dictionary_category (id varchar(32) not null, code varchar(128) not null, name varchar(256) not null, remark varchar(512), fk_parent_id varchar(32), primary key (id))
create table tb_group (id varchar(32) not null, name varchar(32) not null, remark varchar(512), role varchar(64), state integer not null, type varchar(2) not null, value varchar(256), fk_parent_id varchar(32), primary key (id))
create table tb_group_resource (fk_resource_id varchar(32) not null, fk_group_id varchar(32) not null)
create table tb_group_user (fk_group_id varchar(32) not null, fk_user_id varchar(32) not null)
create table tb_operating_record (id varchar(32) not null, end_date timestamp not null, operating_target varchar(512) not null, start_date timestamp not null, ip varchar(64) not null, method varchar(256) not null, remark varchar(512), state integer not null, fk_user_id varchar(32) not null, primary key (id))
create table tb_record_parameter (id varchar(32) not null, name varchar(32) not null, value varchar(512) not null, fk_record_id varchar(32) not null, primary key (id))
create table tb_resource (id varchar(32) not null, icon varchar(32), name varchar(32) not null, permission varchar(64), remark varchar(512), sort integer not null, type varchar(2) not null, value varchar(256), fk_parent_id varchar(32), primary key (id))
create table tb_user (id varchar(32) not null, email varchar(128), password varchar(32) not null, portrait varchar(256), realname varchar(64) not null, state integer not null, username varchar(32) not null, primary key (id))
alter table tb_dictionary_category add constraint UK_9qkei4dxobl1lm4oa0ys8c3nr unique (code)
alter table tb_group add constraint UK_byw2jrrrxrueqimkmgj3o842j unique (name)
alter table tb_resource add constraint UK_aunvlvm32xb4e6590jc9oooq unique (name)
alter table tb_user add constraint UK_4wv83hfajry5tdoamn8wsqa6x unique (username)
alter table tb_data_dictionary add constraint FK_layhfd1butuigsscgucmp2okd foreign key (fk_category_id) references tb_dictionary_category
alter table tb_data_record add constraint FK_5qmdkkc91hqkdysolv0oeji46 foreign key (fk_user_id) references tb_user
alter table tb_dictionary_category add constraint FK_bernf41kympxy2kjl4vbq5q44 foreign key (fk_parent_id) references tb_dictionary_category
alter table tb_group add constraint FK_idve4hc50mytxm181wl1knw28 foreign key (fk_parent_id) references tb_group
alter table tb_group_resource add constraint FK_q82fpmfh128qxoeyymrkg71e2 foreign key (fk_group_id) references tb_group
alter table tb_group_resource add constraint FK_3tjs4wt3vvoibo1fvcvog5srd foreign key (fk_resource_id) references tb_resource
alter table tb_group_user add constraint FK_7k068ltfepa1q75qtmvxuawk foreign key (fk_user_id) references tb_user
alter table tb_group_user add constraint FK_rgmkki7dggfag6ow6eivljmwv foreign key (fk_group_id) references tb_group
alter table tb_operating_record add constraint FK_j2kne6lllqsg2pvj4fpxptq4t foreign key (fk_user_id) references tb_user
alter table tb_record_parameter add constraint FK_6tsjrad76yki1ji619t83r54y foreign key (fk_record_id) references tb_operating_record
alter table tb_resource add constraint FK_k2heqvi9muk4cjyyd53r9y37x foreign key (fk_parent_id) references tb_resource
