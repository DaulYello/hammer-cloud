/*created by huangshuang on 2018/11/20*/
ALTER TABLE `gc_address` ADD COLUMN `create_time` TIMESTAMP NULL COMMENT '创建时间' AFTER `lock`, ADD COLUMN `update_time` TIMESTAMP NULL COMMENT '修改时间' AFTER `create_time`;