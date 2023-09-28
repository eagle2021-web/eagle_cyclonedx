create database hsp_mybatis;
use hsp_mybatis;
drop table health;
create table health
(
    id          bigint not null primary key,
    name        varchar(200),
    age         int,
    status      tinyint,
    description varchar(200),
    deleted     bool
) ENGINE = InnoDB
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
# 修改字段名
alter table health
    change deleted is_deleted bool DEFAULT false COMMENT '假删除';
# 修改字段定义
show create table health;
# private Integer id;
# private Integer age;
# private String name;
# private String email;
# private LocalDate birthday;
# private Object salary;
# private Boolean gender;
# @TableLogic
#     private Byte isDeleted;
drop table monster;
create table monster(
    id bigint not null primary key auto_increment,
    age smallint(8) unsigned comment '年龄',
    name varchar(80)  comment '郵箱地址',
    gender ENUM('男', '女') comment '男/女',
    email varchar(80) comment '郵箱地址',
    birthday date comment '生日',
    salary DECIMAL(10,2) comment '工资保留2位',
    is_deleted tinyint comment '是否已删除：0未删除，1已删除'
) ENGINE = INNODB
DEFAULT character set  utf8mb4
COLLATE utf8mb4_general_ci
comment '妖怪信息表';
alter table monster modify is_deleted tinyint comment '是否已删除：0未删除，1已删除' default 0 not null ;
update monster set is_deleted = 0 where is_deleted is null;
show create table monster;
select * from monster;