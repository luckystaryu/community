create table USER(
 id LONG  auto_increment comment '序号',
 account_id VARCHAR(100) comment '账号',
 name       VARCHAR(50) comment '名称',
 token      VARCHAR(36) comment 'github_token',
 gmt_create BIGINT comment '创建时间',
 gmt_modified BIGINT comment '修改时间',
 primary key (id)
);