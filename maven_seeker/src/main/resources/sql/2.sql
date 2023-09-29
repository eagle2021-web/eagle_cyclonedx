select * from maven_gav;
select count(*) from maven_gav;
CREATE TABLE `maven_gav`
(
    `id`          bigint(20)   NOT NULL AUTO_INCREMENT,
    `group_id`    varchar(120) not null comment '组织',
    `artifact_id` varchar(120) not null comment '名称',
    `version`     varchar(120) not null comment '版本',
    `create_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录更新时间',
    `is_deleted`  tinyint(1)            DEFAULT '0' COMMENT '是否已删除；0未删除，1已删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;