use hsp_mybatis;
show create table maven_repo;
CREATE TABLE `maven_repo`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `relative_path` varchar(120)        DEFAULT NULL COMMENT '相对路径',
    `text`          longtext COMMENT '完整的html text信息',
    `create_time`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp  NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录更新时间',
    `is_deleted`    tinyint(1)          DEFAULT '0' COMMENT '是否已删除；0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;
create index idx_relativepath on maven_repo(relative_path);
select *
from maven_repo;
select count(*) from maven_repo;
